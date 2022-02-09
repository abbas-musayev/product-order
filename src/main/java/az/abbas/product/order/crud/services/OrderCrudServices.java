package az.abbas.product.order.crud.services;

import az.abbas.product.order.db.dto.OrderDTO;
import az.abbas.product.order.db.entity.Orders;

public interface OrderCrudServices {

    OrderDTO saveOrder(OrderDTO orderDTO);

    Orders changeOrderStatus(OrderDTO dto);

    String deleteOrder(Integer unique);

    OrderDTO updateOrder(OrderDTO orderDTO);


}
