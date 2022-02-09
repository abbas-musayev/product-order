package az.abbas.product.order.db.dto;

import az.abbas.product.order.db.entity.Customer;
import az.abbas.product.order.db.entity.ProductAll;
import az.abbas.product.order.enums.EnumOrderStatus;
import az.abbas.product.order.util.ViewsOrder;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OrderDTO {
    private Long idOrder;

    @JsonView(ViewsOrder.simple.class)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime orderCreatedDate;

    @JsonView(ViewsOrder.simple.class)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime orderFinisDate;

    @JsonView(ViewsOrder.simple.class)
    private String address;

    @JsonView(ViewsOrder.simple.class)
    private Customer customer;

    @JsonView(ViewsOrder.simple.class)
    private Integer order_unique;

    @JsonView(ViewsOrder.simple.class)
    private List<ProductAll> productAll;

    @JsonView(ViewsOrder.simple.class)
    private EnumOrderStatus enumOrderStatus;

}
