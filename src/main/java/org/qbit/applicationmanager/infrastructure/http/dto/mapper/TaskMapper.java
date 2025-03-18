package org.qbit.applicationmanager.infrastructure.http.dto.mapper;

import org.qbit.applicationmanager.domain.model.Application;
import org.qbit.applicationmanager.domain.model.Task;
import org.qbit.applicationmanager.domain.model.User;
import org.qbit.applicationmanager.infrastructure.http.dto.TaskDto;

public class TaskMapper {

    public static TaskDto toDto(Task task) {
        return new TaskDto(
            task.getTaskId(),
            task.getApplication() != null ? task.getApplication().getApplicationId() : null,
            task.getCreatedDate(),
            task.getTaskDueDate(),
            task.getNote()
        );
    }

    public static Task fromDto(TaskDto dto, User user, Application application) {
        Task task = new Task();
        task.setUser(user);
        task.setApplication(application);
        task.setTaskDueDate(dto.getTaskDueDate());
        task.setNote(dto.getNote());
        return task;
    }
}
