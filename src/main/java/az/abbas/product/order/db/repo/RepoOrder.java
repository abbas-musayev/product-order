package az.abbas.product.order.db.repo;

import az.abbas.product.order.db.entity.Orders;
import az.abbas.product.order.enums.EnumOrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RepoOrder extends JpaRepository<Orders,Long> {

//    @Query("select OrderDTO from Order o where o.customer.name=:name")
//    OrderDTO getOrderByCustomer(@Param("name") String name);


    //bir istifadecinin butun sifarisleri
    @Query("select o from Orders o join fetch o.customer c where c.name=:name")
    List<Orders> AllOrderByCustomerName(@Param("name") String name);

    @Query("select o from Orders o where o.order_unique=:unique")
    Orders findOrderByOrderUnique(@Param("unique") Integer unique);

    @Query("select o from Orders o where o.enumOrderStatus=:enum1")
    List<Orders> getAllOrder(@Param("enum1") EnumOrderStatus enum1);

}
