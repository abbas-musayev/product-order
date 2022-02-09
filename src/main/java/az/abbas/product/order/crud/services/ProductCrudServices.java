package az.abbas.product.order.crud.services;

import az.abbas.product.order.db.dto.ProductDTO;

public interface ProductCrudServices {

    ProductDTO saveProduct(ProductDTO dto);

    ProductDTO updateProduct(ProductDTO dto);

    String deleteProduct(ProductDTO productDTO);
}
