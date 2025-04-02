package org.qbit.applicationmanager.infrastructure.http.dto;

import java.time.LocalDateTime;

public class ApplicationStatusChangeDto {
    private String status;
    private LocalDateTime changedAt;

    public ApplicationStatusChangeDto(String status, LocalDateTime changedAt) {
        this.status = status;
        this.changedAt = changedAt;
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
}
