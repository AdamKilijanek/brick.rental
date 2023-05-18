package pl.brickrental.order;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/shop/orders")
@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("{id}")
    public OrderDTO getById(@PathVariable Long id){
        return orderService.getById(id);
    }

    @PostMapping
    public OrderDTO createOrder(@RequestBody OrderDTO order){
        return orderService.createOrder(order);
    }

    @PutMapping("{id}")
    public OrderDTO updateOrder(@RequestBody OrderDTO order){
        return orderService.updateOrder(order);
    }
    @DeleteMapping("{id}")
    public void deleteById(@PathVariable Long id){
        orderService.deleteById(id);
    }

    @GetMapping("/")
    public List<OrderDTO> getAllOrders(){
        return orderService.listAllOrders();
    }
}
