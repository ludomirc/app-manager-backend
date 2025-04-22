package org.qbit.applicationmanager.domain.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class TaskEntityTest {

    @Test
    public void testTaskEntity_Creation() {
        User user = new User("testUser", "hashedPassword");
        Enterprise enterprise = new Enterprise("TestEnterprise", user);
        Application application = new Application(user, enterprise, "TestNotes","Test Name",ApplicationStatus.DRAFT);
        LocalDateTime dueDate = LocalDateTime.now().plusDays(3);

        Task task = new Task(user, application, dueDate, "Test Task",TaskStatus.PENDING, "Test Name");

        assertThat(task, is(notNullValue()));
        assertThat(task.getUser(), is(notNullValue()));
        assertThat(task.getApplication(), is(notNullValue()));
        assertThat(task.getNote(), is(equalTo("Test Task")));
        assertThat(task.getTaskDueDate(), is(notNullValue()));
        assertThat(task.getTaskDueDate(), is(greaterThan(LocalDateTime.now())));
    }
}