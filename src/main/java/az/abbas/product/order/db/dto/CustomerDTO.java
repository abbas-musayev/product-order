package az.abbas.product.order.db.dto;

import az.abbas.product.order.db.entity.Address;
import az.abbas.product.order.db.entity.Orders;
import az.abbas.product.order.util.Views;
import az.abbas.product.order.util.ViewsOrder;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CustomerDTO {
    @JsonView(Views.details.class)
    private Long idCustomer;

    @JsonView({Views.simple.class, ViewsOrder.simple.class})
    private String name;

    @JsonView({Views.simple.class,ViewsOrder.simple.class})
    private String surname;

    @JsonView(Views.simple.class)
    private String username;

    @JsonView(Views.simple.class)
    private String password;

    @JsonView(Views.simple.class)
    private Float balance;

    @JsonView(Views.simple.class)
    private List<Address> address;

    @JsonView(Views.simple.class)
    private LocalDateTime createdTimeCustomer;

    @JsonView(Views.details.class)
    private List<Orders> orders;
}
