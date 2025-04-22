package org.qbit.applicationmanager.infrastructure.http.dto;

import java.time.LocalDateTime;

public class TaskStatusChangeDto {
    private Long id;
    private Long taskId;
    private String status;
    private LocalDateTime changedAt;
    private Long changedByUserId;

    public TaskStatusChangeDto(Long id, Long taskId, String status, LocalDateTime changedAt, Long changedByUserId) {
        this.id = id;
        this.taskId = taskId;
        this.status = status;
        this.changedAt = changedAt;
        this.changedByUserId = changedByUserId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getChangedAt() {
        return changedAt;
    }

    public void setChangedAt(LocalDateTime changedAt) {
        this.changedAt = changedAt;
    }

    public Long getChangedByUserId() {
        return changedByUserId;
    }

    public void setChangedByUserId(Long changedByUserId) {
        this.changedByUserId = changedByUserId;
    }
}
