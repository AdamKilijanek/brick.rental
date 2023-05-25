package pl.brickrental.order;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.brickrental.product.Product;
import pl.brickrental.product.ProductDTO;
import pl.brickrental.product.ProductService;
import pl.brickrental.user.User;
import pl.brickrental.user.UserDTO;
import pl.brickrental.user.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final UserService userService;

    private final ProductService productService;

    private final DeliveryRepository deliveryRepository;

    private final PaymentRepository paymentRepository;


    @Override
    public List<OrderDTO> listAllOrders() {
        return orderRepository.findAll().stream().map(OrderDTO::convert).toList();
    }

    @Override
    public OrderDTO createOrder(OrderDTO orderDTO) {
        UserDTO user = userService.findByEmail(orderDTO.userDTO().email());
        List<ProductDTO> products = productService.findAllByIdIn(orderDTO.productDTOList()
                .stream()
                .map(ProductDTO::id)
                .collect(Collectors.toList()));
        Delivery delivery = deliveryRepository.findByType(orderDTO.deliveryDTO().type())
                .orElseThrow(() -> new IllegalArgumentException("No delivery with type " + orderDTO.deliveryDTO().type()));
        Payment payment = paymentRepository.findByType(orderDTO.paymentDTO().type())
                .orElseThrow(() -> new IllegalArgumentException("No payment with type " + orderDTO.paymentDTO().type()));
        Order order = Order.convert(orderDTO);
        order.setUser(User.convert(user));
        order.setProducts(Product.convert(products));
        order.setDelivery(delivery);
        order.setPayment(payment);
        Order entity = orderRepository.save(order);
        return OrderDTO.convert(entity);
    }

    @Override
    public OrderDTO getById(Long id) {
        return orderRepository.findById(id).map(OrderDTO::convert).orElseThrow(() -> new RuntimeException("No order with id =" + id));
    }

    @Override
    public OrderDTO updateOrder(OrderDTO orderDTO) {
        UserDTO user = userService.findByEmail(orderDTO.userDTO().email());
        List<ProductDTO> products = productService.findAllByIdIn(orderDTO.productDTOList()
                .stream()
                .map(ProductDTO::id)
                .collect(Collectors.toList()));
        Delivery delivery = deliveryRepository.findByType(orderDTO.deliveryDTO().type())
                .orElseThrow(() -> new IllegalArgumentException("No delivery with type " + orderDTO.deliveryDTO().type()));
        Payment payment = paymentRepository.findByType(orderDTO.paymentDTO().type())
                .orElseThrow(() -> new IllegalArgumentException("No payment with type " + orderDTO.paymentDTO().type()));
        return orderRepository.findById(orderDTO.id())
                .map(order -> {
                    order.setUser(User.convert(user));
                    order.setProducts(Product.convert(products));
                    order.setRentTime(orderDTO.rentTime());
                    order.setDelivery(delivery);
                    order.setPayment(payment);
                    return order;
                })
                .map(orderRepository::save)
                .map(OrderDTO::convert)
                .orElseThrow(() -> new RuntimeException("No order with id = " + orderDTO.id()));
    }

    @Override
    public void deleteById(Long id) {
        orderRepository.findById(id);
    }

    @Override
    public List<OrderDTO> findAll(Pageable pageable) {
        return orderRepository.findAll(pageable).stream().map(OrderDTO::convert).toList();
    }
}
