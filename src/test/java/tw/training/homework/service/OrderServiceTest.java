package tw.training.homework.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tw.training.homework.exception.OrderNotFoundException;
import tw.training.homework.exception.OrderSubmitMultipleTimesException;
import tw.training.homework.model.Commodity;
import tw.training.homework.model.Customer;
import tw.training.homework.model.Image;
import tw.training.homework.model.Order;
import tw.training.homework.model.OrderStatus;
import tw.training.homework.model.Price;
import tw.training.homework.model.request.CreateOrderRequest;
import tw.training.homework.model.request.ShippingAddressRequest;
import tw.training.homework.model.request.SubmitOrderRequest;
import tw.training.homework.repository.OrderRepository;
import tw.training.homework.service.domain.CommodityDomainService;
import tw.training.homework.utils.AuthUtils;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;


@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @InjectMocks
    OrderService orderService;

    @Mock
    OrderRepository orderRepository;

    @Mock
    CommodityDomainService commodityDomainService;

    @Mock
    AuthUtils authUtils;

    @Test
    void should_create_order_successfully() {
        // given
        Price price = new Price(20);
        Image image1 = new Image("www.xxx.com", "none");
        Image image2 = new Image("www.yyy.com", "none");

        Commodity commodity = new Commodity("sku1", "title1",
                "this is the description",
                price, Arrays.asList(image1, image2));
        CreateOrderRequest request = new CreateOrderRequest(1L, 2);
        when(authUtils.getCurrentUser()).thenReturn(new Customer("username", "password"));
        when(commodityDomainService.getCommodityById(1L)).thenReturn(commodity);

        // when
        orderService.createOrder(request);

        // then
        verify(orderRepository, times(1)).save(any());
    }

    @Test
    void should_submit_order_successfully() {
        // given
        SubmitOrderRequest request = new SubmitOrderRequest(1L, new ShippingAddressRequest("test", "test", "test"));
        Order order = new Order(2, 20, OrderStatus.CREATED);
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        // when
        orderService.submitOrder(request);

        //then
        verify(orderRepository, times(1)).save(any());
    }

    @Test
    void should_throw_exception_for_order_Can_not_submit_for_multiple_times() {
        // given
        SubmitOrderRequest request = new SubmitOrderRequest(1L, new ShippingAddressRequest("test", "test", "test"));
        Order order = new Order(2, 20, OrderStatus.UNPAID);
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));


        //then
        assertThrows(OrderSubmitMultipleTimesException.class, () -> orderService.submitOrder(request), "Order can not be submit for multiple times");
    }

    @Test
    void should_throw_order_not_found_exception() {
        // given
        SubmitOrderRequest request = new SubmitOrderRequest(1L, new ShippingAddressRequest("test", "test", "test"));
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        //then
        assertThrows(OrderNotFoundException.class, () -> orderService.submitOrder(request), "Can not find order by this Id: 1");
    }

}