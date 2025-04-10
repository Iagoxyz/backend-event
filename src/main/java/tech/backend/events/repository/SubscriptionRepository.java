package tech.backend.events.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.backend.events.model.Subscription;

public interface SubscriptionRepository extends JpaRepository<Subscription, Integer> {
}
