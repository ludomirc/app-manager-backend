package org.qbit.applicationmanager.domain.repository;

import org.qbit.applicationmanager.domain.model.ApplicationStatusChange;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationStatusChangeRepository extends JpaRepository<ApplicationStatusChange, Long> {
    List<ApplicationStatusChange> findByApplication_ApplicationIdOrderByChangedAtDesc(Long applicationId);
}
