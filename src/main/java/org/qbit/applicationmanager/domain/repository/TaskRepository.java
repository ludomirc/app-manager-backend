package org.qbit.applicationmanager.domain.repository;

import org.qbit.applicationmanager.domain.model.Task;
import org.qbit.applicationmanager.domain.model.Application;
import org.qbit.applicationmanager.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUser(User user);
    List<Task> findByApplication(Application application);
}