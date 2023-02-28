package tw.training.homework.service;


import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import tw.training.homework.exception.UsernameExistedException;
import tw.training.homework.model.Customer;
import tw.training.homework.model.request.AccountRequest;
import tw.training.homework.repository.CustomerRepository;

import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final CustomerRepository customerRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final AtomicLong customerId = new AtomicLong();

    public void createNewAccount(AccountRequest accountRequest) {
        validateUsernameIsRegistered(accountRequest.getUsername());

        Customer customer = new Customer(customerId.incrementAndGet(), accountRequest.getUsername(),
                bCryptPasswordEncoder.encode(accountRequest.getPassword()));

        customerRepository.save(customer);
    }

    private boolean isUsernameRegistered(String username) {
        return customerRepository.findByUsername(username).isPresent();
    }

    private void validateUsernameIsRegistered(String username) {
        if (isUsernameRegistered(username)) {
            throw new UsernameExistedException(String.format("This username: %s is already registered.", username));
        }
    }

}
