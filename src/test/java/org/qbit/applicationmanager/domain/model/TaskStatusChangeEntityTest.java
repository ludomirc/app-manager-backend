package org.qbit.applicationmanager.domain.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class TaskStatusChangeEntityTest {

    @Test
    public void testTaskStatusChange_Creation() {
        User user = new User("testUser", "hashedPassword");
        Enterprise enterprise = new Enterprise("Test Enterprise", user);
        Application application = new Application(user, enterprise, "Test Notes", "Test App", ApplicationStatus.DRAFT);
        Task task = new Task(user, application, LocalDateTime.now().plusDays(1), "Initial task", TaskStatus.PENDING, "Test Name");

        LocalDateTime changeTime = LocalDateTime.now().minusNanos(1);
        TaskStatusChange statusChange = new TaskStatusChange(task, TaskStatus.IN_PROGRESS, user);

        assertThat(statusChange, is(notNullValue()));
        assertThat(statusChange.getTask(), is(task));
        assertThat(statusChange.getStatus(), is(TaskStatus.IN_PROGRESS));
        assertThat(statusChange.getChangedAt(), is(greaterThan(changeTime)));
        assertThat(statusChange.getChangedBy(), is(user));
    }
}
