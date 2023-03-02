package tw.training.homework.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ImageRequest {

    @NotBlank(message = "The image url can not be null or blank")
    private String url;

    private String alternateText;

}
