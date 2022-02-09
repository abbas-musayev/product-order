package az.abbas.product.order.search.services;

import az.abbas.product.order.db.dto.LocalDateDTO;
import az.abbas.product.order.db.dto.OrderDTO;
import az.abbas.product.order.db.entity.Orders;
import az.abbas.product.order.enums.EnumOrderStatus;

import java.util.List;

public interface OrderSearchServices {

    List<Orders> searchWithCriteria(OrderDTO request);

    List<Orders> allOrderByCustomerName(String name);

    OrderDTO findOrderByOrderUnique(Integer unique);

    List<Orders> getAllOrderStatus(EnumOrderStatus enums);

    List<Orders> getAllOrderWithPagination(Integer pageNumber, Integer pageSize);

    List<Orders> getOrderbetwenTimes(LocalDateDTO request);



}
