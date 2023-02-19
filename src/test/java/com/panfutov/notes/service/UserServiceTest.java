package com.panfutov.notes.service;

import com.panfutov.notes.dto.auth0.UserInfo;
import com.panfutov.notes.entity.User;
import com.panfutov.notes.repository.UserRepository;
import com.panfutov.notes.service.exception.UserNotFoundException;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    public static final UserInfo USER_INFO = new UserInfo("John", "j@dummy.com");
    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TokenExtractor tokenExtractor;

    @Mock
    private Auth0Service auth0Service;

    @Test
    void testGetUserByInfoWhenUserIsNotFound() {
        // GIVEN
        when(userRepository.findBySub(anyString())).thenReturn(Optional.empty());

        // WHEN
        Executable command = () -> userService.getUserByUserInfo(USER_INFO);

        // THEN
        assertThrows(UserNotFoundException.class, command);
    }

    @Test
    void testGetUserByInfo() {
        // GIVEN
        var user = new User();
        when(userRepository.findBySub(USER_INFO.sub())).thenReturn(Optional.of(user));

        // WHEN
        var result = userService.getUserByUserInfo(USER_INFO);

        // THEN
        assertEquals(user, result);
    }

}