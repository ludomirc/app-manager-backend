package org.qbit.applicationmanager.infrastructure.http;

import org.junit.jupiter.api.Test;
import org.qbit.applicationmanager.domain.model.Application;
import org.qbit.applicationmanager.domain.model.Enterprise;
import org.qbit.applicationmanager.domain.model.Task;
import org.qbit.applicationmanager.domain.model.User;
import org.qbit.applicationmanager.domain.service.ApplicationService;
import org.qbit.applicationmanager.domain.service.TaskService;
import org.qbit.applicationmanager.domain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SuppressWarnings("removal")
@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    @MockBean
    private UserService userService;

    @MockBean
    private ApplicationService applicationService;

    @Test
    @WithMockUser(username = "testUser")
    void shouldGetTaskById() throws Exception {
        User user = new User("testUser", "hashedPassword");
        Field filedUserId = User.class.getDeclaredField("userId");
        filedUserId.setAccessible(true);
        filedUserId.set(user, 1L);

        Enterprise enterprise = new Enterprise("Test Enterprise", user);
        Application application = new Application(user, enterprise, "notes", "app name");
        Field fieldApplicationId = Application.class.getDeclaredField("applicationId");
        fieldApplicationId.setAccessible(true);
        fieldApplicationId.set(application, 1L);

        Task task = new Task(user, application, LocalDateTime.now().plusDays(1), "Task Note");
        Field filedTaskId = Task.class.getDeclaredField("taskId");
        filedTaskId.setAccessible(true);
        filedTaskId.set(task, 1L);

        when(userService.getUserByUsername("testUser")).thenReturn(user);
        when(taskService.getTaskById(1L)).thenReturn(Optional.of(task));

        mockMvc.perform(get("/api/tasks/1"))
                .andExpect(status().isOk());
    }
}
