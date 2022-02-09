package az.abbas.product.order.search.impl;

import az.abbas.product.order.db.dto.CustomerDTO;
import az.abbas.product.order.db.entity.Customer;
import az.abbas.product.order.db.repo.RepoCustomer;
import az.abbas.product.order.search.services.CustomerSearchServices;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
public class CustomerSearchServicesImpl implements CustomerSearchServices {

    private final RepoCustomer repoCustomer;
    private final EntityManager entityManager;

    @Override
    public List<CustomerDTO> getAllCustomer(){
        List<Customer> allCustomer = repoCustomer.getAllCustomer();
        List<CustomerDTO> dtoList = new ArrayList<>();

        log.info("Search < getAllCustomer > from DATABASE");
        allCustomer.forEach(item->{

            CustomerDTO build = CustomerDTO.builder()
                    .idCustomer(item.getIdCustomer())
                    .name(item.getName())
                    .surname(item.getSurname())
                    .username(item.getUsername())
                    .address(item.getAddress())
                    .balance(item.getBalance())
                    .createdTimeCustomer(item.getCreatedTimeCustomer())
                    .orders(item.getOrders())
                    .password(item.getPassword())
                    .build();
            dtoList.add(build);
        });

        return dtoList;
    }

    public List<CustomerDTO> getCustomerByname(String name){

        List<Customer> customerList = repoCustomer.getCustomerByName(name);
        List<CustomerDTO> dtoList = new ArrayList<>();
        log.info("Search < getCustomerByname > from DATABASE");
        customerList.forEach(item->{
            CustomerDTO build = CustomerDTO.builder()
                    .idCustomer(item.getIdCustomer())
                    .name(item.getName())
                    .surname(item.getSurname())
                    .username(item.getUsername())
                    .address(item.getAddress())
                    .createdTimeCustomer(item.getCreatedTimeCustomer())
                    .balance(item.getBalance())
                    .orders(item.getOrders())
                    .password(item.getPassword())
                    .build();
            dtoList.add(build);
        });
        return dtoList;
    }


    public List<Customer> searchCustomerWithCriteria(CustomerDTO request){
        log.info("Search < searchCustomerWithCriteria > from DATABASE");
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Customer> criteriaQuery = criteriaBuilder.createQuery(Customer.class);
        Root<Customer> root = criteriaQuery.from(Customer.class);

        List<Predicate> predicateList = new ArrayList<>();

        if (request.getName()!=null){
            Predicate name = criteriaBuilder.equal(root.get("name"), request.getName());
            predicateList.add(name);
        }
        if (request.getSurname()!=null){
            Predicate surname = criteriaBuilder.equal(root.get("surname"), request.getSurname());
            predicateList.add(surname);
        }
        if (request.getUsername()!=null){
            Predicate username = criteriaBuilder.equal(root.get("username"), request.getUsername());
            predicateList.add(username);
        }

        criteriaQuery.where(predicateList.toArray(new Predicate[predicateList.size()]));

        return entityManager.createQuery(criteriaQuery).getResultList();
    }













}
