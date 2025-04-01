package org.qbit.applicationmanager.domain.service;

import org.qbit.applicationmanager.domain.model.Application;
import org.qbit.applicationmanager.domain.model.Enterprise;
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

    public ApplicationServiceImpl(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }

    @Override
    public Application createApplication(Application application) {
        return applicationRepository.save(application);
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
    public List<Application> getApplicationsByEnterprise(Enterprise enterprise) {
        return applicationRepository.findByEnterprise(enterprise);
    }

    @Override
    public void deleteApplication(Long id) {
        applicationRepository.deleteById(id);
    }
}