package az.abbas.product.order.db.dto;

import az.abbas.product.order.db.entity.Orders;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductAllDTO {
    private Long idProductList;

    private String productName;

    private Float productPrice;

    private Orders orders;
}
