package org.qbit.applicationmanager.domain.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long taskId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_id", nullable = false)
    private Application application;

    @Column(nullable = false)
    private LocalDateTime createdDate = LocalDateTime.now();

    @Column(nullable = true)
    private LocalDateTime taskDueDate;

    @Column(columnDefinition = "TEXT")
    private String note;

    public Task() {}

    public Task(User user, Application application, LocalDateTime taskDueDate, String note) {
        this.user = user;
        this.application = application;
        this.taskDueDate = taskDueDate;
        this.note = note;
    }

    public Long getTaskId() { return taskId; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public Application getApplication() { return application; }
    public void setApplication(Application application) { this.application = application; }
    public LocalDateTime getCreatedDate() { return createdDate; }
    public LocalDateTime getTaskDueDate() { return taskDueDate; }
    public void setTaskDueDate(LocalDateTime taskDueDate) { this.taskDueDate = taskDueDate; }
    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(taskId, task.taskId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(taskId);
    }

    @Override
    public String toString() {
        return "Task{" +
                "taskId=" + taskId +
                ", user=" + user +
                ", application=" + application +
                ", createdDate=" + createdDate +
                ", taskDueDate=" + taskDueDate +
                ", note='" + note + '\'' +
                '}';
    }
}