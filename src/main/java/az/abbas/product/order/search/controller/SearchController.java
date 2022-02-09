package az.abbas.product.order.search.controller;

import az.abbas.product.order.db.dto.CustomerDTO;
import az.abbas.product.order.db.dto.LocalDateDTO;
import az.abbas.product.order.db.dto.OrderDTO;
import az.abbas.product.order.db.entity.Customer;
import az.abbas.product.order.db.entity.Orders;
import az.abbas.product.order.db.entity.Product;
import az.abbas.product.order.search.services.ProductSearchServices;
import az.abbas.product.order.util.Views;
import az.abbas.product.order.util.ViewsOrder;
import az.abbas.product.order.search.services.CustomerSearchServices;
import az.abbas.product.order.search.services.OrderSearchServices;
import az.abbas.product.order.search.services.ProductAllSearchServices;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/search")
public class SearchController {


    private final CustomerSearchServices customerServices;
    private final OrderSearchServices orderServices;
    private final ProductSearchServices productServices;
    private final ProductAllSearchServices productAllServices;

//    **********************************Customer******************************************************************
    @Cacheable(value = "Customer")
    @JsonView(Views.simple.class)
    @GetMapping("/getAllCustomer")
    public ResponseEntity<List<CustomerDTO>> getAllCustomer(){
        return new ResponseEntity<>(customerServices.getAllCustomer(), HttpStatus.OK);
    }

    @JsonView(Views.simple.class)
    @Cacheable(value = "Customer")
    @GetMapping("/getCustomerByname")
    public ResponseEntity<List<CustomerDTO>> getCustomerByname(@RequestParam String name){

        return new ResponseEntity<>(customerServices.getCustomerByname(name), HttpStatus.OK);
    }

    @JsonView(Views.simple.class)
    @Cacheable(value = "Customer")
    @GetMapping("/searchCustomerWithCriteria")
    public ResponseEntity<List<Customer>> searchCustomerWithCriteria(CustomerDTO request){
        return new ResponseEntity<>(customerServices.searchCustomerWithCriteria(request),HttpStatus.OK);
    }


//    *************************************ORDER**************************************************************

    @Cacheable(value = "Orders")
    @JsonView(ViewsOrder.simple.class)
    @GetMapping("/searchWithCriteria")
    public ResponseEntity<List<Orders>> searchWithCriteria(OrderDTO request){
        return new ResponseEntity<>(orderServices.searchWithCriteria(request),HttpStatus.OK);
    }

    @Cacheable(value = "Orders")
    @JsonView(ViewsOrder.simple.class)
    @GetMapping("/allOrderByCustomerName")
    public ResponseEntity<List<Orders>> getAllOrderByCustomerName(@RequestParam String name){
        return new ResponseEntity<>(orderServices.allOrderByCustomerName(name),HttpStatus.OK);
    }

    @Cacheable(value = "Orders")
    @JsonView(ViewsOrder.simple.class)
    @GetMapping("/getAllOrderStatus")
    public ResponseEntity<List<Orders>> getAllOrderStatus(@RequestBody OrderDTO dto){
        return new ResponseEntity<>(orderServices.getAllOrderStatus(dto.getEnumOrderStatus()),HttpStatus.OK);
    }

    @Cacheable(value = "Orders")
    @JsonView(ViewsOrder.simple.class)
    @GetMapping("/findOrderByOrderUnique")
    public ResponseEntity<OrderDTO> findOrderByOrder_unique(@RequestParam Integer unique){
        return new ResponseEntity<>(orderServices.findOrderByOrderUnique(unique),HttpStatus.OK);
    }

    @Cacheable(value = "Orders")
    @JsonView(ViewsOrder.simple.class)
    @GetMapping("/getAllOrder")
    public ResponseEntity<List<Orders>> getAllOrder(@RequestParam Integer pageNumber, @RequestParam Integer pageSize){
        return new ResponseEntity<>(orderServices.getAllOrderWithPagination(pageNumber,pageSize),HttpStatus.OK);
    }

    @Cacheable(value = "Orders")
    @GetMapping("/getOrderbetwenTimes")
    public ResponseEntity<List<Orders>> getOrderListForTime(LocalDateDTO dto){
        return new ResponseEntity<>(orderServices.getOrderbetwenTimes(dto),HttpStatus.OK);
    }




//    ************************************Product********************************************************************
    @GetMapping("/countDailyAllProductPrice")
    public ResponseEntity<String> countDailyAllProductPrice(){
        return new ResponseEntity<>(productAllServices.countDailyAllProductPrice(),HttpStatus.OK);
    }
    @JsonView(ViewsOrder.simple.class)
    @GetMapping("/getAllProductSortAndPagination")
    public ResponseEntity<List<Product>> getAllProductSortAndPagination(@RequestParam Integer pageNumber, @RequestParam Integer pageSize, @RequestParam String name){
        return new ResponseEntity<>(productServices.getAllProductSortAndPagination(pageNumber,pageSize,name),HttpStatus.OK);
    }

    @JsonView(ViewsOrder.simple.class)
    @GetMapping("/getAllProductAsc")
    public ResponseEntity<List<Product>> getAllProductAsc(@RequestBody Product product){
        return new ResponseEntity<>(productServices.getDinamikSearchCriteria(product),HttpStatus.OK);
    }




}
