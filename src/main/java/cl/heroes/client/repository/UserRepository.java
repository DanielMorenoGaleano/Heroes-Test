package cl.heroes.client.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import cl.heroes.client.document.User;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByUsername(String username);
}
