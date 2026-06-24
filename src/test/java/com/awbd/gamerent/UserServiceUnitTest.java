package com.awbd.gamerent;

import com.awbd.gamerent.exception.ResourceNotFoundException;
import com.awbd.gamerent.model.User;
import com.awbd.gamerent.repository.UserRepository;
import com.awbd.gamerent.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceUnitTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("GamerX");
        testUser.setPassword("parolaClara");
    }

    @Test
    void testFindAllUsers() {
        when(userRepository.findAll()).thenReturn(Arrays.asList(testUser));

        List<User> users = userService.findAllUsers();

        assertEquals(1, users.size());
        assertEquals("GamerX", users.get(0).getUsername());
    }

    @Test
    void testFindUserById_Success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));

        User foundUser = userService.findUserById(1L);

        assertNotNull(foundUser);
        assertEquals("GamerX", foundUser.getUsername());
    }

    @Test
    void testSaveUser() {
        when(passwordEncoder.encode(anyString())).thenReturn("parolaCriptata123");
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        User savedUser = userService.saveUser(testUser);

        assertNotNull(savedUser);
        assertEquals("parolaCriptata123", testUser.getPassword());
        verify(userRepository, times(1)).save(testUser);
    }

    @Test
    void testDeleteUser() {
        doNothing().when(userRepository).deleteById(1L);

        userService.deleteUser(1L);

        verify(userRepository, times(1)).deleteById(1L);
    }
}