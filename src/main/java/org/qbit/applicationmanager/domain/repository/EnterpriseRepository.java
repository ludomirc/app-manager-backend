package org.qbit.applicationmanager.domain.repository;

import org.qbit.applicationmanager.domain.model.Enterprise;
import org.qbit.applicationmanager.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EnterpriseRepository extends JpaRepository<Enterprise, Long> {

    @Query("SELECT e FROM Enterprise e JOIN FETCH e.user WHERE e.user = :user")
    List<Enterprise> findByUser(User user);
}