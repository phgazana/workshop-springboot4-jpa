package br.com.gazana.course.repositories;

import br.com.gazana.course.entities.OrderItem;
import br.com.gazana.course.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
