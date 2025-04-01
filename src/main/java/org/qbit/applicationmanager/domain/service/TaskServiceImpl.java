package org.qbit.applicationmanager.domain.service;

import org.qbit.applicationmanager.domain.model.Application;
import org.qbit.applicationmanager.domain.model.Task;
import org.qbit.applicationmanager.domain.model.User;
import org.qbit.applicationmanager.domain.repository.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    @Override
    public List<Task> getTasksByUser(User user) {
        return taskRepository.findByUser(user);
    }

    @Override
    public List<Task> getTasksByApplication(Application application) {
        return taskRepository.findByApplication(application);
    }

    @Override
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
}