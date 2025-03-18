package org.qbit.applicationmanager.domain.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class ApplicationStatusChangeTest {

    @Test
    void shouldCreateStatusChangeWithCurrentTime() {
        Application application = new Application();

        ApplicationStatusChange change = new ApplicationStatusChange(application, ApplicationStatus.INTERVIEW);

        assertThat(change.getApplication()).isEqualTo(application);
        assertThat(change.getStatus()).isEqualTo(ApplicationStatus.INTERVIEW);
        assertThat(change.getChangedAt()).isNotNull();
        assertThat(change.getChangedAt()).isBeforeOrEqualTo(LocalDateTime.now());
    }

    @Test
    void shouldAllowManualSetters() {
        ApplicationStatusChange change = new ApplicationStatusChange();
        Application app = new Application();

        change.setApplication(app);
        change.setStatus(ApplicationStatus.REJECTED);
        LocalDateTime customTime = LocalDateTime.of(2025, 1, 1, 10, 0);
        change.setChangedAt(customTime);

        assertThat(change.getApplication()).isEqualTo(app);
        assertThat(change.getStatus()).isEqualTo(ApplicationStatus.REJECTED);
        assertThat(change.getChangedAt()).isEqualTo(customTime);
    }
}
