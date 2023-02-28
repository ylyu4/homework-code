package tw.training.homework.repository;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import tw.training.homework.model.Customer;

import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
@AutoConfigureTestEntityManager
class CustomerRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void should_find_the_existed_customer() {
        // given
        Customer customer = new Customer(1L, "username", "password");
        entityManager.persist(customer);

        // when
        Optional<Customer> optionalCustomer = customerRepository.findByUsername("username");

        // then
        assertTrue(optionalCustomer.isPresent());
        assertEquals("username", optionalCustomer.get().getUsername());
        assertEquals("password", optionalCustomer.get().getPassword());
    }
}
