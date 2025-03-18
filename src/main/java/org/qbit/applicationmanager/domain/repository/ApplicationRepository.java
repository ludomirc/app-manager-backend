package org.qbit.applicationmanager.domain.repository;

import org.qbit.applicationmanager.domain.model.Application;
import org.qbit.applicationmanager.domain.model.Enterprise;
import org.qbit.applicationmanager.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
    List<Application> findByUser(User user);
    List<Application> findByEnterprise(Enterprise enterprise);
}