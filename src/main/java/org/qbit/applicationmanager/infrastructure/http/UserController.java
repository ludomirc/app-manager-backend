package org.qbit.applicationmanager.infrastructure.http;

import org.qbit.applicationmanager.domain.model.User;
import org.qbit.applicationmanager.domain.service.AuthenticationService;
import org.qbit.applicationmanager.domain.service.UserService;
import org.qbit.applicationmanager.infrastructure.http.dto.UserDto;
import org.qbit.applicationmanager.infrastructure.http.dto.mapper.UserMapper;
import org.qbit.applicationmanager.security.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private static final String JWT_TOKEN_NAME = "token";

    private final UserService userService;
    private final UserMapper userMapper;
    private final JwtService jwtService;
    private final AuthenticationService authenticationService;

    public UserController(UserService userService,
                          UserMapper userMapper,
                          JwtService jwtService,
                          AuthenticationService authenticationService) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto) {
        User user = userService.registerUser(userDto.getUserName(), userDto.getRawPassword());
        return ResponseEntity.ok(userMapper.toDto(user));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<Map<String, String>> authenticateUser(@RequestBody UserDto userDto) {
        Optional<User> userOpt = userService.getUserByUserName(userDto.getUserName());
        if (userOpt.isPresent() && authenticationService.verifyPassword(userDto.getRawPassword(), userOpt.get().getPasswordHash())) {
            String token = jwtService.generateToken(userDto.getUserName());
            return ResponseEntity.ok(Map.of(JWT_TOKEN_NAME, token));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
