package org.qbit.applicationmanager.infrastructure.http.dto.mapper;

import org.qbit.applicationmanager.domain.model.TaskStatusChange;
import org.qbit.applicationmanager.infrastructure.http.dto.TaskStatusChangeDto;
import org.springframework.stereotype.Component;

@Component
public class TaskStatusChangeMapper {
    public TaskStatusChangeDto toDto(TaskStatusChange change) {
        return new TaskStatusChangeDto(
                change.getId(),
                change.getTask().getTaskId(),
                change.getStatus().name(),
                change.getChangedAt(),
                change.getChangedBy() != null ? change.getChangedBy().getUserId() : null
        );
    }
}  