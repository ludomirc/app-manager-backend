package org.qbit.applicationmanager.domain.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long taskId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
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
}