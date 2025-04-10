package tech.backend.events.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.backend.events.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    public User findByEmail(String email);
}
