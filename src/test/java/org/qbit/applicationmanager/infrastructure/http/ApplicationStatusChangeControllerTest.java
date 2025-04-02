package org.qbit.applicationmanager.infrastructure.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.qbit.applicationmanager.domain.model.Application;
import org.qbit.applicationmanager.domain.model.ApplicationStatus;
import org.qbit.applicationmanager.domain.model.ApplicationStatusChange;
import org.qbit.applicationmanager.domain.model.User;
import org.qbit.applicationmanager.domain.service.ApplicationService;
import org.qbit.applicationmanager.domain.service.ApplicationStatusChangeService;
import org.qbit.applicationmanager.domain.service.ApplicationStatusChangeServiceImpl;
import org.qbit.applicationmanager.infrastructure.http.dto.ApplicationStatusChangeDto;

import org.qbit.applicationmanager.infrastructure.http.dto.mapper.ApplicationStatusChangeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SuppressWarnings("removal")
@WebMvcTest(ApplicationStatusChangeController.class)
class ApplicationStatusChangeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ApplicationService applicationService;

    @MockBean
    private ApplicationStatusChangeService statusChangeService;

    @MockBean
    private ApplicationStatusChangeMapper mapper;

    @Autowired
    private ObjectMapper objectMapper;


    @TestConfiguration
    static class MockConfig {
        @Bean
        public ApplicationService applicationService() {
            return mock(ApplicationService.class);
        }

        @Bean
        public ApplicationStatusChangeService applicationStatusChangeService() {
            return mock(ApplicationStatusChangeService.class);
        }
    }

    @Test
    void shouldLogStatusChange() throws Exception {
        Long applicationId = 1L;
        Application application = new Application();

        Field filedApplicationId = Application.class.getDeclaredField("applicationId");
        filedApplicationId.setAccessible(true);
        filedApplicationId.set(application, 1L);

        ApplicationStatusChange change = new ApplicationStatusChange(application, ApplicationStatus.APPLIED);
        change.setChangedAt(LocalDateTime.now());

        ApplicationStatusChangeDto inputDto = new ApplicationStatusChangeDto("APPLIED", null);
        ApplicationStatusChangeDto responseDto = new ApplicationStatusChangeDto("APPLIED", change.getChangedAt());

        Mockito.when(applicationService.getApplicationById(applicationId)).thenReturn(Optional.of(application));
        Mockito.when(statusChangeService.logStatusChange(application, ApplicationStatus.APPLIED)).thenReturn(change);
        Mockito.when(mapper.toDto(change)).thenReturn(responseDto);

        mockMvc.perform(post("/api/statuses/{applicationId}", applicationId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("APPLIED"))
                .andExpect(jsonPath("$.changedAt").exists());
    }
}
