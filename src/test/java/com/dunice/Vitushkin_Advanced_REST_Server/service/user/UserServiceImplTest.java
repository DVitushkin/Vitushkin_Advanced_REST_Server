package com.dunice.Vitushkin_Advanced_REST_Server.service.user;

import com.dunice.Vitushkin_Advanced_REST_Server.dto.user.PutUserDto;
import com.dunice.Vitushkin_Advanced_REST_Server.models.User;
import com.dunice.Vitushkin_Advanced_REST_Server.repository.UserRepository;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    private static UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private static User user;

    private static Principal mockPrincipal;

    private static PutUserDto putUserDto;

    private static final UUID correctUUID = UUID.fromString("50e8400-e29b-41d4-a716-446655440000");
    private static final UUID incorrectUUID = UUID.fromString("50e8400-1337-228e-a716-446655440000");


    @BeforeAll
    static void setUp() {
        user = User
                .builder()
                .avatar("path/to/avatar")
                .email("defaultEmail@mail.ru")
                .name("Default Name")
                .password("encodedPassword")
                .role("user")
                .build();

        putUserDto = new PutUserDto();
        putUserDto.setAvatar("path/to/new/avatar");
        putUserDto.setEmail("NewEmail@mail.ru");
        putUserDto.setName("New Name");
        putUserDto.setRole("user");


        mockPrincipal = mock(Principal.class);
        Mockito
                .when(mockPrincipal.getName())
                .thenReturn(String.valueOf(correctUUID));

    }

    @Test
    @Epic(value = "Actions on user service")
    @Feature(value = "GET user info")
    @Description(value = "Was putted propper put dto data")
    public void shouldReturnSuccessUserView() {
        Mockito
                .when(userRepository.findById(correctUUID))
                .thenReturn(Optional.ofNullable(user));


        var result = userService.getUserInfo(mockPrincipal);

        assertEquals(true, result.getSuccess());
        assertEquals(1, result.getStatusCode());
        assertEquals("defaultEmail@mail.ru", result.getData().getEmail());
    }

    @Test
    @Epic(value = "Actions on user service")
    @Feature(value = "PUT user info")
    @Description(value = "Was putted horosho")
    public void shouldReturnSuccessPutResponse() {
        Mockito
                .when(userRepository.findById(correctUUID))
                .thenReturn(Optional.ofNullable(user));

        Mockito
                .when(userRepository.existsUserByEmail(putUserDto.getEmail()))
                .thenReturn(false);

        Mockito
                .when(userRepository.save(user))
                .thenReturn(user);


        var result = userService.putUserInfo(mockPrincipal, putUserDto);

        assertEquals(true, result.getSuccess());
        assertEquals(1, result.getStatusCode());
        assertEquals("New Name", result.getData().getName());
    }

    @Test
    @Epic(value = "Actions on user service")
    @Feature(value = "PUT user info")
    @Description(value = "Was putted already used email")
    public void shouldEntityExistsException() {
        Mockito
                .when(userRepository.findById(correctUUID))
                .thenReturn(Optional.ofNullable(user));

        Mockito
                .when(userRepository.existsUserByEmail(putUserDto.getEmail()))
                .thenReturn(true);

        user.setEmail("defaultEmail@mail.ru");
        assertThrows(EntityExistsException.class, () -> userService.putUserInfo(mockPrincipal, putUserDto));
    }

    @Test
    @Epic(value = "Actions on user service")
    @Feature(value = "GET all user info")
    @Description(value = "Returning correct list")
    public void shouldReturnSuccessListUserView() {
        Mockito
                .when(userRepository.findAll())
                .thenReturn(Arrays.asList(
                        User
                                .builder()
                                .avatar("path/to/avatar1")
                                .email("defaultEmail1@mail.ru")
                                .name("First Default Name1")
                                .password("encodedPassword1")
                                .role("user")
                                .build(),
                        User
                                .builder()
                                .avatar("path/to/avatar2")
                                .email("defaultEmail2@mail.ru")
                                .name("Second Default Name")
                                .password("encodedPassword2")
                                .role("user")
                                .build()
                ));

        var result = userService.getAllUserInfo();

        assertEquals(true, result.getSuccess());
        assertEquals(1, result.getStatusCode());
        assertEquals(2, result.getData().size());
    }

    @Test
    @Epic(value = "Actions on user service")
    @Feature(value = "GET user by id")
    @Description(value = "Returning user by correct ID")
    public void shouldReturnSuccessUserViewById() {
        Mockito
                .when(userRepository.findById(correctUUID))
                .thenReturn(Optional.ofNullable(user));

        var result = userService.getUserById(correctUUID);

        assertEquals(true, result.getSuccess());
        assertEquals(1, result.getStatusCode());
        assertEquals("Default Name", result.getData().getName());
    }

    @Test
    @Epic(value = "Actions on user service")
    @Feature(value = "GET user by id")
    @Description(value = "Was putted incorrect ID")
    public void shouldReturnEntityNotFoundExc() {
        Mockito
                .when(userRepository.findById(incorrectUUID))
                .thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.getUserById(incorrectUUID));
    }

    @Test
    @Epic(value = "Actions on user service")
    @Feature(value = "DELETE user")
    @Description(value = "Correct deleting")
    public void shouldReturnBaseSuccess() {
        Mockito
                .when(userRepository.findById(correctUUID))
                .thenReturn(Optional.ofNullable(user));

        user.setId(correctUUID);
        doNothing().when(userRepository).deleteById(user.getId());

        var result = userService.deleteUser(mockPrincipal);
        assertEquals(true, result.getSuccess());
        assertEquals(1, result.getStatusCode());
    }
}