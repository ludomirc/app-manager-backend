package org.qbit.applicationmanager.domain.repository;

import org.qbit.applicationmanager.domain.model.Task;
import org.qbit.applicationmanager.domain.model.Application;
import org.qbit.applicationmanager.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query("SELECT t FROM Task t JOIN FETCH t.application WHERE t.user = :user")
    List<Task> findByUser(User user);

    @Query("SELECT t FROM Task t JOIN FETCH t.user WHERE t.application = :application")
    List<Task> findByApplication(Application application);
}