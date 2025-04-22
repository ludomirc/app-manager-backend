package org.qbit.applicationmanager.domain.repository;

import org.qbit.applicationmanager.domain.model.TaskStatusChange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskStatusChangeRepository extends JpaRepository<TaskStatusChange, Long> {
    List<TaskStatusChange> findByTask_TaskIdOrderByChangedAtDesc(Long taskId);
}
