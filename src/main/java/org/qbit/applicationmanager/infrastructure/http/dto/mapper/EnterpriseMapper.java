package org.qbit.applicationmanager.infrastructure.http.dto.mapper;

import org.qbit.applicationmanager.domain.model.Enterprise;
import org.qbit.applicationmanager.domain.model.User;
import org.qbit.applicationmanager.infrastructure.http.dto.EnterpriseDto;
import org.springframework.stereotype.Component;

@Component
public class EnterpriseMapper {

    public EnterpriseDto toDto(Enterprise enterprise) {
        return new EnterpriseDto(
                enterprise.getEnterpriseId(),
                enterprise.getName());
    }

    public Enterprise fromDto(EnterpriseDto dto, User user) {
        Enterprise enterprise = new Enterprise();
        enterprise.setName(dto.getName());
        enterprise.setUser(user);
        return enterprise;
    }
}
