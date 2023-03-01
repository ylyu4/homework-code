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
import tw.training.homework.exception.UsernameExistedException;
import tw.training.homework.model.request.AccountRequest;
import tw.training.homework.service.AuthService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

        mockMvc.perform(post("/user/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request.write(accountRequest).getJson()))
                .andExpect(status().isOk());
    }

    @Test
    void should_throw_exception_when_username_is_taken() throws Exception {
        AccountRequest accountRequest = new AccountRequest("username", "password");

        doThrow(new UsernameExistedException("This username: username is already registered.")).when(authService).createNewAccount(any());

        mockMvc.perform(post("/user/signup")
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

        mockMvc.perform(post("/user/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request.write(accountRequest).getJson()))
                .andExpect(status().isBadRequest());
    }

    @Test
    void should_throw_exception_when_password_is_invalid() throws Exception {
        AccountRequest accountRequest = new AccountRequest("username", "");

        doNothing().when(authService).createNewAccount(any());

        mockMvc.perform(post("/user/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request.write(accountRequest).getJson()))
                .andExpect(status().isBadRequest());
    }

}
