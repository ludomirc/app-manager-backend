package org.qbit.applicationmanager.infrastructure.http.dto.mapper;

import org.junit.jupiter.api.Test;
import org.qbit.applicationmanager.domain.model.Application;
import org.qbit.applicationmanager.domain.model.ApplicationStatus;
import org.qbit.applicationmanager.domain.model.Task;
import org.qbit.applicationmanager.domain.model.User;
import org.qbit.applicationmanager.infrastructure.http.dto.TaskDto;

import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class TaskMapperTest {

    @Test
    void shouldMapTaskToDto() {
        // Given
        User user = new User("john", "hashed_pwd");
        Application app = new Application(user, null, "example notes","Test Name", ApplicationStatus.DRAFT);
        Task task = new Task(user, app, LocalDateTime.now().plusDays(2), "Reminder");

        // When
        TaskDto dto = TaskMapper.toDto(task);

        // Then
        assertThat(dto, is(notNullValue()));
        assertThat(dto.getApplicationId(), is(app.getApplicationId()));
        assertThat(dto.getNote(), is("Reminder"));
        assertThat(dto.getCreatedDate(), is(notNullValue()));
        assertThat(dto.getTaskDueDate(), is(task.getTaskDueDate()));
        assertThat(dto.getTaskDueDate(), is(task.getTaskDueDate()));
    }

    @Test
    void shouldMapDtoToTask() {
        // Given
        User user = new User("john", "hashed_pwd");
        Application app = new Application(user, null, "example notes","Test Name",ApplicationStatus.DRAFT);
        LocalDateTime dueDate = LocalDateTime.now().plusDays(3);
        TaskDto dto = new TaskDto(null, 2L, null, dueDate, "DTO Note");

        // When
        Task task = TaskMapper.fromDto(dto, user, app);

        // Then
        assertThat(task, is(notNullValue()));
        assertThat(task.getUser(), is(user));
        assertThat(task.getApplication(), is(app));
        assertThat(task.getNote(), is("DTO Note"));
        assertThat(task.getTaskDueDate(), is(dueDate));
    }
}
