package org.qbit.applicationmanager.domain.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.qbit.applicationmanager.domain.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class EntityTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EnterpriseRepository enterpriseRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Test
    public void testUserEntity() {
        User user = new User("test_user", "secure_password");
        User savedUser = userRepository.save(user);

        User foundUser = userRepository.findByUserName("test_user");

        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getUserId()).isEqualTo(savedUser.getUserId());
        assertThat(foundUser.getUserName()).isEqualTo("test_user");
    }

    @Test
    public void testEnterpriseEntity() {
        User user = userRepository.save(new User("test_user2", "secure_password"));
        Enterprise enterprise = new Enterprise("Google", user);
        Enterprise savedEnterprise = enterpriseRepository.save(enterprise);

        List<Enterprise> enterprises = enterpriseRepository.findByUser(user);

        assertThat(enterprises).isNotEmpty();
        assertThat(enterprises.get(0).getName()).isEqualTo("Google");
    }

    @Test
    public void testApplicationEntity() {
        User user = userRepository.save(new User("applicant", "secure_password"));
        Enterprise enterprise = enterpriseRepository.save(new Enterprise("Facebook", user));
        Application application = new Application(user, enterprise, "Applied for developer role");
        Application savedApplication = applicationRepository.save(application);

        List<Application> applications = applicationRepository.findByUser(user);

        assertThat(applications).isNotEmpty();
        assertThat(applications.get(0).getNotes()).isEqualTo("Applied for developer role");
    }

    @Test
    public void testTaskEntity() {
        User user = userRepository.save(new User("task_user", "secure_password"));
        Enterprise enterprise = enterpriseRepository.save(new Enterprise("Amazon", user));
        Application application = applicationRepository.save(new Application(user, enterprise, "Interview scheduled"));
        Task task = new Task(user, application, LocalDateTime.now().plusDays(3), "Prepare for interview");
        Task savedTask = taskRepository.save(task);

        List<Task> tasks = taskRepository.findByUser(user);

        assertThat(tasks).isNotEmpty();
        assertThat(tasks.get(0).getNote()).isEqualTo("Prepare for interview");
    }
}