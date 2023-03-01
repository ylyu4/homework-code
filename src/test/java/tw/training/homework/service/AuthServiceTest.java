package tw.training.homework.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import tw.training.homework.exception.CredentialsIncorrectException;
import tw.training.homework.exception.CustomerNotFoundException;
import tw.training.homework.exception.UsernameExistedException;
import tw.training.homework.model.Customer;
import tw.training.homework.model.request.AccountRequest;
import tw.training.homework.model.response.TokenResponse;
import tw.training.homework.repository.CustomerRepository;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {


    @InjectMocks
    private AuthService authService;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Test
    void should_create_account_for_customer_successfully() {
        // given
        AccountRequest accountRequest = new AccountRequest("thisIsTheNewUsername", "thisIsThePassword");
        when(customerRepository.findByUsername("thisIsTheNewUsername")).thenReturn(Optional.empty());

        // when
        authService.createNewAccount(accountRequest);

        // then
        verify(bCryptPasswordEncoder, times(1)).encode("thisIsThePassword");
        verify(customerRepository, times(1)).findByUsername("thisIsTheNewUsername");
    }


    @Test
    void should_throw_exception_when_username_is_registered() {
        // given
        AccountRequest accountRequest = new AccountRequest("thisIsTheOldUsername", "thisIsThePassword");
        when(customerRepository.findByUsername("thisIsTheOldUsername")).thenReturn(Optional.of(new Customer()));

        // then
        assertThrows(UsernameExistedException.class,
                () -> authService.createNewAccount(accountRequest),
                "This username: thisIsTheOldUsername is already registered.");
    }

    @Test
    void should_generate_token_when_username_and_password_are_correct() {
        // given
        AccountRequest accountRequest = new AccountRequest("username", "password");
        when(customerRepository.findByUsername("username")).thenReturn(Optional.of(new Customer(1L, "username", "password")));
        when(bCryptPasswordEncoder.matches(any(), any())).thenReturn(true);

        // when
        TokenResponse tokenResponse = authService.getTokenAfterLogin(accountRequest);

        // then
        assertNotNull(tokenResponse.getToken());
    }

    @Test
    void should_throw_exception_when_login_with_a_not_registered_username() {
        // given
        AccountRequest accountRequest = new AccountRequest("username", "password");
        when(customerRepository.findByUsername("username")).thenReturn(Optional.empty());

        // then
        assertThrows(CustomerNotFoundException.class,
                () -> authService.getTokenAfterLogin(accountRequest),
                "This username: username is not registered yet.");
    }

    @Test
    void should_throw_exception_when_login_with_a_wrong_password() {
        // given
        AccountRequest accountRequest = new AccountRequest("username", "password");
        when(customerRepository.findByUsername("username")).thenReturn(Optional.of(new Customer(1L, "username", "password")));
        when(bCryptPasswordEncoder.matches(any(), any())).thenReturn(false);


        // then
        assertThrows(CredentialsIncorrectException.class,
                () -> authService.getTokenAfterLogin(accountRequest),
                "The password is incorrect");
    }





}