package org.qbit.applicationmanager.infrastructure.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.qbit.applicationmanager.domain.model.*;
import org.qbit.applicationmanager.domain.service.TaskService;
import org.qbit.applicationmanager.domain.service.TaskStatusChangeService;
import org.qbit.applicationmanager.domain.service.UserService;
import org.qbit.applicationmanager.infrastructure.http.dto.TaskStatusChangeDto;
import org.qbit.applicationmanager.infrastructure.http.dto.mapper.TaskStatusChangeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SuppressWarnings("removal")
@WebMvcTest(TaskStatusChangeController.class)
class TaskStatusChangeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TaskService taskService;

    @MockBean
    private UserService userService;

    @MockBean
    private TaskStatusChangeService statusChangeService;

    @MockBean
    private TaskStatusChangeMapper mapper;

    @Test
    @WithMockUser(username = "testUser")
    void shouldRecordStatusChange() throws Exception {
        // Arrange
        User user = new User("testUser", "hashedPwd");
        ReflectionTestUtils.setField(user, "userId", 1L);

        Task task = new Task();
        task.setUser(user);

        TaskStatusChange change = new TaskStatusChange();
        change.setChangedBy(user);
        change.setChangedAt(LocalDateTime.now());
        change.setStatus(TaskStatus.IN_PROGRESS);
        change.setTask(task);

        TaskStatusChangeDto requestDto = new TaskStatusChangeDto(null, 1L, "IN_PROGRESS", LocalDateTime.now(), null);
        TaskStatusChangeDto responseDto = new TaskStatusChangeDto(1L, 1L, "IN_PROGRESS", requestDto.getChangedAt(), 1L);

        when(userService.getUserByUserName("testUser")).thenReturn(Optional.of(user));
        when(taskService.getTaskById(1L)).thenReturn(Optional.of(task));
        when(statusChangeService.recordStatusChange(task, TaskStatus.IN_PROGRESS, user)).thenReturn(change);
        when(mapper.toDto(change)).thenReturn(responseDto);

        // Act + Assert
        mockMvc.perform(post("/api/task-status-changes/task/1/status")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is("IN_PROGRESS")))
                .andExpect(jsonPath("$.taskId", is(1)));
    }

}
