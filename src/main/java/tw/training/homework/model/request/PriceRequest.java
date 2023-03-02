package tw.training.homework.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PriceRequest {

    @NotNull(message = "The price amount should not be null")
    private Integer amount;

}
