package az.abbas.product.order.crud.impl;

import az.abbas.product.order.crud.services.OrderCrudServices;
import az.abbas.product.order.db.dto.OrderDTO;
import az.abbas.food.order.db.entity.*;
import az.abbas.product.order.db.repo.RepoCustomer;
import az.abbas.product.order.db.repo.RepoOrder;
import az.abbas.product.order.db.repo.RepoProduct;
import az.abbas.product.order.db.repo.RepoProductAll;
import az.abbas.product.order.exception.ThrowInsufficientBalanceException;
import az.abbas.product.order.exception.ThrowNotFoundExpHandle;
import az.abbas.product.order.db.entity.*;
import az.abbas.product.order.search.impl.OrderSearchServicesImpl;
import com.example.cbrn.generaltask.db.entity.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderCrudServicesImpl implements OrderCrudServices {

    private final RepoOrder repoOrder;
    private final RepoCustomer repoCustomer;
    private final RepoProduct repoProduct;
    private final RepoProductAll repoProductAll;
    private final OrderSearchServicesImpl orderServices;

    public OrderDTO saveOrder(OrderDTO orderDTO){

        Customer customerBySurname = repoCustomer.getCustomerBySurnameAndName(orderDTO.getCustomer().getSurname(), orderDTO.getCustomer().getName());

        List<Address> address = orderDTO.getCustomer().getAddress();
        String addName = null;
        for (Address address1 : address) {
            addName = address1.getAddressName();
        }

        //******
        List<ProductAll> productlist = orderDTO.getProductAll();
        //******
        Random random = new Random();
        int i = random.nextInt(90000);
        if (i<10000){
            i+=10000;
        }

        Float balance = customerBySurname.getBalance();

        Orders orders = Orders.builder()
                .idOrder(orderDTO.getIdOrder())
                .customer(customerBySurname)
                .productAll(orderDTO.getProductAll())
                .enumOrderStatus(orderDTO.getEnumOrderStatus())
                .address(addName)
                .orderFinisDate(orderDTO.getOrderFinisDate())
                .order_unique(i)
                .build();

        log.info("OrderMy.builder ile Set olundu");
        //Mehsullarin List icine yigilib save olunmasi ucun yaranib
        List<ProductAll> all = new ArrayList<>();
        //Mehsul Table-inda mehsullarin miqdarinin azalmasi ucun yaranib
        List<Product> all1 = new ArrayList<>();
        // Mehsullarin adinin yoxlanisi Ve Hesabdan pul cixilmasi Ve ProductList Save Olunur Ve Product miqdarinnan cixilir
        for (ProductAll item : productlist) {

            Product product = repoProduct.findProductByName(item.getProductName());

            if (product != null){
                log.info("ProductName null OLMADI");

                Integer miqdar = product.getMiqdar();
                if (miqdar<=0){
                    throw new ThrowInsufficientBalanceException("Bu Mehsuldan Anbarda Qalmamisdir");
                }else {
                    //Mehsulun miqdarinnan mehsulun sayi -1 cixilir
                    product.setMiqdar(miqdar - item.getMiqdar());
                    all1.add(product);
                }
                //balansdaki pulun yeterli olub-olmadigini yoxlayir
                if ((customerBySurname.getBalance()- product.getProductPrice())<0){
                    throw new ThrowInsufficientBalanceException("Az-Yetersiz Bakiye\nEng- Insufficient Balance");
                }
                //balansdan pulu cixir
                customerBySurname.setBalance(balance - product.getProductPrice());

                item.setOrders(orders);
                item.setProductPrice(product.getProductPrice());
                all.add(item);
                log.info("ProductList Save Olundu");

            }
        }
        repoProduct.saveAll(all1);
        log.info("Productun miqdarinnan cixildi");

        repoProductAll.saveAll(all);
        log.info("ProductAll set Olundu");


        repoCustomer.save(customerBySurname);
        log.info("Balans emeliyyati kecirildi");

        Orders save = repoOrder.save(orders);
        log.info("Order Save Olundu");

       return OrderDTO.builder()
                .idOrder(save.getIdOrder())
                .address(save.getAddress())
                .orderCreatedDate(save.getOrderCreatedDate())
                .orderFinisDate(save.getOrderFinisDate())
                .enumOrderStatus(save.getEnumOrderStatus())
                .order_unique(save.getOrder_unique())
                .customer(save.getCustomer())
                .productAll(save.getProductAll())
                .build();
    }

    public Orders changeOrderStatus(OrderDTO dto){

        OrderDTO orderDto = orderServices.findOrderByOrderUnique(dto.getOrder_unique());

        if (orderDto == null){
            log.info("changeOrderStatus Ucun Order Tapilmadi");
            return null;
        }else {

            Orders order = Orders.builder()
                    .idOrder(orderDto.getIdOrder())
                    .customer(orderDto.getCustomer())
                    .productAll(orderDto.getProductAll())
                    .enumOrderStatus(orderDto.getEnumOrderStatus())
                    .address(orderDto.getAddress())
                    .orderFinisDate(orderDto.getOrderFinisDate())
                    .order_unique(orderDto.getOrder_unique())
                    .build();

            order.setEnumOrderStatus(dto.getEnumOrderStatus());
            order.setOrderFinisDate(LocalDateTime.now());
            repoOrder.save(order);
            log.info("changeOrderStatus Deyisdirildi");
            return order;
        }
    }

    public String deleteOrder(Integer unique){
        Orders order = repoOrder.findOrderByOrderUnique(unique);

        if (order==null){
            throw new ThrowNotFoundExpHandle("Silinmek ucun Sifaris Tapilmadi");
        }
        else{
            List<ProductAll> productAll = order.getProductAll();
            repoProductAll.deleteAll(productAll);
            log.info("ProductAll dan ("+order.getIdOrder()+") id-li sifaris silindi");
            repoOrder.delete(order);
            log.info("Sifaris ("+unique+") Silindi");
            return "Sifaris ("+unique+") Silindi";
        }
    }

    public OrderDTO updateOrder(OrderDTO orderDTO){

        Orders orders = repoOrder.findOrderByOrderUnique(orderDTO.getOrder_unique());

        if (orders !=null){
            Orders build = Orders.builder()
                    .idOrder(orderDTO.getIdOrder())
                    .address(orderDTO.getAddress())
//                    .customer(orderDTO.getCustomer())
                    .order_unique(orders.getOrder_unique())
                    .productAll(orderDTO.getProductAll())
                    .enumOrderStatus(orderDTO.getEnumOrderStatus())
                    .orderCreatedDate(orders.getOrderCreatedDate())
                    .orderFinisDate(orderDTO.getOrderFinisDate())
                    .build();
            repoOrder.save(build);

            return OrderDTO.builder()
                    .idOrder(build.getIdOrder())
                    .address(build.getAddress())
                    .orderCreatedDate(build.getOrderCreatedDate())
                    .orderFinisDate(build.getOrderFinisDate())
                    .enumOrderStatus(build.getEnumOrderStatus())
                    .order_unique(orders.getOrder_unique())
                    .customer(build.getCustomer())
                    .productAll(build.getProductAll())
                    .build();

        }else {
            throw new ThrowNotFoundExpHandle("Update Etmek Istediyiniz Siafris Tapilmadi");
        }
    }
}
