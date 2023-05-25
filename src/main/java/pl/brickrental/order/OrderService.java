package pl.brickrental.order;

import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService {

    List<OrderDTO> listAllOrders();

    OrderDTO createOrder(OrderDTO order);

    OrderDTO getById(Long id);

    OrderDTO updateOrder(OrderDTO orderDTO);

    void deleteById(Long id);

    List<OrderDTO> findAll(Pageable pageable);
}
