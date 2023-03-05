package tw.training.homework.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tw.training.homework.model.Commodity;
import tw.training.homework.model.Customer;
import tw.training.homework.model.Order;
import tw.training.homework.model.OrderStatus;
import tw.training.homework.model.request.CreateOrderRequest;
import tw.training.homework.repository.OrderRepository;
import tw.training.homework.service.domain.CommodityDomainService;
import tw.training.homework.utils.AuthUtils;


@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    private final CommodityDomainService commodityDomainService;

    private final AuthUtils authUtils;


    public void createOrder(CreateOrderRequest request) {
        Customer customer = authUtils.getCurrentUser();
        Integer quantity = request.getQuantity();
        Commodity commodity = commodityDomainService.getCommodityById(request.getCommodityId());
        int totalPrice = Math.multiplyExact(quantity, commodity.getPrice().getAmount());

        Order order = new Order(quantity, totalPrice, OrderStatus.CREATED, commodity, customer);
        orderRepository.save(order);
    }
}
