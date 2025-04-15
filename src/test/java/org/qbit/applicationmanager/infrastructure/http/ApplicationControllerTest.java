package org.qbit.applicationmanager.infrastructure.http;

import org.junit.jupiter.api.Test;
import org.qbit.applicationmanager.domain.model.Application;
import org.qbit.applicationmanager.domain.model.ApplicationStatus;
import org.qbit.applicationmanager.domain.model.Enterprise;
import org.qbit.applicationmanager.domain.model.User;
import org.qbit.applicationmanager.domain.service.*;
import org.qbit.applicationmanager.infrastructure.http.dto.ApplicationDto;
import org.qbit.applicationmanager.infrastructure.http.dto.mapper.ApplicationMapper;
import org.qbit.applicationmanager.infrastructure.http.dto.mapper.TaskMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.lang.reflect.Field;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SuppressWarnings("removal")
@WebMvcTest(ApplicationController.class)
@Import(ApplicationControllerTest.MockConfig.class)
class ApplicationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ApplicationService applicationService;

    @MockBean
    private UserService userService;

    @MockBean
    private EnterpriseService enterpriseService;

    @MockBean
    private ApplicationMapper applicationMapper;

    @MockBean
    private ApplicationStatusChangeService applicationStatusChangeService;

    @MockBean
    private TaskService taskService;

    @MockBean
    private TaskMapper taskMapper;

    @TestConfiguration
    static class MockConfig {
        @Bean
        public ApplicationService applicationService() {
            return mock(ApplicationService.class);
        }
    }

    @Test
    @WithMockUser(username = "testUser", roles = "USER")
    void shouldGetApplicationById() throws Exception {
        // Given
        User user = new User("testUser", "hashedPassword");
        Field field = User.class.getDeclaredField("userId");
        field.setAccessible(true);
        field.set(user, 1L);

        Enterprise enterprise = new Enterprise("Test Enterprise", user);
        Application application = new Application(user, enterprise, "Test Notes","Test Name", ApplicationStatus.DRAFT);

        // Mock service calls
        when(userService.getUserByUsername("testUser")).thenReturn(user);
        when(applicationService.getApplicationById(1L)).thenReturn(Optional.of(application));
        when(applicationMapper.toDto(application)).thenReturn(
                new ApplicationDto(1L, null, "Test Enterprise", application.getCreationDate(), "Test Notes", "Test name", ApplicationStatus.DRAFT.name())
        );

        // When + Then
        mockMvc.perform(get("/api/applications/1"))
                .andExpect(status().isOk());
    }

}
