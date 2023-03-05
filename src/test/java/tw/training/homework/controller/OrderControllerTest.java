package tw.training.homework.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tw.training.homework.exception.CommodityNotFoundException;
import tw.training.homework.exception.CustomerForbiddenActionException;
import tw.training.homework.exception.OrderNotFoundException;
import tw.training.homework.exception.OrderSubmitMultipleTimesException;
import tw.training.homework.exception.UsernameExistedException;
import tw.training.homework.model.request.CreateOrderRequest;
import tw.training.homework.model.request.ShippingAddressRequest;
import tw.training.homework.model.request.SubmitOrderRequest;
import tw.training.homework.service.OrderService;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureJsonTesters
public class OrderControllerTest {

    @MockBean
    private OrderService orderService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JacksonTester<CreateOrderRequest> request;


    @Test
    void should_create_order_successfully() throws Exception {

        CreateOrderRequest createOrderRequest = new CreateOrderRequest(1l, 20);

        doNothing().when(orderService).createOrder(any());

        mockMvc.perform(post("/order/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request.write(createOrderRequest).getJson()))
                        .andExpect(status().isCreated());
    }

    @Test
    void should_throw_exception_when_commodity_is_not_found() throws Exception {

        CreateOrderRequest createOrderRequest = new CreateOrderRequest(1l, 20);

        doThrow(new CommodityNotFoundException("Commodity is not found by this id: 1")).when(orderService).createOrder(any());

        mockMvc.perform(post("/order/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request.write(createOrderRequest).getJson()))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof CommodityNotFoundException))
                .andExpect(result -> assertEquals("Commodity is not found by this id: 1", result.getResolvedException().getMessage()));

    }

    @Test
    void should_submit_order_successfully() throws Exception {

        SubmitOrderRequest submitOrderRequest = new SubmitOrderRequest(1l, new ShippingAddressRequest("test", "test", "test"));

        doNothing().when(orderService).createOrder(any());

        mockMvc.perform(post("/order/submit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(submitOrderRequest)))
                .andExpect(status().isCreated());
    }

    @Test
    void should_throw_exception_when_order_is_not_found() throws Exception {

        SubmitOrderRequest submitOrderRequest = new SubmitOrderRequest(1l, new ShippingAddressRequest("test", "test", "test"));

        doThrow(new OrderNotFoundException("Can not find order by this Id: 1")).when(orderService).createOrder(any());

        mockMvc.perform(post("/order/submit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(submitOrderRequest)))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof OrderNotFoundException))
                .andExpect(result -> assertEquals("Can not find order by this Id: 1", result.getResolvedException().getMessage()));
    }

    @Test
    void should_throw_exception_when_order_is_modify_by_other_user() throws Exception {

        SubmitOrderRequest submitOrderRequest = new SubmitOrderRequest(1l, new ShippingAddressRequest("test", "test", "test"));

        doThrow(new CustomerForbiddenActionException("You can not modify other's order")).when(orderService).createOrder(any());

        mockMvc.perform(post("/order/submit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(submitOrderRequest)))
                .andExpect(status().isForbidden())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof CustomerForbiddenActionException))
                .andExpect(result -> assertEquals("You can not modify other's order", result.getResolvedException().getMessage()));
    }

    @Test
    void should_throw_exception_when_order_can_not_submit_for_multiple_times() throws Exception {

        SubmitOrderRequest submitOrderRequest = new SubmitOrderRequest(1l, new ShippingAddressRequest("test", "test", "test"));

        doThrow(new OrderSubmitMultipleTimesException("Order can not be submit for multiple times")).when(orderService).createOrder(any());

        mockMvc.perform(post("/order/submit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(submitOrderRequest)))
                .andExpect(status().isForbidden())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof OrderSubmitMultipleTimesException))
                .andExpect(result -> assertEquals("Order can not be submit for multiple times", result.getResolvedException().getMessage()));
    }


}
