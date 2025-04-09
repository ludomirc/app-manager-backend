package org.qbit.applicationmanager.domain.service;

import org.qbit.applicationmanager.domain.model.Enterprise;
import org.qbit.applicationmanager.domain.model.User;
import org.qbit.applicationmanager.domain.repository.EnterpriseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EnterpriseServiceImpl implements EnterpriseService {
    private final EnterpriseRepository enterpriseRepository;

    public EnterpriseServiceImpl(EnterpriseRepository enterpriseRepository) {
        this.enterpriseRepository = enterpriseRepository;
    }

    @Override
    public Enterprise createEnterprise(String name, User user) {
        Enterprise enterprise = new Enterprise(name, user);
        return enterpriseRepository.save(enterprise);
    }

    @Override
    public Optional<Enterprise> getEnterpriseById(Long id) {
        return enterpriseRepository.findById(id);
    }

    @Override
    public List<Enterprise> getEnterprisesByUser(User user) {
        return enterpriseRepository.findByUser(user);
    }
}