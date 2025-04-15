package org.qbit.applicationmanager.infrastructure.http.dto.mapper;

import org.junit.jupiter.api.Test;
import org.qbit.applicationmanager.domain.model.*;
import org.qbit.applicationmanager.infrastructure.http.dto.TaskStatusChangeDto;

import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class TaskStatusChangeMapperTest {

    private final TaskStatusChangeMapper mapper = new TaskStatusChangeMapper();

    @Test
    void shouldMapTaskStatusChangeToDtoCorrectly() {
        // Given
        User user = new User("john", "hashed_pwd");
        Enterprise enterprise = new Enterprise("Test Enterprise", user);
        Application application = new Application(user, enterprise, "notes", "app", ApplicationStatus.DRAFT);
        Task task = new Task(user, application, LocalDateTime.now().plusDays(2), "Reminder", TaskStatus.IN_PROGRESS, "Test Name");

        TaskStatusChange change = new TaskStatusChange(task, TaskStatus.COMPLETED, user);
        change.setChangedBy(user);

        // When
        TaskStatusChangeDto dto = mapper.toDto(change);

        // Then
        assertThat(dto, notNullValue());
        assertThat(dto.getTaskId(), is(task.getTaskId()));
        assertThat(dto.getStatus(), is(TaskStatus.COMPLETED.name()));
        assertThat(dto.getChangedAt(), is(change.getChangedAt()));
        assertThat(dto.getChangedByUserId(), is(user.getUserId()));
    }
}
