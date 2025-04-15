package org.qbit.applicationmanager.infrastructure.http.dto.mapper;

import org.junit.jupiter.api.Test;
import org.qbit.applicationmanager.domain.model.ApplicationStatus;
import org.qbit.applicationmanager.domain.model.ApplicationStatusChange;
import org.qbit.applicationmanager.infrastructure.http.dto.ApplicationStatusChangeDto;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ApplicationStatusChangeMapperTest {

    private final ApplicationStatusChangeMapper mapper = new ApplicationStatusChangeMapper();

    @Test
    void shouldMapEntityToDto() {
        ApplicationStatusChange change = new ApplicationStatusChange();
        change.setStatus(ApplicationStatus.APPLIED);
        change.setChangedAt(LocalDateTime.now());

        ApplicationStatusChangeDto dto = mapper.toDto(change);

        assertEquals("APPLIED", dto.getStatus());
        assertEquals(change.getChangedAt(), dto.getChangedAt());
    }

    @Test
    void shouldMapListOfEntitiesToDtoList() {
        ApplicationStatusChange change1 = new ApplicationStatusChange();
        change1.setStatus(ApplicationStatus.DRAFT);
        change1.setChangedAt(LocalDateTime.now());

        ApplicationStatusChange change2 = new ApplicationStatusChange();
        change2.setStatus(ApplicationStatus.INTERVIEW);
        change2.setChangedAt(LocalDateTime.now());

        List<ApplicationStatusChangeDto> result = mapper.toDtoList(List.of(change1, change2));

        assertEquals(2, result.size());
        assertEquals("DRAFT", result.get(0).getStatus());
        assertEquals("INTERVIEW", result.get(1).getStatus());
    }
}
