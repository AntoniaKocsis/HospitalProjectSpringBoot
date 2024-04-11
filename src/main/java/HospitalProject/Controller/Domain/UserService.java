package HospitalProject.Controller.Domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    public boolean save(User user) {
        List<User> userList = (List<User>) repository.findAll();
        List<String> userNames = new ArrayList<>();
        userList.stream().map(User::getUsername).forEach(userNames::add);
        if (userNames.contains(user.getUsername())) {
            return false;
        }
        List<String> emails = new ArrayList<>();
        userList.stream().map(User::getEmail).forEach(emails::add);
        if (emails.contains(user.getEmail()))
            return false;
        repository.save(user);
        return true;
    }

    public User get(Integer id) throws UserNotFoundException {
        Optional<User> result = repository.findById(id);
        if (result.isPresent()) {
            return result.get();
        }
        throw new UserNotFoundException("Could not find any Users with ID " + id);
    }

    public void delete(Integer id) throws UserNotFoundException {
        Long count = repository.countById(id);
        if (count == null || count == 0) {
            throw new UserNotFoundException("Could not find any Users with ID " + id);
        }
        repository.deleteById(id);

    }

    public Optional<User> findByUsername(String username) {
        return repository.findByUsername(username);
    }
}
