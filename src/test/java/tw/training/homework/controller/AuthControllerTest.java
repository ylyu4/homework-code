package tw.training.homework.controller;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tw.training.homework.exception.CredentialsIncorrectException;
import tw.training.homework.exception.CustomerNotFoundException;
import tw.training.homework.exception.UsernameExistedException;
import tw.training.homework.model.request.AccountRequest;
import tw.training.homework.model.response.TokenResponse;
import tw.training.homework.service.AuthService;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureJsonTesters
public class AuthControllerTest {

    @MockBean
    private AuthService authService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JacksonTester<AccountRequest> request;

    @Test
    void should_register_new_account_successfully() throws Exception {
        AccountRequest accountRequest = new AccountRequest("username", "password");

        doNothing().when(authService).createNewAccount(any());

        mockMvc.perform(post("/users/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request.write(accountRequest).getJson()))
                .andExpect(status().isCreated());
    }

    @Test
    void should_throw_exception_when_username_is_taken() throws Exception {
        AccountRequest accountRequest = new AccountRequest("username", "password");

        doThrow(new UsernameExistedException("This username: username is already registered.")).when(authService).createNewAccount(any());

        mockMvc.perform(post("/users/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request.write(accountRequest).getJson()))
                        .andExpect(status().isBadRequest())
                        .andExpect(result -> assertTrue(result.getResolvedException() instanceof UsernameExistedException))
                        .andExpect(result -> assertEquals("This username: username is already registered.", result.getResolvedException().getMessage()));
    }

    @Test
    void should_throw_exception_when_username_is_invalid() throws Exception {
        AccountRequest accountRequest = new AccountRequest("", "password");

        doNothing().when(authService).createNewAccount(any());

        mockMvc.perform(post("/users/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request.write(accountRequest).getJson()))
                .andExpect(status().isBadRequest());
    }

    @Test
    void should_throw_exception_when_password_is_invalid() throws Exception {
        AccountRequest accountRequest = new AccountRequest("username", "");

        doNothing().when(authService).createNewAccount(any());

        mockMvc.perform(post("/users/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request.write(accountRequest).getJson()))
                .andExpect(status().isBadRequest());
    }

    @Test
    void should_get_token_with_correct_username_and_password() throws Exception {
        AccountRequest accountRequest = new AccountRequest("username", "password");

        when(authService.getTokenAfterLogin(any())).thenReturn(new TokenResponse("this is the token"));

        mockMvc.perform(get("/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request.write(accountRequest).getJson()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.token", containsString("this is the token")));
    }

    @Test
    void should_throw_exception_when_login_with_unregistered_username() throws Exception {
        AccountRequest accountRequest = new AccountRequest("username", "password");

        when(authService.getTokenAfterLogin(any())).thenThrow(new CustomerNotFoundException("This username: username is not registered yet."));

        mockMvc.perform(get("/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request.write(accountRequest).getJson()))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof CustomerNotFoundException))
                .andExpect(result -> assertEquals("This username: username is not registered yet.", result.getResolvedException().getMessage()));
    }

    @Test
    void should_throw_exception_when_login_with_wrong_password() throws Exception {
        AccountRequest accountRequest = new AccountRequest("username", "password");

        when(authService.getTokenAfterLogin(any())).thenThrow(new CredentialsIncorrectException("The password is incorrect"));

        mockMvc.perform(get("/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request.write(accountRequest).getJson()))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof CredentialsIncorrectException))
                .andExpect(result -> assertEquals("The password is incorrect", result.getResolvedException().getMessage()));
    }

}
