package org.qbit.applicationmanager.infrastructure.http.dto;

import java.time.LocalDateTime;
import java.util.Objects;

public class ApplicationDto {
    private String name;
    private Long applicationId;
    private Long enterpriseId;
    private String enterpriseName;
    private LocalDateTime creationDate;
    private String notes;
    private String currentStatus; // added

    public ApplicationDto() {
    }

    public ApplicationDto(Long applicationId, Long enterpriseId, String enterpriseName,
                          LocalDateTime creationDate, String notes, String name, String currentStatus) {
        this.applicationId = applicationId;
        this.enterpriseId = enterpriseId;
        this.enterpriseName = enterpriseName;
        this.creationDate = creationDate;
        this.notes = notes;
        this.name = name;
        this.currentStatus = currentStatus;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public Long getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(Long enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ApplicationDto that = (ApplicationDto) o;
        return Objects.equals(applicationId, that.applicationId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(applicationId);
    }
}
