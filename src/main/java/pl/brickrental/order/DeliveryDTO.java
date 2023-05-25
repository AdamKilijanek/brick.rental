package pl.brickrental.order;

public record DeliveryDTO(Long id, DeliveryType type) {

    public static DeliveryDTO convert(Delivery delivery) {
        return new DeliveryDTO(delivery.getId(), delivery.getType());
    }
}
