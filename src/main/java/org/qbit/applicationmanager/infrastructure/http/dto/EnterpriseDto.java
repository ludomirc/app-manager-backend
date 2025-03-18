package org.qbit.applicationmanager.infrastructure.http.dto;

public class EnterpriseDto {
    private Long enterpriseId;
    private String name;

    public EnterpriseDto() {}

    public EnterpriseDto(Long enterpriseId, String name) {
        this.enterpriseId = enterpriseId;
        this.name = name;
    }

    public Long getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(Long enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
