package az.abbas.product.order.db.repo;

import az.abbas.product.order.db.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RepoProduct extends JpaRepository<Product,Long> {
//    @Query("SELECT * FROM fooddetails ORDER BY price ASC")

    @Query("select p from Product p where p.productName=:name")
    Product findProductByName(@Param("name") String name);

    @Query("select p from Product p order by p.productPrice asc")
    List<Product> findProductASC();

    @Query("select p from Product p order by p.productPrice desc ")
    List<Product> findProductDESC();
}
