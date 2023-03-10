package tw.training.homework.service;


import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import tw.training.homework.exception.CredentialsIncorrectException;
import tw.training.homework.exception.CustomerNotFoundException;
import tw.training.homework.exception.UsernameExistedException;
import tw.training.homework.model.Customer;
import tw.training.homework.model.request.AccountRequest;
import tw.training.homework.model.response.TokenResponse;
import tw.training.homework.repository.CustomerRepository;
import tw.training.homework.utils.JwtUtils;

import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final CustomerRepository customerRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void createNewAccount(AccountRequest accountRequest) {
        validateUsernameIsRegistered(accountRequest.getUsername());
        Customer customer = new Customer(accountRequest.getUsername(),
                bCryptPasswordEncoder.encode(accountRequest.getPassword()));

        customerRepository.save(customer);
    }

    public TokenResponse getTokenAfterLogin(AccountRequest accountRequest) {
        String username = accountRequest.getUsername();
        Customer customer = customerRepository.findByUsername(username)
                .orElseThrow(() -> new CustomerNotFoundException(
                        String.format("This username: %s is not registered yet.", username)));
        validatePassword(accountRequest.getPassword(), customer.getPassword());
        return new TokenResponse(JwtUtils.generateToken(customer.getId(), username));
    }

    private boolean isUsernameRegistered(String username) {
        return customerRepository.findByUsername(username).isPresent();
    }

    private void validateUsernameIsRegistered(String username) {
        if (isUsernameRegistered(username)) {
            throw new UsernameExistedException(String.format("This username: %s is already registered.", username));
        }
    }

    private void validatePassword(String password, String encodePassword) {
        if (!bCryptPasswordEncoder.matches(password, encodePassword)) {
            throw new CredentialsIncorrectException("The password is incorrect");
        }
    }

}
