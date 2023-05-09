package pl.brickrental.payment;

import lombok.*;
import pl.brickrental.order.Order;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private Order order;
    @Column(name = "pay_on")
    private LocalDateTime dateTime;
    @Enumerated(EnumType.STRING)
    private PaymentMethod method;

    @PrePersist
    public void prePersist(){
        this.dateTime = LocalDateTime.now();
    }
}
