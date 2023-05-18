package pl.brickrental.order;

import pl.brickrental.product.ProductDTO;
import pl.brickrental.user.UserDTO;

public record OrderDTO(Long id, UserDTO userDTO, ProductDTO productDTO, int rentTime, DeliveryDTO deliveryDTO, PaymentDTO paymentDTO) {

    public static OrderDTO convert(Order order){
        return new OrderDTO(order.getId(), UserDTO.convert(order.getUser()), ProductDTO.convert(order.getProduct()), order.getRentTime(), DeliveryDTO.convert(order.getDelivery()), PaymentDTO.convert(order.getPayment()));
    }
}
