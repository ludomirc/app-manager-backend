package org.qbit.applicationmanager.domain.repository;

import org.qbit.applicationmanager.domain.model.Application;
import org.qbit.applicationmanager.domain.model.Enterprise;
import org.qbit.applicationmanager.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {

    @Query("SELECT a FROM Application a JOIN FETCH a.enterprise WHERE a.user = :user")
    List<Application> findByUser(User user);

    @Query("SELECT t FROM Task t JOIN FETCH t.user WHERE t.application = :application")
    List<Application> findByEnterprise(Enterprise enterprise);
}