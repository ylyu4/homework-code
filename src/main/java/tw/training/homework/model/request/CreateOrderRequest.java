package tw.training.homework.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderRequest {

    @NotNull(message = "Commodity Id can not be null")
    private Long commodityId;

    @NotNull(message = "Quantity Id can not be null")
    private Integer quantity;


}
