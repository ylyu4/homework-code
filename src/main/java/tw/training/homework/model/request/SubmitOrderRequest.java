package tw.training.homework.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SubmitOrderRequest {

    @NotNull(message = "Order Id can not be null")
    private Long orderId;

    @NotNull(message = "shipping address  can not be null")
    @Valid
    private ShippingAddressRequest shippingAddress;

}
