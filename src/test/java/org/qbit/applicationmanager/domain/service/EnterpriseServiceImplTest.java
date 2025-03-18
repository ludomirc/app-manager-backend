package org.qbit.applicationmanager.domain.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.qbit.applicationmanager.domain.model.Enterprise;
import org.qbit.applicationmanager.domain.model.User;
import org.qbit.applicationmanager.domain.repository.EnterpriseRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class EnterpriseServiceImplTest {

    @Mock
    private EnterpriseRepository enterpriseRepository;

    @InjectMocks
    private EnterpriseServiceImpl enterpriseService;

    private User user;
    private Enterprise enterprise;

    @BeforeEach
    void setUp() {
        user = new User("testUser", "passwordHash");
        enterprise = new Enterprise("Test Enterprise", user);
    }

    @Test
    void shouldCreateEnterprise() {
        when(enterpriseRepository.save(ArgumentMatchers.any(Enterprise.class))).thenReturn(enterprise);

        Enterprise createdEnterprise = enterpriseService.createEnterprise("Test Enterprise", user);

        assertThat(createdEnterprise, notNullValue());
        assertThat(createdEnterprise.getName(), equalTo("Test Enterprise"));
        assertThat(createdEnterprise.getUser(), equalTo(user));
    }

    @Test
    void shouldFindEnterpriseById() {
        when(enterpriseRepository.findById(1L)).thenReturn(Optional.of(enterprise));

        Optional<Enterprise> foundEnterprise = enterpriseService.getEnterpriseById(1L);

        assertThat(foundEnterprise.isPresent(), is(true));
        assertThat(foundEnterprise.get(), is(equalTo(enterprise)));
    }

    @Test
    void shouldFindEnterprisesByUser() {
        List<Enterprise> enterprises = Arrays.asList(enterprise);
        when(enterpriseRepository.findByUser(user)).thenReturn(enterprises);

        List<Enterprise> foundEnterprises = enterpriseService.getEnterprisesByUser(user);

        assertThat(foundEnterprises, hasSize(1));
        assertThat(foundEnterprises.get(0), equalTo(enterprise));
    }
}
