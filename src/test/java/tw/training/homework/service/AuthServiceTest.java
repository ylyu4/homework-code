package tw.training.homework.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import tw.training.homework.exception.UsernameExistedException;
import tw.training.homework.model.Customer;
import tw.training.homework.model.request.AccountRequest;
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

}