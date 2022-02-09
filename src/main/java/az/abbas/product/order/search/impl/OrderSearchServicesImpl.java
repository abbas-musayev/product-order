package az.abbas.product.order.search.impl;

import az.abbas.product.order.db.dto.LocalDateDTO;
import az.abbas.product.order.db.dto.OrderDTO;
import az.abbas.product.order.db.entity.Customer;
import az.abbas.product.order.db.entity.Orders;
import az.abbas.product.order.db.repo.RepoOrder;
import az.abbas.product.order.enums.EnumOrderStatus;
import az.abbas.product.order.exception.ThrowNotFoundExpHandle;
import az.abbas.product.order.search.services.OrderSearchServices;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderSearchServicesImpl implements OrderSearchServices {


    private final RepoOrder repoOrder;
    private final EntityManager entityManager;

    // CriteriaBuilder istifade etmekle dinamik axtaris
    public List<Orders> searchWithCriteria(OrderDTO request){

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Orders> criteriaQuery = criteriaBuilder.createQuery(Orders.class);
        Root<Orders> root = criteriaQuery.from(Orders.class);

        List<Predicate> predicateList = new ArrayList<>();

        if (request.getEnumOrderStatus()!=null){
            Predicate predicate = criteriaBuilder.equal(root.get("enumOrderStatus"), request.getEnumOrderStatus());
            predicateList.add(predicate);
        }
        if (request.getOrderCreatedDate()!=null){
            Predicate predicate = criteriaBuilder.equal(root.get("orderCreatedDate"), request.getOrderCreatedDate());
            predicateList.add(predicate);
        }
        if (request.getCustomer().getName()!=null){
            criteriaBuilder.equal(root.join("customer").get("name"),request.getCustomer().getName());
        }

        criteriaQuery.where(predicateList.toArray(new Predicate[predicateList.size()]));

        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    //Musterinin adina gore Sifaris-lerinin axtarisi
    public List<Orders> allOrderByCustomerName(String name){
        List<Orders> orders = repoOrder.AllOrderByCustomerName(name);
        log.info("Search < allOrderByCustomerName > from DATABASE");
        if (orders.isEmpty()) {
            throw new ThrowNotFoundExpHandle("Axtardiginiz Istifadecinin Sifarisleri Yoxdur");
        }else {
            return orders;
        }

    }

    //Unikal koda gore Sifaris axtarisi
    public OrderDTO findOrderByOrderUnique(Integer unique){

        Orders orders = repoOrder.findOrderByOrderUnique(unique);
        log.info("Search < findOrderByOrderUnique > from DATABASE");
        if (orders == null){
            throw new ThrowNotFoundExpHandle("Axtardiginiz Sifaris Tapilmadi");
        }else {

            Customer customer = orders.getCustomer();
            log.info("Orderin Customeri: {}",customer);

            return OrderDTO.builder()
                    .idOrder(orders.getIdOrder())
                    .address(orders.getAddress())
                    .orderCreatedDate(orders.getOrderCreatedDate())
                    .orderFinisDate(orders.getOrderFinisDate())
                    .enumOrderStatus(orders.getEnumOrderStatus())
                    .order_unique(orders.getOrder_unique())
                    .customer(orders.getCustomer())
                    .productAll(orders.getProductAll())
                    .build();

        }
    }

    //EnumStatusa gore Sifaris axatarisi
    public List<Orders> getAllOrderStatus(EnumOrderStatus enums){
        List<Orders> allOrder = repoOrder.getAllOrder(enums);
        log.info("Search < getAllOrderStatus > from DATABASE");
        return allOrder;
    }

    public List<Orders> getAllOrderWithPagination(Integer pageNumber, Integer pageSize){

//        Sort sort = Sort.by(Sort.Direction.ASC, "age");
        log.info("Search < getAllOrderWithPagination > from DATABASE");

        PageRequest pageable = PageRequest.of(pageNumber, pageSize);
        Page<Orders> all = repoOrder.findAll(pageable);

        return all.getContent();
    }


    public List<Orders> getOrderbetwenTimes(LocalDateDTO request){

        log.info("Search < getOrderbetwenTimes > from DATABASE");
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Orders> criteriaQuery = criteriaBuilder.createQuery(Orders.class);
        Root<Orders> root = criteriaQuery.from(Orders.class);

        List<Predicate> predicateList= new ArrayList<>();

        if (request.getOrderCreatedDate()==null&&request.getOrderFinishDate()==null){
            Predicate orderCreatedDate = criteriaBuilder.between(root.get("orderCreatedDate"), request.getOrderCreatedDate().atStartOfDay(), request.getOrderFinishDate().atTime(23, 59, 59));
            predicateList.add(orderCreatedDate);
        }
        criteriaQuery.where(predicateList.toArray(new Predicate[predicateList.size()]));

        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}
