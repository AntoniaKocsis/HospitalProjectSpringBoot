package HospitalProject.Controller.Domain;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer> {
    public Long countById(Integer id);

    Optional<User> findByUsername(String username);
}
