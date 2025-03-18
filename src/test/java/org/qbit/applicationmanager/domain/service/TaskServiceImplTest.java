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
import org.qbit.applicationmanager.domain.model.Application;
import org.qbit.applicationmanager.domain.model.Enterprise;
import org.qbit.applicationmanager.domain.model.Task;
import org.qbit.applicationmanager.domain.model.User;
import org.qbit.applicationmanager.domain.repository.TaskRepository;
import java.util.Optional;
import java.util.List;
import java.util.Arrays;
import java.time.LocalDateTime;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    private User user;
    private Application application;
    private Task task;

    @BeforeEach
    void setUp() {
        user = new User("testUser", "passwordHash");
        application = new Application(user, new Enterprise("Test Enterprise", user), "Test Notes","Test Name");
        task = new Task(user, application, LocalDateTime.now().plusDays(1), "Task Note");
    }

    @Test
    void shouldCreateTask() {
        when(taskRepository.save(ArgumentMatchers.any(Task.class))).thenReturn(task);

        Task createdTask = taskService.createTask(user, application, "Task Note");

        assertThat(createdTask, notNullValue());
        assertThat(createdTask.getUser(), equalTo(user));
        assertThat(createdTask.getApplication(), equalTo(application));
        assertThat(createdTask.getNote(), equalTo("Task Note"));
    }

    @Test
    void shouldFindTaskById() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        Optional<Task> foundTask = taskService.getTaskById(1L);

        assertThat(foundTask.isPresent(), is(true));
        assertThat(foundTask.get(), is(equalTo(task)));
    }

    @Test
    void shouldFindTasksByUser() {
        List<Task> tasks = Arrays.asList(task);
        when(taskRepository.findByUser(user)).thenReturn(tasks);

        List<Task> foundTasks = taskService.getTasksByUser(user);

        assertThat(foundTasks, hasSize(1));
        assertThat(foundTasks.get(0), equalTo(task));
    }

    @Test
    void shouldFindTasksByApplication() {
        List<Task> tasks = Arrays.asList(task);
        when(taskRepository.findByApplication(application)).thenReturn(tasks);

        List<Task> foundTasks = taskService.getTasksByApplication(application);

        assertThat(foundTasks, hasSize(1));
        assertThat(foundTasks.get(0), equalTo(task));
    }
}
