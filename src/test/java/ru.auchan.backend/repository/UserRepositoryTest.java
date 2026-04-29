//package ru.auchan.backend.repository;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.TestPropertySource;
import ru.auchan.backend.entity.User;
//import ru.auchan.backend.exception.ResourceAlreadyExistsException;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.assertj.core.api.Assertions.assertThatThrownBy;
//
//import java.util.Optional;
//
//@DataJpaTest
//class UserRepositoryTest {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Test
//    void shouldFindUserByEmailIgnoreCase() {
//        User user = User.builder()
//                .login("user123")
//                .password("password123")
//                .build();
//        userRepository.save(user);
//
//        Optional<User> found = userRepository.findByLogin("user123");
//
//        assertThat(found).isPresent();
//        assertThat(found.get().getLogin()).isEqualTo("user123");
//    }
//
//    @Test
//    void shouldNotAllowDuplicateEmails() {
//        User user1 = User.builder().login("user11").password("pass11").build();
//        User user2 = User.builder().login("user11").password("user111").build();
//
//        userRepository.save(user1);
//
//        assertThatThrownBy(() -> userRepository.saveAndFlush(user2))
//                .isInstanceOf(DataIntegrityViolationException.class);
//    }
////}
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.dao.DataIntegrityViolationException;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.assertj.core.api.Assertions.assertThatThrownBy;
//
//@DataJpaTest
//@Transactional
//@TestPropertySource(properties = {
//        "spring.flyway.enabled=false",
//        "spring.jpa.hibernate.ddl-auto=create-drop"
//})
//class UserRepositoryTest {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Test
//    void shouldFindUserByLogin() {
//        User user = User.builder()
//                .login("user123")
//                .password("password123")
//                .build();
//        userRepository.save(user);
//
//        Optional<User> found = userRepository.findByLogin("user123");
//
//        assertThat(found).isPresent();
//        assertThat(found.get().getLogin()).isEqualTo("user123");
//    }
//
//    @Test
//    void shouldNotAllowDuplicateLogins() {
//        User user1 = User.builder()
//                .login("user11")
//                .password("pass11")
//                .build();
//        User user2 = User.builder()
//                .login("user11")
//                .password("user111")
//                .build();
//
//        userRepository.save(user1);
//
//        assertThatThrownBy(() -> userRepository.saveAndFlush(user2))
//                .isInstanceOf(DataIntegrityViolationException.class);
//    }
//}