package az.abbas.product.order.crud.services;

import az.abbas.product.order.db.dto.CustomerDTO;

public interface CustomerCrudServices {

    CustomerDTO saveCustomer(CustomerDTO dto);

    String deleteCustomer(CustomerDTO dto);

    CustomerDTO updateCustomer(CustomerDTO dto);

    CustomerDTO balansArtimi(Long idCustomer,Float newbalance);

    CustomerDTO addAddress(CustomerDTO customerDTO);

    String deleteAddress( CustomerDTO customerDTO);

}
