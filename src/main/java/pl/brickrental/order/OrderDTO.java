package pl.brickrental.order;

import pl.brickrental.product.ProductDTO;
import pl.brickrental.user.UserDTO;

import java.util.List;
import java.util.stream.Collectors;


public record OrderDTO(Long id, UserDTO userDTO, List<ProductDTO> productDTOList, int rentTime, DeliveryDTO deliveryDTO,
                       PaymentDTO paymentDTO) {

    public static OrderDTO convert(Order order) {
        return new OrderDTO(order.getId(), UserDTO.convert(order.getUser()), order.getProducts().stream().map(ProductDTO::convert).collect(Collectors.toList()), order.getRentTime(), DeliveryDTO.convert(order.getDelivery()), PaymentDTO.convert(order.getPayment()));
    }
}
