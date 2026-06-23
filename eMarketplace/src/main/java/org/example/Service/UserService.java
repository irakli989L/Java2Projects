package org.example.Service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.validator.routines.EmailValidator;
import org.example.Dto.RegisterRequest;
import org.example.Entity.User;
import org.example.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public void register(RegisterRequest request) {
        if (request.getUsername() == null || !request.getUsername().matches("^[a-zA-Z0-9]{8,20}$")) {
            throw new IllegalArgumentException("Username must only contain letters or numbers and between 8 to 20 characters.");
        }

        if (request.getEmail() == null || !EmailValidator.getInstance().isValid(request.getEmail())) {
            throw new IllegalArgumentException("Invalid Email address format.");
        }

        if (request.getBirthday() == null || request.getBirthday().toString().isEmpty()) {
            throw new IllegalArgumentException("Birthday is required.");
        }
        LocalDate bday = request.getBirthday();
        if (Period.between(bday, LocalDate.now()).getYears() <= 13) {
            throw new IllegalArgumentException("You must be older than 13 years old to sign up.");
        }


        if (userRepository.findByUsername(request.getUsername()).isPresent() || userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Username or Email already registered.");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setBirthday(bday);
        userRepository.save(user);
    }

    public User login(String loginIdentifier, String password) {
        User user = userRepository.findByUsernameOrEmail(loginIdentifier, loginIdentifier)
                .orElseThrow(() -> new IllegalArgumentException("User matching credential records not found."));

        if (!user.getPassword().equals(password)) {
            throw new IllegalArgumentException("Incorrect password string introduced.");
        }
        return user;
    }
}
