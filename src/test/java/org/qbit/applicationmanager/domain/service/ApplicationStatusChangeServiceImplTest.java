package org.qbit.applicationmanager.domain.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.qbit.applicationmanager.domain.model.*;
import org.qbit.applicationmanager.domain.repository.ApplicationStatusChangeRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class ApplicationStatusChangeServiceImplTest {

    private ApplicationStatusChangeRepository repository;
    private ApplicationStatusChangeService service;

    @BeforeEach
    void setup() {
        repository = mock(ApplicationStatusChangeRepository.class);
        service = new ApplicationStatusChangeServiceImpl(repository);
    }

    @Test
    void shouldLogStatusChange() {
        Application app = mock(Application.class);
        ApplicationStatus status = ApplicationStatus.OFFER;

        service.logStatusChange(app, status);

        ArgumentCaptor<ApplicationStatusChange> captor = ArgumentCaptor.forClass(ApplicationStatusChange.class);
        verify(repository).save(captor.capture());

        ApplicationStatusChange saved = captor.getValue();
        assertThat(saved.getApplication()).isEqualTo(app);
        assertThat(saved.getStatus()).isEqualTo(status);
        assertThat(saved.getChangedAt()).isNotNull();
    }

    @Test
    void shouldReturnStatusHistory() {
        when(repository.findByApplication_ApplicationIdOrderByChangedAtDesc(1L))
                .thenReturn(List.of(new ApplicationStatusChange(), new ApplicationStatusChange()));

        List<ApplicationStatusChange> history = service.getStatusHistory(1L);

        assertThat(history).hasSize(2);
        verify(repository).findByApplication_ApplicationIdOrderByChangedAtDesc(1L);
    }
}
