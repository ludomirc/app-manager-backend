package org.qbit.applicationmanager.domain.service;

import org.qbit.applicationmanager.domain.model.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TaskStatusChangeService {
    TaskStatusChange recordStatusChange(Task task, TaskStatus newStatus, User changedBy);
    TaskStatusChange recordStatusChange(TaskStatusChange taskStatusChange);
    List<TaskStatusChange> getStatusHistoryForTask(Long taskId);
}
