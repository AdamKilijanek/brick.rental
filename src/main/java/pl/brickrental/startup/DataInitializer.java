package pl.brickrental.startup;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.brickrental.product.Category;
import pl.brickrental.product.CategoryRepository;
import pl.brickrental.product.CategoryType;
import pl.brickrental.order.Delivery;
import pl.brickrental.order.DeliveryRepository;
import pl.brickrental.order.DeliveryType;
import pl.brickrental.order.Payment;
import pl.brickrental.order.PaymentRepository;
import pl.brickrental.order.PaymentType;

@Component
@Slf4j
@RequiredArgsConstructor
public class DataInitializer {

  private final CategoryRepository categoryRepository;

  private final DeliveryRepository deliveryRepository;

  private final PaymentRepository paymentRepository;

  @EventListener
  @Transactional
  public void loadInitialData(ContextRefreshedEvent unused) {

    categoryRepository.save(new Category(1L, CategoryType.TECHNIC));
    categoryRepository.save(new Category(2L, CategoryType.CITY));
    categoryRepository.save(new Category(3L, CategoryType.IDEAS));
    categoryRepository.save(new Category(4L, CategoryType.CREATOR));

    deliveryRepository.save(new Delivery(1L, DeliveryType.POST));
    deliveryRepository.save(new Delivery(2L, DeliveryType.COURIER));
    deliveryRepository.save(new Delivery(3L, DeliveryType.PERSONAL_COLLECTION));

    paymentRepository.save(new Payment(1L, PaymentType.CARD));
    paymentRepository.save(new Payment(2L, PaymentType.TRANSFER));
    paymentRepository.save(new Payment(3L, PaymentType.FAST_TRANSFER));
    paymentRepository.save(new Payment(4L, PaymentType.CASH_ON_DELIVERY));
  }
}
