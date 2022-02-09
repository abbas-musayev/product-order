package az.abbas.product.order.db.repo;

import az.abbas.product.order.db.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RepoCustomer extends JpaRepository<Customer,Long> {


    @Query("select c from Customer c join fetch c.address where c.name=:name")
    List<Customer> getCustomerByName(@Param("name") String name);

    @Query("select c from Customer c join fetch c.address where c.surname=:surname and c.name=:name")
    Customer getCustomerBySurnameAndName(@Param("surname") String surname,@Param("name") String name);

    @Query("select c from Customer c join fetch c.address where c.username=:username")
    Customer getCustomerByUsername(@Param("username") String username);

    @Query("select c from Customer c join fetch c.address")
    List<Customer> getAllCustomer();

}