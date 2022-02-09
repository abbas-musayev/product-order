package az.abbas.product.order.search.services;

import az.abbas.product.order.db.entity.Product;

import java.util.List;

public interface ProductSearchServices {


    List<Product> getAllProductSortAndPagination(Integer pageNumber, Integer pageSize, String name);

    List<Product> getDinamikSearchCriteria(Product request);


}
