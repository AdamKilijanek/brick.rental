package pl.brickrental.order;

import lombok.*;
import pl.brickrental.delivery.Delivery;
import pl.brickrental.payment.Payment;
import pl.brickrental.product.Product;
import pl.brickrental.user.User;

import javax.persistence.*;

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
    private int rentTime;
    @OneToOne
    private Delivery delivery;
    @OneToOne
    private Payment payment;
}
