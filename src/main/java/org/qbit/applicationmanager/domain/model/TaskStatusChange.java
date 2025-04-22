package org.qbit.applicationmanager.domain.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(
        name = "task_status_change",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "UK_TASK_STATUS_TIMESTAMP",
                        columnNames = {"task_id", "status", "changed_at"}
                )
        }
)
public class TaskStatusChange {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "task_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "FK_TASK_STATUS_CHANGE_TASK")
    )
    private Task task;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private TaskStatus status;

    @Column(name = "changed_at", nullable = false)
    private LocalDateTime changedAt;

    @ManyToOne
    @JoinColumn(
            name = "changed_by_user_id",
            foreignKey = @ForeignKey(name = "FK_TASK_STATUS_CHANGE_USER")
    )
    private User changedBy;

    public TaskStatusChange() {
    }

    public TaskStatusChange(Task task, TaskStatus status, User changedBy) {
        this.task = task;
        this.status = status;
        this.changedAt = LocalDateTime.now();
        this.changedBy = changedBy;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public LocalDateTime getChangedAt() {
        return changedAt;
    }

    public void setChangedAt(LocalDateTime changedAt) {
        this.changedAt = changedAt;
    }

    public User getChangedBy() {
        return changedBy;
    }

    public void setChangedBy(User changedBy) {
        this.changedBy = changedBy;
    }

    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        TaskStatusChange that = (TaskStatusChange) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TaskStatusChange{" +
                "id=" + id +
                ", task=" + task +
                ", status=" + status +
                ", changedAt=" + changedAt +
                ", changedBy=" + changedBy +
                '}';
    }
}
