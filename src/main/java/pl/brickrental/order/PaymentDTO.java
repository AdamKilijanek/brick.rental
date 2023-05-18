package pl.brickrental.order;


public record PaymentDTO(Long id, PaymentType type) {

    public static PaymentDTO convert(Payment payment){
        return new PaymentDTO(payment.getId(), payment.getType());
    }
}
