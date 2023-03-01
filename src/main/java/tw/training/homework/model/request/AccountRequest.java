package tw.training.homework.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AccountRequest {

    @NotEmpty(message = "Username cannot be null or empty")
    @Size(max = 20, message = "The max username length is 20")
    private String username;

    @NotEmpty(message = "Password cannot be null or empty")
    @Size(max = 20, message = "The max password length is 20")
    private String password;

}
