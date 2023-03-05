package tw.training.homework.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import tw.training.homework.model.Customer;
import tw.training.homework.repository.CustomerRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthUtilsTest {

    @InjectMocks
    AuthUtils authUtils;

    @Mock
    CustomerRepository customerRepository;


    @Test
    void should_get_current_user_successfully() {
        // given
        Customer customer = new Customer("username", "password");
        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn("username");
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(customerRepository.findByUsername("username")).thenReturn(Optional.of(customer));

        // when
        Customer currentUser = authUtils.getCurrentUser();

        // then
        assertEquals(currentUser.getUsername(), "username");
        assertEquals(currentUser.getPassword(), "password");

    }

}