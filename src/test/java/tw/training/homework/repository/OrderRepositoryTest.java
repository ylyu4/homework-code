package tw.training.homework.repository;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import tw.training.homework.model.Order;
import tw.training.homework.model.OrderStatus;

import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
@AutoConfigureTestEntityManager
class OrderRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private OrderRepository orderRepository;

    @Test
    void should_find_the_existed_order() {
        // given
        Order order = new Order(2, 20, OrderStatus.CREATED);
        entityManager.persist(order);

        // when
        Optional<Order> optionalOrder = orderRepository.findById(1L);

        // then
        assertTrue(optionalOrder.isPresent());
    }
}
