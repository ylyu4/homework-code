package tw.training.homework.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import tw.training.homework.exception.CustomerNotFoundException;
import tw.training.homework.model.Customer;
import tw.training.homework.repository.CustomerRepository;

@Component
@RequiredArgsConstructor
public class AuthUtils {

    private final CustomerRepository customerRepository;

    public Customer getCurrentUser() {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return customerRepository.findByUsername(username)
                .orElseThrow(() -> new CustomerNotFoundException("Can not find token with the current token"));
    }

}
