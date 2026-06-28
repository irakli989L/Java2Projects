package org.example.Service;

import org.example.Dto.RegisterRequest;
import org.example.Entity.User;
import org.example.Repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private RegisterRequest validRequest;
    private User mockUser;

    @BeforeEach
    void setUp() {
        validRequest = new RegisterRequest();
        validRequest.setUsername("ikakoshubitttt");
        validRequest.setEmail("ikakabadze989@gmail.com");
        validRequest.setPassword("aaaaaaaa");
        validRequest.setBirthday(LocalDate.now().minusYears(20));

        mockUser = new User();
        mockUser.setId(1L);
        mockUser.setUsername("ikakoshubitttt");
        mockUser.setEmail("ikakabadze989@gmail.com");
        mockUser.setPassword("aaaaaaaa");
    }

    @Test
    void register_Success() {
        when(userRepository.findByUsername(validRequest.getUsername())).thenReturn(Optional.empty());
        when(userRepository.findByEmail(validRequest.getEmail())).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(mockUser);

        assertDoesNotThrow(() -> userService.register(validRequest));

        verify(userRepository, times(1)).findByUsername(validRequest.getUsername());
        verify(userRepository, times(1)).findByEmail(validRequest.getEmail());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void register_ThrowsException_WhenUserAlreadyExists() {
        when(userRepository.findByUsername(validRequest.getUsername())).thenReturn(Optional.of(mockUser));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.register(validRequest);
        });

        assertEquals("Username or Email already registered.", exception.getMessage());

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void login_Success() {
        when(userRepository.findByUsernameOrEmail("ikakoshubitttt", "ikakoshubitttt")).thenReturn(Optional.of(mockUser));

        User result = userService.login("ikakoshubitttt", "aaaaaaaa");

        assertNotNull(result);
        assertEquals("ikakoshubitttt", result.getUsername());
        assertTrue(result.getId() > 0);

        verify(userRepository, times(1)).findByUsernameOrEmail("ikakoshubitttt", "ikakoshubitttt");
    }

    @Test
    void login_ThrowsException_WhenUserNotFound() {
        when(userRepository.findByUsernameOrEmail("unknownUser", "unknownUser"))
                .thenThrow(new IllegalArgumentException("User matching credential records not found."));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.login("unknownUser", "password");
        });

        assertEquals("User matching credential records not found.", exception.getMessage());
        verify(userRepository, times(1)).findByUsernameOrEmail("unknownUser", "unknownUser");
    }
}
