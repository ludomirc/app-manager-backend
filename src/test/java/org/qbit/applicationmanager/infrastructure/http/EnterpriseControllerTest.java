package org.qbit.applicationmanager.infrastructure.http;

import org.junit.jupiter.api.Test;
import org.qbit.applicationmanager.domain.model.Enterprise;
import org.qbit.applicationmanager.domain.model.User;
import org.qbit.applicationmanager.domain.service.EnterpriseService;

import org.qbit.applicationmanager.domain.service.UserService;
import org.qbit.applicationmanager.infrastructure.http.dto.EnterpriseDto;
import org.qbit.applicationmanager.infrastructure.http.dto.mapper.EnterpriseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.Optional;


@SuppressWarnings("removal")
@WebMvcTest(EnterpriseController.class)
class EnterpriseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EnterpriseService enterpriseService;

    @MockBean
    private UserService userService;

    @MockBean
    private EnterpriseMapper enterpriseMapper;

    @Test
    @WithMockUser(username = "testUser", roles = "USER")
    void shouldGetEnterpriseById() throws Exception {

        Enterprise enterprise = new Enterprise("Test Enterprise", new User("testUser", "hashedPassword"));
        EnterpriseDto dto = new EnterpriseDto();
        dto.setName("Test Enterprise");

        when(enterpriseService.getEnterpriseById(1L)).thenReturn(Optional.of(enterprise));
        when(enterpriseMapper.toDto(enterprise)).thenReturn(dto); // ✅ Mock the mapper

        mockMvc.perform(get("/api/enterprises/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Enterprise"));
    }

}
