package org.qbit.applicationmanager.domain.service;

import org.qbit.applicationmanager.domain.model.Application;
import org.qbit.applicationmanager.domain.model.Enterprise;
import org.qbit.applicationmanager.domain.model.User;
import java.util.List;
import java.util.Optional;

public interface ApplicationService {
    Application createApplication(User user, Enterprise enterprise, String note, String name);
    Optional<Application> getApplicationById(Long id);
    List<Application> getApplicationsByUser(User user);
    List<Application> getApplicationsByEnterprise(Enterprise enterprise);
    void deleteApplication(Long id);
}