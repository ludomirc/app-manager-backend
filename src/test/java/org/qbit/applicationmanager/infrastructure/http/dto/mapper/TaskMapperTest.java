package org.qbit.applicationmanager.infrastructure.http.dto.mapper;

import org.junit.jupiter.api.Test;
import org.qbit.applicationmanager.domain.model.*;
import org.qbit.applicationmanager.infrastructure.http.dto.TaskDto;

import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class TaskMapperTest {

    private final TaskMapper taskMapper = new TaskMapper();

    @Test
    void shouldMapTaskToDto() {
        // Given
        User user = new User("john", "hashed_pwd");
        Application app = new Application(user, null, "example notes", "Test Name", ApplicationStatus.DRAFT);
        Task task = new Task(user, app, LocalDateTime.now().plusDays(2), "Reminder", TaskStatus.PENDING, "Test Name");

        // When
        TaskDto dto = taskMapper.toDto(task);

        // Then
        assertThat(dto, is(notNullValue()));
        assertThat(dto.getApplicationId(), is(app.getApplicationId()));
        assertThat(dto.getNote(), is("Reminder"));
        assertThat(dto.getCreatedDate(), is(notNullValue()));
        assertThat(dto.getTaskDueDate(), is(task.getTaskDueDate()));
        assertThat(dto.getStatus(), is(task.getStatus().name()));
        assertThat(dto.getName(), is(task.getName()));
    }

    @Test
    void shouldMapDtoToTask() {
        // Given
        User user = new User("john", "hashed_pwd");
        Application app = new Application(user, null, "example notes", "Test Name", ApplicationStatus.DRAFT);
        LocalDateTime dueDate = LocalDateTime.now().plusDays(3);
        TaskDto dto = new TaskDto(null, 2L, null, dueDate, "DTO Note", TaskStatus.PENDING.name(),"DTO Name" );

        // When
        Task task = taskMapper.fromDto(dto, user, app);

        // Then
        assertThat(task, is(notNullValue()));
        assertThat(task.getUser(), is(user));
        assertThat(task.getApplication(), is(app));
        assertThat(task.getNote(), is("DTO Note"));
        assertThat(task.getTaskDueDate(), is(dueDate));
        assertThat(task.getStatus(), is(TaskStatus.PENDING));
        assertThat(task.getName(), is("DTO Name" ));
    }
}
