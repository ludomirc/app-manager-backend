package org.qbit.applicationmanager.infrastructure.http;

import org.qbit.applicationmanager.domain.model.Application;
import org.qbit.applicationmanager.domain.model.ApplicationStatus;
import org.qbit.applicationmanager.domain.model.Enterprise;
import org.qbit.applicationmanager.domain.model.User;
import org.qbit.applicationmanager.domain.service.ApplicationService;
import org.qbit.applicationmanager.domain.service.EnterpriseService;
import org.qbit.applicationmanager.domain.service.UserService;
import org.qbit.applicationmanager.infrastructure.http.dto.ApplicationDto;
import org.qbit.applicationmanager.infrastructure.http.dto.mapper.ApplicationMapper;
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

    public ApplicationController(ApplicationService applicationService,
                                 UserService userService,
                                 ApplicationMapper applicationMapper,
                                 EnterpriseService enterpriseService) {
        this.applicationService = applicationService;
        this.userService = userService;
        this.applicationMapper = applicationMapper;
        this.enterpriseService = enterpriseService;
    }

    @PostMapping
    public ResponseEntity<ApplicationDto> createApplication(@RequestBody ApplicationDto dto, Authentication authentication) {
        User user = userService.getUserByUsername(authentication.getName());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Optional<Enterprise> enterpriseOp = enterpriseService.getEnterpriseById(dto.getEnterpriseId());
        if (enterpriseOp.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        Enterprise enterprise =  enterpriseOp.get();

        if (enterprise.getUser().getUserName().equals(user.getUserName())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        Application application = applicationMapper.fromDto(dto, user, enterprise);
        application.setCurrentStatus(ApplicationStatus.DRAFT);
        Application savedApplication = applicationService.createApplication(application);
        ApplicationDto responseDto = applicationMapper.toDto(savedApplication);

        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApplicationDto> getApplication(@PathVariable Long id, Authentication authentication) {
        User currentUser = userService.getUserByUsername(authentication.getName());
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return applicationService.getApplicationById(id)
                .filter(app -> app.getUser().getUserId().equals(currentUser.getUserId()))
                .map(applicationMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.FORBIDDEN).build());
    }

    @GetMapping
    public ResponseEntity<List<ApplicationDto>> getApplicationsForCurrentUser(Authentication authentication) {
        User user = userService.getUserByUsername(authentication.getName());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        List<ApplicationDto> userApplications = applicationService.getApplicationsByUser(user).stream()
                .map(applicationMapper::toDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(userApplications);
    }
}
