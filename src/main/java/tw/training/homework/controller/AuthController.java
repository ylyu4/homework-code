package tw.training.homework.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import tw.training.homework.model.request.AccountRequest;
import tw.training.homework.model.response.TokenResponse;
import tw.training.homework.service.AuthService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public void signUpForUser(@RequestBody @Valid AccountRequest accountRequest) {
        authService.createNewAccount(accountRequest);
    }

    @GetMapping("/login")
    public TokenResponse login(@RequestBody AccountRequest accountRequest) {
        return authService.getTokenAfterLogin(accountRequest);
    }

}
