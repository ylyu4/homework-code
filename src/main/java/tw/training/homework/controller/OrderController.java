package tw.training.homework.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import tw.training.homework.model.request.CreateOrderRequest;
import tw.training.homework.model.request.SubmitOrderRequest;
import tw.training.homework.service.OrderService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void createCommodityOrder(@RequestBody @Valid CreateOrderRequest createOrderRequest) {
        orderService.createOrder(createOrderRequest);
    }

    @PostMapping("/submit")
    @ResponseStatus(HttpStatus.CREATED)
    public void submitCommodityOrder(@RequestBody @Valid SubmitOrderRequest submitOrderRequest) {
        orderService.submitOrder(submitOrderRequest);
    }

}
