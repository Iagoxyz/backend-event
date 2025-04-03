package tech.backend.events.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.backend.events.model.Event;

public interface EventRepository extends JpaRepository<Event, Integer> {
    public Event findByPrettyName(String prettyName);
}
