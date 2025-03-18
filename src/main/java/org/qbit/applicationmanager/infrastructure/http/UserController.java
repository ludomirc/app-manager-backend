package org.qbit.applicationmanager.infrastructure.http;

import org.qbit.applicationmanager.domain.model.User;
import org.qbit.applicationmanager.domain.service.UserService;
import org.qbit.applicationmanager.infrastructure.http.dto.UserDto;
import org.qbit.applicationmanager.infrastructure.http.dto.mapper.UserMapper;
import org.qbit.applicationmanager.security.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final JwtService jwtService;

    public UserController(UserService userService, UserMapper userMapper, JwtService jwtService) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto) {
        User user = userService.registerUser(userDto.getUserName(), userDto.getRawPassword());
        return ResponseEntity.ok(userMapper.toDto(user));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<Map<String, String>> authenticateUser(@RequestBody UserDto userDto) {
        boolean authenticated = userService.authenticateUser(userDto.getUserName(), userDto.getRawPassword());
        if (authenticated) {
            String token = jwtService.generateToken(userDto.getUserName());
            return ResponseEntity.ok(Map.of("token", token));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
