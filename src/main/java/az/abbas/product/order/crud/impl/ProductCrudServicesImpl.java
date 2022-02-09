package az.abbas.product.order.crud.impl;

import az.abbas.product.order.crud.services.ProductCrudServices;
import az.abbas.product.order.db.dto.ProductDTO;
import az.abbas.product.order.db.entity.Product;
import az.abbas.product.order.db.repo.RepoProduct;
import az.abbas.product.order.exception.ThrowNotFoundExpHandle;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductCrudServicesImpl implements ProductCrudServices {
    private final RepoProduct repoProduct;

    public ProductDTO saveProduct(ProductDTO dto){
        Product product = Product.builder()
                .idProduct(dto.getIdProduct())
                .productName(dto.getProductName())
                .productPrice(dto.getProductPrice())
                .miqdar(dto.getMiqdar())
                .build();

        repoProduct.save(product);

        return ProductDTO.builder()
                .idProduct(product.getIdProduct())
                .productName(product.getProductName())
                .productPrice(product.getProductPrice())
                .build();
    }

    public ProductDTO updateProduct(ProductDTO dto){

        Product product1 = repoProduct.findProductByName(dto.getProductName());

        Product product = Product.builder()
                .idProduct(product1.getIdProduct())
                .productName(dto.getProductName())
                .productPrice(dto.getProductPrice())
                .miqdar((product1.getMiqdar()+dto.getMiqdar()))
                .build();

        repoProduct.save(product);

        return ProductDTO.builder()
                .idProduct(product.getIdProduct())
                .productName(product.getProductName())
                .productPrice(product.getProductPrice())
                .build();
    }


    public String deleteProduct(ProductDTO productDTO){

        Product productByName = repoProduct.findProductByName(productDTO.getProductName());

        if (productByName==null){
            throw new ThrowNotFoundExpHandle("Silinmek Ucun Mehsul Tapilmadi name->"+productDTO.getProductName());
        }else {
            repoProduct.delete(productByName);

            return productDTO.getProductName() + " Adli Mehsul Ugurla Silindi";
        }
    }


}
