package az.abbas.product.order.db.repo;

import az.abbas.product.order.db.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepoAddress extends JpaRepository<Address,Long> {
}
