package org.qbit.applicationmanager.domain.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.qbit.applicationmanager.domain.model.*;
import org.qbit.applicationmanager.domain.repository.ApplicationRepository;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class ApplicationServiceImplTest {

    @Mock
    private ApplicationRepository applicationRepository;

    @Mock
    private ApplicationStatusChangeService applicationStatusChangeService;

    @InjectMocks
    private ApplicationServiceImpl applicationService;

    private User user;
    private Enterprise enterprise;
    private Application application;

    @BeforeEach
    void setUp() {
        user = new User("testUser", "passwordHash");
        enterprise = new Enterprise("Test Enterprise", user);
        application = new Application(user, enterprise, "Test Notes","Test Name", ApplicationStatus.DRAFT);
    }

    @Test
    void shouldCreateApplication() {
        when(applicationRepository.save(ArgumentMatchers.any(Application.class))).thenReturn(application);

        Application expectedApplication = new Application(user, enterprise,  "Test Notes","Test Name",ApplicationStatus.DRAFT);
        Application createdApp = applicationService.createApplication(expectedApplication);

        assertThat(createdApp, is(notNullValue()));
        assertThat(createdApp.getUser(), is(equalTo(user)));
        assertThat(createdApp.getEnterprise(), is(equalTo(enterprise)));
        assertThat(createdApp.getNotes(), is(equalTo("Test Notes")));
        assertThat(createdApp.getCurrentStatus(), is(equalTo(ApplicationStatus.DRAFT)));
    }

    @Test
    void shouldFindApplicationById() {
        when(applicationRepository.findById(1L)).thenReturn(Optional.of(application));

        Optional<Application> foundApp = applicationService.getApplicationById(1L);

        assertThat(foundApp.isPresent(), is(true));
        assertThat(foundApp.get(), is(equalTo(application)));
    }
}