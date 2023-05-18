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
@Table(name = "deliveries")
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private DeliveryType type;

    public static Delivery convert(DeliveryDTO deliveryDTO){
        return new Delivery(deliveryDTO.id(), deliveryDTO.type());
    }
}
