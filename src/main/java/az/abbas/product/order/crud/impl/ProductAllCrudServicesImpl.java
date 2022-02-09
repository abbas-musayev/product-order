package az.abbas.product.order.crud.impl;

import az.abbas.product.order.crud.services.ProductAllCrudServices;
import az.abbas.product.order.db.entity.ProductAll;
import az.abbas.product.order.db.repo.RepoProductAll;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductAllCrudServicesImpl implements ProductAllCrudServices {


    private final RepoProductAll repoProductAll;


    public String deleteProductAll(Long id){

        ProductAll productAll = repoProductAll.findById(id).get();

        repoProductAll.delete(productAll);
        return "Product All ("+id+") Silindi";
    }


}
