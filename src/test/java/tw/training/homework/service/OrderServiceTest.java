package tw.training.homework.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tw.training.homework.model.Commodity;
import tw.training.homework.model.Customer;
import tw.training.homework.model.Image;
import tw.training.homework.model.Price;
import tw.training.homework.model.request.CreateOrderRequest;
import tw.training.homework.repository.OrderRepository;
import tw.training.homework.service.domain.CommodityDomainService;
import tw.training.homework.utils.AuthUtils;

import java.util.Arrays;

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

}