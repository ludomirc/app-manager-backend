package org.qbit.applicationmanager.infrastructure.http.dto.mapper;

import org.qbit.applicationmanager.domain.model.Application;
import org.qbit.applicationmanager.domain.model.Task;
import org.qbit.applicationmanager.domain.model.TaskStatus;
import org.qbit.applicationmanager.domain.model.User;
import org.qbit.applicationmanager.infrastructure.http.dto.TaskDto;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class TaskMapper {

    public TaskDto toDto(Task task) {
        return new TaskDto(
                task.getTaskId(),
                task.getApplication() != null ? task.getApplication().getApplicationId() : null,
                task.getCreatedDate(),
                task.getTaskDueDate(),
                task.getNote(),
                task.getStatus() != null ? task.getStatus().name() : null,
                task.getName()
        );
    }

    public Task fromDto(TaskDto dto, User user, Application application) {
        Task task = new Task();
        task.setUser(user);
        task.setApplication(application);
        task.setTaskDueDate(dto.getTaskDueDate());
        task.setNote(dto.getNote());
        task.setStatus(Optional.ofNullable(dto.getStatus())
                .map(TaskStatus::valueOf)
                .orElse(null));
        task.setName(dto.getName());

        return task;
    }

}
