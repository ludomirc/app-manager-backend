package org.qbit.applicationmanager.infrastructure.http;

import org.qbit.applicationmanager.domain.model.Application;
import org.qbit.applicationmanager.domain.model.ApplicationStatus;
import org.qbit.applicationmanager.domain.model.Enterprise;
import org.qbit.applicationmanager.domain.model.User;
import org.qbit.applicationmanager.domain.service.ApplicationService;
import org.qbit.applicationmanager.domain.service.EnterpriseService;
import org.qbit.applicationmanager.domain.service.TaskService;
import org.qbit.applicationmanager.domain.service.UserService;
import org.qbit.applicationmanager.infrastructure.http.dto.ApplicationDto;
import org.qbit.applicationmanager.infrastructure.http.dto.TaskDto;
import org.qbit.applicationmanager.infrastructure.http.dto.mapper.ApplicationMapper;
import org.qbit.applicationmanager.infrastructure.http.dto.mapper.TaskMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {

    private final ApplicationService applicationService;
    private final UserService userService;
    private final ApplicationMapper applicationMapper;
    private final EnterpriseService enterpriseService;
    private final TaskService taskService;
    private final TaskMapper taskMapper;

    public ApplicationController(ApplicationService applicationService,
                                 UserService userService,
                                 ApplicationMapper applicationMapper,
                                 EnterpriseService enterpriseService,
                                 TaskService taskService,
                                 TaskMapper taskMapper) {
        this.applicationService = applicationService;
        this.userService = userService;
        this.applicationMapper = applicationMapper;
        this.enterpriseService = enterpriseService;
        this.taskService = taskService;
        this.taskMapper = taskMapper;
    }

    @PostMapping
    public ResponseEntity<ApplicationDto> createApplication(@RequestBody ApplicationDto dto, Authentication authentication) {
        Optional<User> user = userService.getUserByUserName(authentication.getName());
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Optional<Enterprise> enterpriseOp = enterpriseService.getEnterpriseById(dto.getEnterpriseId());
        if (enterpriseOp.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        Optional<User> enterpriseUser = enterpriseOp
                .map(Enterprise::getUser)
                .filter(enUser -> isTheSameUser(user.get(), enUser));

        if (enterpriseUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        Application application = applicationMapper.fromDto(dto, user.get(), enterpriseOp.get());
        application.setCurrentStatus(ApplicationStatus.DRAFT);
        Application savedApplication = applicationService.createApplication(application);
        ApplicationDto responseDto = applicationMapper.toDto(savedApplication);

        return ResponseEntity.ok(responseDto);
    }

    private boolean isTheSameUser(User userLeft, User userRight) {
        return userLeft.equals(userRight);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApplicationDto> getApplication(@PathVariable Long id, Authentication authentication) {
        Optional<User> currentUser = userService.getUserByUserName(authentication.getName());
        if (currentUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return applicationService.getApplicationById(id)
                .filter(app -> app.getUser().getUserId().equals(currentUser.get().getUserId()))
                .map(applicationMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.FORBIDDEN).build());
    }

    @GetMapping
    public ResponseEntity<List<ApplicationDto>> getApplicationsForCurrentUser(Authentication authentication) {
        Optional<User> user = userService.getUserByUserName(authentication.getName());
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        List<ApplicationDto> userApplications = applicationService.getApplicationsByUser(user.get()).stream()
                .map(applicationMapper::toDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(userApplications);
    }

    @GetMapping("/{id}/tasks")
    public ResponseEntity<List<TaskDto>> getTasksForApplication(@PathVariable Long id) {
        List<TaskDto> tasks = taskService.getTasksByApplicationId(id).stream()
                .map(taskMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(tasks);
    }
}
