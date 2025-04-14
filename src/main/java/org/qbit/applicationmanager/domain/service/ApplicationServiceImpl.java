package org.qbit.applicationmanager.domain.service;

import org.qbit.applicationmanager.domain.model.Application;
import org.qbit.applicationmanager.domain.model.User;
import org.qbit.applicationmanager.domain.repository.ApplicationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ApplicationServiceImpl implements ApplicationService {
    private final ApplicationRepository applicationRepository;
    private final ApplicationStatusChangeService applicationStatusChangeService;

    public ApplicationServiceImpl(ApplicationRepository applicationRepository, ApplicationStatusChangeService applicationStatusChangeService) {
        this.applicationRepository = applicationRepository;
        this.applicationStatusChangeService = applicationStatusChangeService;
    }

    @Override
    public Application createApplication(Application application) {
        Application cratedApplication = applicationRepository.save(application);
        applicationStatusChangeService.logStatusChange(cratedApplication, cratedApplication.getCurrentStatus());
        return cratedApplication;
    }

    @Override
    public Optional<Application> getApplicationById(Long id) {
        return applicationRepository.findById(id);
    }

    @Override
    public List<Application> getApplicationsByUser(User user) {
        return applicationRepository.findByUser(user);
    }

    @Override
    public Application update(Application application) {
        return applicationRepository.save(application);
    }
}