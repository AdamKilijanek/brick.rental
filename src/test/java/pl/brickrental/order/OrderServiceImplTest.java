package pl.brickrental.order;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.brickrental.product.*;
import pl.brickrental.user.UserDTO;
import pl.brickrental.user.UserService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

class OrderServiceImplTest {

    private OrderRepository orderRepository;

    private OrderService orderService;

    private ProductService productService;

    private UserService userService;

    private DeliveryRepository deliveryRepository;

    private PaymentRepository paymentRepository;

    @BeforeEach
    public void setUp() {
        orderRepository = Mockito.mock(OrderRepository.class);
        deliveryRepository = Mockito.mock(DeliveryRepository.class);
        paymentRepository = Mockito.mock(PaymentRepository.class);
        productService = Mockito.mock(ProductService.class);
        userService = Mockito.mock(UserService.class);
        orderService = new OrderServiceImpl(orderRepository, userService, productService, deliveryRepository, paymentRepository);
    }

    @Test
    public void givenNewOrder_whenAdd_thenReturnSavedOrder() {
        UserDTO userDTO = new UserDTO(1L, "John", "Doe", 18, "john.doe@gmail.com", "John1");
        Mockito.when(userService.findByEmail(userDTO.email())).thenReturn(userDTO);

        Product product1 = new Product(1L, new Category(1L, CategoryType.TECHNIC), "42199", 1999, 199.99);
        Product product2 = new Product(2L, new Category(1L, CategoryType.TECHNIC), "42199", 1999, 199.99);
        List<Product> products = List.of(product1, product2);
        Mockito.when(productService.findAllByIdIn(products.stream().map(Product::getId).collect(Collectors.toList())))
                .thenReturn(products.stream().map(ProductDTO::convert).collect(Collectors.toList()));

        Delivery delivery = Delivery.builder().id(1L).type(DeliveryType.POST).build();
        Mockito.when(deliveryRepository.findByType(delivery.getType())).thenReturn(Optional.of(delivery));

        Payment payment = Payment.builder().id(1L).type(PaymentType.CARD).build();
        Mockito.when(paymentRepository.findByType(payment.getType())).thenReturn(Optional.of(payment));

        OrderDTO orderDTO = new OrderDTO(1L, userDTO,
                List.of(ProductDTO.convert(product1), ProductDTO.convert(product2)),
                21, new DeliveryDTO(1L, DeliveryType.POST), new PaymentDTO(1L, PaymentType.CARD));
        Mockito.when(orderRepository.save(any())).thenReturn(Order.convert(orderDTO));

        OrderDTO actual = orderService.createOrder(orderDTO);

        assertEquals(orderDTO, actual);
        Mockito.verify(orderRepository, Mockito.times(1)).save(any(Order.class));
    }
    @Test
    public void givenNewOrder_whenAddWithWrongPayment_thenFail() {
        UserDTO userDTO = new UserDTO(1L, "John", "Doe", 18, "john.doe@gmail.com", "John1");
        Mockito.when(userService.findByEmail(userDTO.email())).thenReturn(userDTO);

        Product product1 = new Product(1L, new Category(1L, CategoryType.TECHNIC), "42199", 1999, 199.99);
        Product product2 = new Product(2L, new Category(1L, CategoryType.TECHNIC), "42199", 1999, 199.99);
        List<Product> products = List.of(product1, product2);
        Mockito.when(productService.findAllByIdIn(products.stream().map(Product::getId).collect(Collectors.toList())))
                .thenReturn(products.stream().map(ProductDTO::convert).collect(Collectors.toList()));

        Delivery delivery = Delivery.builder().id(1L).type(DeliveryType.POST).build();
        Mockito.when(deliveryRepository.findByType(delivery.getType())).thenReturn(Optional.of(delivery));

        Mockito.when(paymentRepository.findByType(PaymentType.CARD)).thenReturn(Optional.empty());

        OrderDTO orderDTO = new OrderDTO(1L, userDTO,
                List.of(ProductDTO.convert(product1), ProductDTO.convert(product2)),
                21, new DeliveryDTO(1L, DeliveryType.POST), new PaymentDTO(1L, PaymentType.CARD));
        Mockito.when(orderRepository.save(any())).thenReturn(Order.convert(orderDTO));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> orderService.createOrder(orderDTO));

        assertThat(exception).hasMessage("No payment with type CARD");
    }
    @Test
    public void givenNewOrder_whenAddWithWrongDelivery_thenFail(){
        UserDTO userDTO = new UserDTO(1L, "John", "Doe", 18, "john.doe@gmail.com", "John1");
        Mockito.when(userService.findByEmail(userDTO.email())).thenReturn(userDTO);

        Product product1 = new Product(1L, new Category(1L, CategoryType.TECHNIC), "42199", 1999, 199.99);
        Product product2 = new Product(2L, new Category(1L, CategoryType.TECHNIC), "42199", 1999, 199.99);
        List<Product> products = List.of(product1, product2);
        Mockito.when(productService.findAllByIdIn(products.stream().map(Product::getId).collect(Collectors.toList())))
                .thenReturn(products.stream().map(ProductDTO::convert).collect(Collectors.toList()));

        Mockito.when(deliveryRepository.findByType(DeliveryType.POST)).thenReturn(Optional.empty());

        OrderDTO orderDTO = new OrderDTO(1L, userDTO,
                List.of(ProductDTO.convert(product1), ProductDTO.convert(product2)),
                21, new DeliveryDTO(1L, DeliveryType.POST), new PaymentDTO(1L, PaymentType.CARD));
        Mockito.when(orderRepository.save(any())).thenReturn(Order.convert(orderDTO));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> orderService.createOrder(orderDTO));

        assertThat(exception).hasMessage("No delivery with type POST");
    }
    @Test
    public void givenOrder_whenUpdateOrder_thenReturnSavedOrder(){
        UserDTO userDTO = new UserDTO(1L, "John", "Doe", 18, "john.doe@gmail.com", "John1");
        Mockito.when(userService.findByEmail(userDTO.email())).thenReturn(userDTO);

        Product product1 = new Product(1L, new Category(1L, CategoryType.TECHNIC), "42199", 1999, 199.99);
        Product product2 = new Product(2L, new Category(1L, CategoryType.TECHNIC), "42199", 1999, 199.99);
        List<Product> products = List.of(product1, product2);
        Mockito.when(productService.findAllByIdIn(products.stream().map(Product::getId).collect(Collectors.toList())))
                .thenReturn(products.stream().map(ProductDTO::convert).collect(Collectors.toList()));

        Delivery delivery = Delivery.builder().id(1L).type(DeliveryType.POST).build();
        Mockito.when(deliveryRepository.findByType(delivery.getType())).thenReturn(Optional.of(delivery));

        Payment payment = Payment.builder().id(1L).type(PaymentType.CARD).build();
        Mockito.when(paymentRepository.findByType(payment.getType())).thenReturn(Optional.of(payment));

        OrderDTO orderDTO = new OrderDTO(1L, userDTO,
                List.of(ProductDTO.convert(product1), ProductDTO.convert(product2)),
                21, new DeliveryDTO(1L, DeliveryType.POST), new PaymentDTO(1L, PaymentType.CARD));
        Mockito.when(orderRepository.save(any())).thenReturn(Order.convert(orderDTO));

        Mockito.when(orderRepository.findById(orderDTO.id())).thenReturn(Optional.of(Order.convert(orderDTO)));

        OrderDTO actual = orderService.updateOrder(orderDTO);

        assertEquals(orderDTO, actual);
        Mockito.verify(orderRepository, Mockito.times(1)).save(any(Order.class));
    }
    @Test
    public void givenOrder_whenUpdateOrderWithWrongDelivery_thenFail(){
        UserDTO userDTO = new UserDTO(1L, "John", "Doe", 18, "john.doe@gmail.com", "John1");
        Mockito.when(userService.findByEmail(userDTO.email())).thenReturn(userDTO);

        Product product1 = new Product(1L, new Category(1L, CategoryType.TECHNIC), "42199", 1999, 199.99);
        Product product2 = new Product(2L, new Category(1L, CategoryType.TECHNIC), "42199", 1999, 199.99);
        List<Product> products = List.of(product1, product2);
        Mockito.when(productService.findAllByIdIn(products.stream().map(Product::getId).collect(Collectors.toList())))
                .thenReturn(products.stream().map(ProductDTO::convert).collect(Collectors.toList()));

        Mockito.when(deliveryRepository.findByType(DeliveryType.POST)).thenReturn(Optional.empty());

        OrderDTO orderDTO = new OrderDTO(1L, userDTO,
                List.of(ProductDTO.convert(product1), ProductDTO.convert(product2)),
                21, new DeliveryDTO(1L, DeliveryType.POST), new PaymentDTO(1L, PaymentType.CARD));
        Mockito.when(orderRepository.save(any())).thenReturn(Order.convert(orderDTO));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> orderService.updateOrder(orderDTO));

        assertThat(exception).hasMessage("No delivery with type POST");
    }
    @Test
    public void givenOrder_whenUpdateOrderWithWrongPayment_thenFail(){
        UserDTO userDTO = new UserDTO(1L, "John", "Doe", 18, "john.doe@gmail.com", "John1");
        Mockito.when(userService.findByEmail(userDTO.email())).thenReturn(userDTO);

        Product product1 = new Product(1L, new Category(1L, CategoryType.TECHNIC), "42199", 1999, 199.99);
        Product product2 = new Product(2L, new Category(1L, CategoryType.TECHNIC), "42199", 1999, 199.99);
        List<Product> products = List.of(product1, product2);
        Mockito.when(productService.findAllByIdIn(products.stream().map(Product::getId).collect(Collectors.toList())))
                .thenReturn(products.stream().map(ProductDTO::convert).collect(Collectors.toList()));

        Delivery delivery = Delivery.builder().id(1L).type(DeliveryType.POST).build();
        Mockito.when(deliveryRepository.findByType(delivery.getType())).thenReturn(Optional.of(delivery));

        Mockito.when(paymentRepository.findByType(PaymentType.CARD)).thenReturn(Optional.empty());

        OrderDTO orderDTO = new OrderDTO(1L, userDTO,
                List.of(ProductDTO.convert(product1), ProductDTO.convert(product2)),
                21, new DeliveryDTO(1L, DeliveryType.POST), new PaymentDTO(1L, PaymentType.CARD));
        Mockito.when(orderRepository.save(any())).thenReturn(Order.convert(orderDTO));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> orderService.updateOrder(orderDTO));

        assertThat(exception).hasMessage("No payment with type CARD");
    }
    @Test
    public void  givenOrder_whenUpdateWithNonExistingIdProduct_thenFail(){
        UserDTO userDTO = new UserDTO(1L, "John", "Doe", 18, "john.doe@gmail.com", "John1");
        Mockito.when(userService.findByEmail(userDTO.email())).thenReturn(userDTO);

        Product product1 = new Product(1L, new Category(1L, CategoryType.TECHNIC), "42199", 1999, 199.99);
        Product product2 = new Product(2L, new Category(1L, CategoryType.TECHNIC), "42199", 1999, 199.99);
        List<Product> products = List.of(product1, product2);
        Mockito.when(productService.findAllByIdIn(products.stream().map(Product::getId).collect(Collectors.toList())))
                .thenReturn(products.stream().map(ProductDTO::convert).collect(Collectors.toList()));

        Delivery delivery = Delivery.builder().id(1L).type(DeliveryType.POST).build();
        Mockito.when(deliveryRepository.findByType(delivery.getType())).thenReturn(Optional.of(delivery));

        Payment payment = Payment.builder().id(1L).type(PaymentType.CARD).build();
        Mockito.when(paymentRepository.findByType(payment.getType())).thenReturn(Optional.of(payment));

        OrderDTO orderDTO = new OrderDTO(1L, userDTO,
                List.of(ProductDTO.convert(product1), ProductDTO.convert(product2)),
                21, new DeliveryDTO(1L, DeliveryType.POST), new PaymentDTO(1L, PaymentType.CARD));
        Mockito.when(orderRepository.save(any())).thenReturn(Order.convert(orderDTO));

        Mockito.when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> orderService.updateOrder(orderDTO));

        assertThat(exception).hasMessage("No order with id = 1");
    }
    @Test
    public void givenOrder_whenGetById_thenFail(){
        Mockito.when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> orderService.getById(1L));

        assertThat(exception).hasMessage("No order with id =1");
    }
}