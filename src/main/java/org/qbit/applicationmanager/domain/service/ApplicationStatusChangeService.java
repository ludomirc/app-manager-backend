package org.qbit.applicationmanager.domain.service;

import org.qbit.applicationmanager.domain.model.Application;
import org.qbit.applicationmanager.domain.model.ApplicationStatus;
import org.qbit.applicationmanager.domain.model.ApplicationStatusChange;

import java.util.List;

public interface ApplicationStatusChangeService {
    ApplicationStatusChange logStatusChange(Application application, ApplicationStatus status);
    List<ApplicationStatusChange> getStatusHistory(Long applicationId);
}
