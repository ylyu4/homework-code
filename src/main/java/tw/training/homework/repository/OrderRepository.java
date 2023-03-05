package tw.training.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tw.training.homework.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
