package pl.brickrental.order;

import lombok.*;
import pl.brickrental.product.Product;
import pl.brickrental.user.User;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private User user;
    @ManyToOne
    private Product product;
    @Min(7)@Max(28)
    private int rentTime;
    @OneToOne
    private Delivery delivery;
    @OneToOne
    private Payment payment;

    public static Order convert(OrderDTO orderDTO){
        return new Order(orderDTO.id(), User.convert(orderDTO.userDTO()), Product.convert(orderDTO.productDTO()), orderDTO.rentTime(), Delivery.convert(orderDTO.deliveryDTO()), Payment.convert(orderDTO.paymentDTO()));
    }
}
