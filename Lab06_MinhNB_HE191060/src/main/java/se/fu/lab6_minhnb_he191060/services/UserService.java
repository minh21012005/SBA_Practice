package se.fu.lab6_minhnb_he191060.services;

import org.springframework.stereotype.Service;
import se.fu.lab6_minhnb_he191060.entities.User;
import se.fu.lab6_minhnb_he191060.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> allUsers() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        return users;
    }
}
