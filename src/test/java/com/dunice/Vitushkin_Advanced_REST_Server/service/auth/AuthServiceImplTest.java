package com.dunice.Vitushkin_Advanced_REST_Server.service.auth;

import com.dunice.Vitushkin_Advanced_REST_Server.dto.user.AuthDto;
import com.dunice.Vitushkin_Advanced_REST_Server.dto.user.RegisterUserDto;
import com.dunice.Vitushkin_Advanced_REST_Server.jwt.JwtTokenUtil;
import com.dunice.Vitushkin_Advanced_REST_Server.models.User;
import com.dunice.Vitushkin_Advanced_REST_Server.repository.UserRepository;
import com.dunice.Vitushkin_Advanced_REST_Server.service.auth.AuthServiceImpl;
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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class AuthServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private static JwtTokenUtil jwtTokenUtil;

    @InjectMocks
    private AuthServiceImpl authService;

    private static RegisterUserDto registerDto;
    private static User user;
    private static AuthDto authDto;

    @BeforeAll
    public static void setup() {
         registerDto = RegisterUserDto
                .builder()
                .avatar("path/to/avatar")
                .email("someEmail@mail.ru")
                .name("Some Name")
                .password("Qwerty123!")
                .role("user")
                .build();

         user = User
                .builder()
                .avatar("path/to/avatar")
                .email("someEmail@mail.ru")
                .name("Some Name")
                .password("encodedPassword")
                .role("user")
                .build();

        authDto = new AuthDto();
        authDto.setEmail("someEmail@mail.ru");
        authDto.setPassword("Qwerty123!");
    }

    @Test
    @Epic(value = "Actions on auth")
    @Feature(value = "Register a news.")
    @Description(value = "Proper register of new user")
    public void shouldReturnSuccessResponse() {
        Mockito
                .when(userRepository.existsUserByEmail(registerDto.getEmail()))
                .thenReturn(false);

        Mockito
                .when(passwordEncoder.encode(registerDto.getPassword()))
                        .thenReturn("encodedPassword");

        Mockito
                .when(userRepository.save(user))
                .thenReturn(user);

        Mockito
                .when(jwtTokenUtil.generateToken(user))
                .thenReturn("correctJwtToken");

        var result = authService.register(registerDto);

        assertEquals(true, result.getSuccess());
        assertEquals(1, result.getStatusCode());
        assertEquals("correctJwtToken", result.getData().getToken());
    }

    @Test
    @Epic(value = "Actions on auth")
    @Feature(value = "Register a news.")
    @Description(value = "Incorrect registration of new user, by existing user email")
    public void shouldAssertEntityExist() {
        Mockito
                .when(userRepository.existsUserByEmail(registerDto.getEmail()))
                .thenReturn(true);

        assertThrows(EntityExistsException.class, () -> authService.register(registerDto));
    }

    @Test
    @Epic(value = "Actions on auth")
    @Feature(value = "Login a news.")
    @Description(value = "Proper login of new user")
    public void shouldReturnSuccessLogin() {

        Mockito
                .when(userRepository.findByEmail(authDto.getEmail()))
                .thenReturn(Optional.ofNullable(user));


        Authentication authentication = mock(Authentication.class);
        Mockito
                .when(authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                user.getId(), authDto.getPassword())))
                        .thenReturn(authentication);

        Mockito
                .when(jwtTokenUtil.generateToken(user))
                .thenReturn("correctJwtToken");

        var result = authService.login(authDto);

        assertEquals(true, result.getSuccess());
        assertEquals(1, result.getStatusCode());
        assertEquals("correctJwtToken", result.getData().getToken());
    }

    @Test
    @Epic(value = "Actions on auth")
    @Feature(value = "Login a news.")
    @Description(value = "Incorrect login of new user, by passing non exit email")
    public void shouldAssertEntityNotFound() {
        authDto.setEmail("NonCorrectEmail@mail.ru");

        Mockito
                .when(userRepository.findByEmail(authDto.getEmail()))
                .thenThrow(EntityNotFoundException.class);

        assertThrows(EntityNotFoundException.class, () -> authService.login(authDto));
    }

    @Test
    @Epic(value = "Actions on auth")
    @Feature(value = "Login a news.")
    @Description(value = "Incorrect login of new user, by passing incorrect password")
    public void shouldAssertPasswordNonCorrect() {
        authDto.setPassword("nonCorrectPassword");

        Mockito
                .when(userRepository.findByEmail(authDto.getEmail()))
                .thenReturn(Optional.ofNullable(user));

        Mockito
                .when(authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                user.getId(), authDto.getPassword())))
                .thenThrow(BadCredentialsException.class);

        assertThrows(BadCredentialsException.class, () -> authService.login(authDto));
    }
}
