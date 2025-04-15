package org.qbit.applicationmanager.infrastructure.http.dto.mapper;

import org.qbit.applicationmanager.domain.model.ApplicationStatusChange;
import org.qbit.applicationmanager.infrastructure.http.dto.ApplicationStatusChangeDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ApplicationStatusChangeMapper {

    public ApplicationStatusChangeDto toDto(ApplicationStatusChange change) {
        return new ApplicationStatusChangeDto(
                change.getStatus().name(),
                change.getChangedAt()
        );
    }

    public List<ApplicationStatusChangeDto> toDtoList(List<ApplicationStatusChange> changes) {
        return changes.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
