package org.qbit.applicationmanager.domain.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "application_status_change")
public class ApplicationStatusChange {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_id", nullable = false)
    private Application application;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApplicationStatus status;

    @Column(nullable = false)
    private LocalDateTime changedAt = LocalDateTime.now();

    public ApplicationStatusChange() {}

    public ApplicationStatusChange(Application application, ApplicationStatus status) {
        this.application = application;
        this.status = status;
        this.changedAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public Application getApplication() { return application; }
    public void setApplication(Application application) { this.application = application; }
    public ApplicationStatus getStatus() { return status; }
    public void setStatus(ApplicationStatus status) { this.status = status; }
    public LocalDateTime getChangedAt() { return changedAt; }
    public void setChangedAt(LocalDateTime changedAt) { this.changedAt = changedAt; }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ApplicationStatusChange that = (ApplicationStatusChange) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ApplicationStatusChange{" +
                "id=" + id +
                ", application=" + application +
                ", status=" + status +
                ", changedAt=" + changedAt +
                '}';
    }
}
