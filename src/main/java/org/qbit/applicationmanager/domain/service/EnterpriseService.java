package org.qbit.applicationmanager.domain.service;

import org.qbit.applicationmanager.domain.model.Enterprise;
import org.qbit.applicationmanager.domain.model.User;
import java.util.List;
import java.util.Optional;

public interface EnterpriseService {
    Enterprise createEnterprise(String name, User user);
    Optional<Enterprise> getEnterpriseById(Long id);
    List<Enterprise> getEnterprisesByUser(User user);
    void deleteEnterprise(Long id);
}