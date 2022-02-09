package az.abbas.product.order.search.services;

import az.abbas.product.order.db.dto.CustomerDTO;
import az.abbas.product.order.db.entity.Customer;

import java.util.List;

public interface CustomerSearchServices {

    List<CustomerDTO> getAllCustomer();

    List<CustomerDTO> getCustomerByname(String name);

    List<Customer> searchCustomerWithCriteria(CustomerDTO dto);



    }
