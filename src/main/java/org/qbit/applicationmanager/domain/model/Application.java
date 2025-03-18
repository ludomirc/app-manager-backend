package org.qbit.applicationmanager.domain.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "application")
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long applicationId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "enterprise_id", nullable = false)
    private Enterprise enterprise;

    @Column(nullable = false)
    private LocalDateTime creationDate = LocalDateTime.now();

    @Column(columnDefinition = "TEXT")
    private String notes;

    public Application() {}

    public Application(User user, Enterprise enterprise, String notes) {
        this.user = user;
        this.enterprise = enterprise;
        this.notes = notes;
    }

    public Long getApplicationId() { return applicationId; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public Enterprise getEnterprise() { return enterprise; }
    public void setEnterprise(Enterprise enterprise) { this.enterprise = enterprise; }
    public LocalDateTime getCreationDate() { return creationDate; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}