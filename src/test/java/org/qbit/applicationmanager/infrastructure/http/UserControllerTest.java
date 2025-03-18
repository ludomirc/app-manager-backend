package org.qbit.applicationmanager.infrastructure.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.qbit.applicationmanager.domain.model.User;
import org.qbit.applicationmanager.domain.service.UserService;
import org.qbit.applicationmanager.infrastructure.http.dto.UserDto;
import org.qbit.applicationmanager.infrastructure.http.dto.mapper.UserMapper;
import org.qbit.applicationmanager.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.test.context.support.WithMockUser;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@SuppressWarnings("removal")
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @MockBean
    private UserMapper userMapper;

    @MockBean
    private JwtService jwtService;

    @Test
    @WithMockUser
    void shouldRegisterUser() throws Exception {
        // Arrange
        UserDto requestDto = new UserDto("testUser", "rawPassword");
        User user = new User("testUser", "hashedPassword");
        UserDto responseDto = new UserDto("testUser", null);

        when(userService.registerUser("testUser", "rawPassword")).thenReturn(user);
        when(userMapper.toDto(user)).thenReturn(responseDto);

        // Act & Assert
        mockMvc.perform(post("/api/users/register")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userName").value("testUser"))
                .andExpect(jsonPath("$.rawPassword").doesNotExist());
    }
}
