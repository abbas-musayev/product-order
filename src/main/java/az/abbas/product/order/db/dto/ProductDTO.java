package az.abbas.product.order.db.dto;

import az.abbas.product.order.db.entity.ProductAll;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductDTO {

    private Long idProduct;

    private String productName;
    private Float productPrice;

    private Integer miqdar;
    private ProductAll productAll;
}
