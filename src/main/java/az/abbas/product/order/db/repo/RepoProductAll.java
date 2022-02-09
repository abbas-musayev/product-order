package az.abbas.product.order.db.repo;

import az.abbas.product.order.db.entity.ProductAll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RepoProductAll extends JpaRepository<ProductAll,Long> {


    @Query("select p.productPrice from ProductAll p")
    List<Float> countAllProductAllPrice();



}
