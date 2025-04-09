package org.qbit.applicationmanager.domain.service;

import org.qbit.applicationmanager.domain.model.Application;
import org.qbit.applicationmanager.domain.model.ApplicationStatus;
import org.qbit.applicationmanager.domain.model.ApplicationStatusChange;
import org.qbit.applicationmanager.domain.repository.ApplicationStatusChangeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Service
public class ApplicationStatusChangeServiceImpl implements ApplicationStatusChangeService {

    private final ApplicationStatusChangeRepository repository;

    public ApplicationStatusChangeServiceImpl(ApplicationStatusChangeRepository repository) {
        this.repository = repository;
    }

    public ApplicationStatusChange logStatusChange(Application application, ApplicationStatus status) {
        ApplicationStatusChange change = new ApplicationStatusChange(application, status);
        return repository.save(change);
    }

    public List<ApplicationStatusChange> getStatusHistory(Long applicationId) {
        return repository.findByApplication_ApplicationIdOrderByChangedAtDesc(applicationId);
    }

    public List<String> getAvailableStatuses() {
        return Stream.of(ApplicationStatus.values())
                .map(Enum::name)
                .toList();
    }
}
