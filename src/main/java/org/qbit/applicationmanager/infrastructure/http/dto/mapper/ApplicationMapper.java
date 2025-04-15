package org.qbit.applicationmanager.infrastructure.http.dto.mapper;

import org.qbit.applicationmanager.domain.model.Application;
import org.qbit.applicationmanager.domain.model.ApplicationStatus;
import org.qbit.applicationmanager.domain.model.Enterprise;
import org.qbit.applicationmanager.domain.model.User;
import org.qbit.applicationmanager.infrastructure.http.dto.ApplicationDto;
import org.springframework.stereotype.Component;

@Component
public class ApplicationMapper {

    public ApplicationDto toDto(Application application) {
        return new ApplicationDto(
                application.getApplicationId(),
                application.getEnterprise() != null ? application.getEnterprise().getEnterpriseId() : null,
                application.getEnterprise() != null ? application.getEnterprise().getName() : null,
                application.getCreationDate(),
                application.getNotes(),
                application.getName(),
                application.getCurrentStatus() != null ? application.getCurrentStatus().name() : null
        );
    }

    public Application fromDto(ApplicationDto dto, User user, Enterprise enterprise) {
        Application application = new Application();
        application.setUser(user);
        application.setEnterprise(enterprise);
        application.setNotes(dto.getNotes());
        application.setName(dto.getName());

        if (dto.getCurrentStatus() != null) {
            application.setCurrentStatus(ApplicationStatus.valueOf(dto.getCurrentStatus()));
        }

        return application;
    }
}
