package tw.training.homework.config;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import tw.training.homework.model.Customer;
import tw.training.homework.repository.CustomerRepository;

import java.util.Optional;


@Component
@RequiredArgsConstructor
public class UserDetailService implements UserDetailsService {

    private final CustomerRepository customerRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Customer> optionalCustomer = customerRepository.findByUsername(username);
        if (!optionalCustomer.isPresent()) {
            throw new UsernameNotFoundException("The username does not exist");
        }
        Customer customer = optionalCustomer.get();


        return User.withUsername(customer.getUsername())
                .password(customer.getPassword())
                .build();
    }
}
