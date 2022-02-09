package az.abbas.product.order.db.dto;

import az.abbas.product.order.db.entity.Customer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AddressDTO {
    private Long idAddress;
    private String addressName;
    private Customer customer;



}
