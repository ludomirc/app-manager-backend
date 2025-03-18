package org.qbit.applicationmanager.infrastructure.http.dto;

import java.time.LocalDateTime;

public class TaskDto {
    private Long taskId;
    private Long applicationId;
    private LocalDateTime createdDate;
    private LocalDateTime taskDueDate;
    private String note;

    public TaskDto() {}

    public TaskDto(Long taskId,Long applicationId, LocalDateTime createdDate, LocalDateTime taskDueDate, String note) {
        this.taskId = taskId;
        this.applicationId = applicationId;
        this.createdDate = createdDate;
        this.taskDueDate = taskDueDate;
        this.note = note;
    }

    public Long getTaskId() { return taskId; }
    public void setTaskId(Long taskId) { this.taskId = taskId; }

    public Long getApplicationId() { return applicationId; }
    public void setApplicationId(Long applicationId) { this.applicationId = applicationId; }

    public LocalDateTime getCreatedDate() { return createdDate; }
    public void setCreatedDate(LocalDateTime createdDate) { this.createdDate = createdDate; }

    public LocalDateTime getTaskDueDate() { return taskDueDate; }
    public void setTaskDueDate(LocalDateTime taskDueDate) { this.taskDueDate = taskDueDate; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }
}