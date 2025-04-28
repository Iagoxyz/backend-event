package tech.backend.events.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.backend.events.model.Event;
import tech.backend.events.model.Subscription;
import tech.backend.events.model.User;

public interface SubscriptionRepository extends JpaRepository<Subscription, Integer> {
    public Subscription findByEventAndSubscriber(Event event, User user);
}
