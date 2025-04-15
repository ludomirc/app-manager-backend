package org.qbit.applicationmanager.domain.service;

import org.qbit.applicationmanager.domain.model.*;
import org.qbit.applicationmanager.domain.repository.TaskStatusChangeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskStatusChangeServiceImpl implements TaskStatusChangeService {

    private final TaskStatusChangeRepository repository;

    public TaskStatusChangeServiceImpl(TaskStatusChangeRepository repository) {
        this.repository = repository;
    }

    @Override
    public TaskStatusChange recordStatusChange(Task task, TaskStatus newStatus, User changedBy) {
        TaskStatusChange change = new TaskStatusChange(
                task,
                newStatus,
                changedBy
        );
        return repository.save(change);
    }

    @Override
    public List<TaskStatusChange> getStatusHistoryForTask(Long taskId) {
        return repository.findByTask_TaskIdOrderByChangedAtDesc(taskId);
    }

    @Override
    public TaskStatusChange recordStatusChange(TaskStatusChange taskStatusChange) {
            return repository.save(taskStatusChange);
    }
}
