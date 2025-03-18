package org.qbit.applicationmanager.infrastructure.http;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.qbit.applicationmanager.domain.model.Application;
import org.qbit.applicationmanager.domain.model.Enterprise;
import org.qbit.applicationmanager.domain.model.Task;
import org.qbit.applicationmanager.domain.model.User;
import org.qbit.applicationmanager.domain.service.EnterpriseService;

import org.qbit.applicationmanager.domain.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.test.context.support.WithMockUser;

import java.time.LocalDateTime;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TaskService taskService;

    @InjectMocks
    private TaskController taskController;

    @TestConfiguration
    static class MockConfig {
        @Bean
        public TaskService taskService() {
            return mock(TaskService.class);
        }
    }

    @Test
    @WithMockUser(username = "testUser", roles = "USER")
    void shouldGetTaskById() throws Exception {
        Task task = new Task(new User("testUser", "hashedPassword"),
                new Application(new User("testUser", "hashedPassword"),
                        new Enterprise("Test Enterprise", new User("testUser", "hashedPassword")),
                        "Test Notes","Test Name"),
                LocalDateTime.now().plusDays(1),
                "Task Note");
        when(taskService.getTaskById(1L)).thenReturn(Optional.of(task));

        mockMvc.perform(get("/api/tasks/1"))
                .andExpect(status().isOk());
    }
}
