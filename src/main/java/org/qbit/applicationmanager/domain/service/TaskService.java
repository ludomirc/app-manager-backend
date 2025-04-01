package org.qbit.applicationmanager.domain.service;

import org.qbit.applicationmanager.domain.model.Application;
import org.qbit.applicationmanager.domain.model.Task;
import org.qbit.applicationmanager.domain.model.User;
import java.util.List;
import java.util.Optional;

public interface TaskService {
    Task createTask(Task task);
    Optional<Task> getTaskById(Long id);
    List<Task> getTasksByUser(User user);
    List<Task> getTasksByApplication(Application application);
    void deleteTask(Long id);
}