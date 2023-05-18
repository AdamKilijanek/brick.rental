package pl.brickrental.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.brickrental.product.Product;
import pl.brickrental.product.ProductRepository;
import pl.brickrental.user.User;
import pl.brickrental.user.UserRepository;

import java.util.List;
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final UserRepository userRepository;

    private final ProductRepository productRepository;

    private final DeliveryRepository deliveryRepository;

    private final PaymentRepository paymentRepository;


    @Override
    public List<OrderDTO> listAllOrders() {
        return orderRepository.findAll().stream().map(OrderDTO::convert).toList();
    }

    @Override
    public OrderDTO createOrder(OrderDTO orderDTO) {
        User user = userRepository.findByEmail(orderDTO.userDTO().email())
                .orElseThrow(()-> new IllegalArgumentException("No user with email " + orderDTO.userDTO().email()));
        Product product = productRepository.findById(orderDTO.paymentDTO().id())
                .orElseThrow(()-> new IllegalArgumentException("No product with id = " + orderDTO.productDTO().id()));
        Delivery delivery = deliveryRepository.findByType(orderDTO.deliveryDTO().type())
                .orElseThrow(()-> new IllegalArgumentException("No delivery with type " + orderDTO.deliveryDTO().type()));
        Payment payment = paymentRepository.findByType(orderDTO.paymentDTO().type())
                .orElseThrow(()-> new IllegalArgumentException("No payment with type " + orderDTO.paymentDTO().type()));
        Order order = Order.convert(orderDTO);
        order.setUser(user);
        order.setProduct(product);
        order.setDelivery(delivery);
        order.setPayment(payment);
        Order entity = orderRepository.save(order);
        return OrderDTO.convert(entity);
    }

    @Override
    public OrderDTO getById(Long id) {
        return orderRepository.findById(id).map(OrderDTO::convert).orElseThrow(()-> new RuntimeException("No order with id =" + id));
    }

    @Override
    public OrderDTO updateOrder(OrderDTO orderDTO) {
        User user = userRepository.findByEmail(orderDTO.userDTO().email())
                .orElseThrow(()-> new IllegalArgumentException("No user with email " + orderDTO.userDTO().email()));
        Product product = productRepository.findById(orderDTO.paymentDTO().id())
                .orElseThrow(()-> new IllegalArgumentException("No product with id = " + orderDTO.productDTO().id()));
        Delivery delivery = deliveryRepository.findByType(orderDTO.deliveryDTO().type())
                .orElseThrow(()-> new IllegalArgumentException("No delivery with type " + orderDTO.deliveryDTO().type()));
        Payment payment = paymentRepository.findByType(orderDTO.paymentDTO().type())
                .orElseThrow(()-> new IllegalArgumentException("No payment with type " + orderDTO.paymentDTO().type()));
        return orderRepository.findById(orderDTO.id())
                .map(order -> {
                    order.setUser(user);
                    order.setProduct(product);
                    order.setRentTime(orderDTO.rentTime());
                    order.setDelivery(delivery);
                    order.setPayment(payment);
                    return order;
                })
                .map(orderRepository::save)
                .map(OrderDTO::convert)
                .orElseThrow(()-> new RuntimeException("No order with id = " + orderDTO.id()));
    }

    @Override
    public void deleteById(Long id) {
        orderRepository.findById(id);
    }
}
