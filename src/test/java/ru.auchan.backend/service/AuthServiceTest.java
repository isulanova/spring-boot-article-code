package ru.auchan.backend.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.auchan.backend.dto.AuthRequest;
import ru.auchan.backend.dto.AuthResponse;
import ru.auchan.backend.entity.User;
import ru.auchan.backend.exception.InvalidCredentialsException;
import ru.auchan.backend.exception.ResourceAlreadyExistsException;
import ru.auchan.backend.exception.ResourceNotFoundException;
import ru.auchan.backend.repository.UserRepository;
import ru.auchan.backend.security.JwtUtil;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
@DisplayName("Тесты для AuthServiceTest")
public class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Captor
    private ArgumentCaptor<User> userCaptor;

    private User user;
    private AuthRequest authRequest;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(1L)
                .login("login")
                .password("password")
                .build();

        authRequest = AuthRequest.builder()
                .login("login")
                .password("password")
                .build();
    }

    @Nested
    @DisplayName("Создание пользователя")
    class CreateUserTest {

        @Test
        @DisplayName("Успешное создание пользователя")
        void createUserSuccess() {
            when(userRepository.findByLogin("login")).thenReturn(Optional.empty());
            when(userRepository.save(any(User.class))).thenReturn(user);

            User response = userService.register(authRequest.getLogin(), authRequest.getPassword());

            assertThat(response).isNotNull();
            assertThat(response.getLogin()).isEqualTo("login");

            verify(userRepository, times(1)).findByLogin("login");
            verify(userRepository, times(1)).save(any(User.class));

            verifyNoMoreInteractions(userRepository);
        }
    }

    @Test
    @DisplayName("Создание пользователя с существующим логином - ошибка")
    void createUser_AlreadyExists_ThrowsException() {
        when(userRepository.findByLogin(authRequest.getLogin())).thenReturn(Optional.of(user));

        assertThatThrownBy(() -> userService.register(authRequest.getLogin(), authRequest.getPassword()))
                .isInstanceOf(ResourceAlreadyExistsException.class)
                .hasMessageContaining("already exists");

        verify(userRepository, never()).save(any(User.class));
    }

    @Nested
    @DisplayName("Получение токена")
    class GetTokenTest {

        @Test
        @DisplayName("Успешное получение токена")
        void getTokenSuccess() {
            when(userRepository.findByLogin("login")).thenReturn(Optional.of(user));
            when(passwordEncoder.matches("password", user.getPassword())).thenReturn(true);
            AuthResponse response = userService.login(authRequest.getLogin(), authRequest.getPassword());

            assertThat(response).isNotNull();
            assertThat(response.getLogin()).isEqualTo("login");

            verify(userRepository, times(1)).findByLogin("login");
        }
    }

    @Test
    @DisplayName("Получение токена для несуществуюшего пользователя - ошибка")
    void getUser_NotFound_ThrowsException() {
        when(userRepository.findByLogin("user123")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.login("user123", "pass123"))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("not found");
    }

    @Test
    @DisplayName("Получение токена для пользователя с неправильным паролем - ошибка")
    void getUser_WrongPassword_ThrowsException() {
        when(userRepository.findByLogin(authRequest.getLogin())).thenReturn(Optional.of(user));

        assertThatThrownBy(() -> userService.login(authRequest.getLogin(), "pass123"))
                .isInstanceOf(InvalidCredentialsException.class);
    }
}
