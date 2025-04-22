package org.qbit.applicationmanager.domain.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.qbit.applicationmanager.domain.model.*;
import org.qbit.applicationmanager.domain.repository.TaskStatusChangeRepository;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class TaskStatusChangeServiceTest {

    @Mock
    private TaskStatusChangeRepository repository;

    @InjectMocks
    private TaskStatusChangeServiceImpl taskStatusChangeService;

    private User user;
    private Application application;
    private Task task;

    @BeforeEach
    void setUp() {
        user = new User("testUser", "passwordHash");
        ReflectionTestUtils.setField(user, "userId", 1L);

        application = new Application(user, new Enterprise("Test Enterprise", user), "Test Notes", "Test Name", ApplicationStatus.DRAFT);
        ReflectionTestUtils.setField(application, "applicationId", 1L);

        task = new Task(user, application, LocalDateTime.now().plusDays(1), "Task Note", TaskStatus.PENDING, "Test Name");
        ReflectionTestUtils.setField(task, "taskId", 1L);
    }

    @Test
    void shouldRecordStatusChange() {
        TaskStatusChange expectedChange = new TaskStatusChange(task, TaskStatus.IN_PROGRESS, user);
        ReflectionTestUtils.setField(expectedChange, "id", 100L);

        when(repository.save(ArgumentMatchers.any(TaskStatusChange.class))).thenReturn(expectedChange);

        TaskStatusChange result = taskStatusChangeService.recordStatusChange(task, TaskStatus.IN_PROGRESS, user);

        assertThat(result, notNullValue());
        assertThat(result.getStatus(), is(TaskStatus.IN_PROGRESS));
        assertThat(result.getChangedBy(), equalTo(user));
    }

    @Test
    void shouldReturnStatusHistoryForTask() {
        TaskStatusChange change1 = new TaskStatusChange(task, TaskStatus.PENDING, user);
        change1.setChangedAt(LocalDateTime.now().minusDays(1));
        ReflectionTestUtils.setField(change1, "id", 1L);

        TaskStatusChange change2 = new TaskStatusChange(task, TaskStatus.IN_PROGRESS, user);
        ReflectionTestUtils.setField(change2, "id", 2L);

        when(repository.findByTask_TaskIdOrderByChangedAtDesc(task.getTaskId()))
                .thenReturn(List.of(change2, change1));

        List<TaskStatusChange> history = taskStatusChangeService.getStatusHistoryForTask(task.getTaskId());

        assertThat(history, hasSize(2));
        assertThat(history.get(0).getStatus(), is(TaskStatus.IN_PROGRESS));
        assertThat(history.get(1).getStatus(), is(TaskStatus.PENDING));
    }
}
