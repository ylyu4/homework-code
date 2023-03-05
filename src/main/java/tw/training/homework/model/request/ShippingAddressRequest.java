package tw.training.homework.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ShippingAddressRequest {

    @NotBlank(message = "The address can not be blank")
    private String address;

    @NotBlank(message = "The full name can not be blank")
    private String fullName;

    @NotBlank(message = "The phone number can not be blank")
    private String phoneNumber;
}
