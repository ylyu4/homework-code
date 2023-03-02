package tw.training.homework.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tw.training.homework.model.Image;
import tw.training.homework.model.Price;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommodityRequest {

    @NotEmpty(message = "Username cannot be null or empty")
    @Size(max = 40, message = "The max username length is 20")
    private String sku;

    @NotEmpty(message = "Username cannot be null or empty")
    @Size(max = 40, message = "The max username length is 20")
    private String title;

    @Size(max = 200, message = "The max description length is 200")
    private String description;

    @NotNull(message = "The price field should not be null")
    @Valid
    private PriceRequest price;

    @NotNull(message = "The images should not be null")
    @Valid
    private List<ImageRequest> images;
}
