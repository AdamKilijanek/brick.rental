package pl.brickrental.order;

import lombok.*;

import javax.persistence.*;

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
//    @OneToOne
//    private Order order;
//    @Column(name = "pay_on")
//    private LocalDateTime dateTime;
    @Enumerated(EnumType.STRING)
    private PaymentType type;

//    @PrePersist
//    public void prePersist(){
//        this.dateTime = LocalDateTime.now();
//    }
    public static Payment convert(PaymentDTO paymentDTO){
        return new Payment(paymentDTO.id(), paymentDTO.type());
    }
}
