package org.qbit.applicationmanager.infrastructure.http;

import org.qbit.applicationmanager.domain.model.Application;
import org.qbit.applicationmanager.domain.model.ApplicationStatus;
import org.qbit.applicationmanager.domain.model.ApplicationStatusChange;
import org.qbit.applicationmanager.domain.service.ApplicationService;
import org.qbit.applicationmanager.domain.service.ApplicationStatusChangeService;
import org.qbit.applicationmanager.infrastructure.http.dto.ApplicationStatusChangeDto;
import org.qbit.applicationmanager.infrastructure.http.dto.mapper.ApplicationStatusChangeMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/statuses")
public class ApplicationStatusChangeController {

    private final ApplicationStatusChangeService statusChangeService;
    private final ApplicationService applicationService;
    private final ApplicationStatusChangeMapper statusChangeMapper;

    public ApplicationStatusChangeController(ApplicationStatusChangeService statusChangeService,
                                             ApplicationService applicationService,
                                             ApplicationStatusChangeMapper statusChangeMapper) {
        this.statusChangeService = statusChangeService;
        this.applicationService = applicationService;
        this.statusChangeMapper = statusChangeMapper;
    }

    @GetMapping("/{applicationId}/history")
    public List<ApplicationStatusChangeDto> getHistory(@PathVariable Long applicationId, Authentication authentication) {
        var changes = statusChangeService.getStatusHistory(applicationId);
        return statusChangeMapper.toDtoList(changes);
    }

    @PostMapping("/{applicationId}")
    public ResponseEntity<ApplicationStatusChangeDto> logStatusChange(
            @PathVariable Long applicationId,
            @RequestBody ApplicationStatusChangeDto statusDto, Authentication authentication) {

        Application application = applicationService.getApplicationById(applicationId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Application not found"));

        ApplicationStatus status;
        try {
            status = ApplicationStatus.valueOf(statusDto.getStatus());
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid application status: " + statusDto.getStatus());
        }
        ApplicationStatusChange change = statusChangeService.logStatusChange(application, status);
        application.setCurrentStatus(status);
        applicationService.update(application);
        return ResponseEntity.ok(statusChangeMapper.toDto(change));
    }

    @GetMapping
    public List<String> getAvailableStatuses() {
        return statusChangeService.getAvailableStatuses();
    }

}
