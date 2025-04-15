package org.qbit.applicationmanager.infrastructure.http;

import org.qbit.applicationmanager.domain.model.*;
import org.qbit.applicationmanager.domain.service.ApplicationService;
import org.qbit.applicationmanager.domain.service.TaskService;
import org.qbit.applicationmanager.domain.service.TaskStatusChangeService;
import org.qbit.applicationmanager.domain.service.UserService;
import org.qbit.applicationmanager.infrastructure.http.dto.TaskDto;
import org.qbit.applicationmanager.infrastructure.http.dto.mapper.TaskMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import java.util.Optional;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;
    private final UserService userService;
    private final ApplicationService applicationService;
    private final TaskMapper taskMapper;
    private final TaskStatusChangeService taskStatusChangeService;

    public TaskController(TaskService taskService, UserService userService, ApplicationService applicationService, TaskMapper taskMapper, TaskStatusChangeService taskStatusChangeService) {
        this.taskService = taskService;
        this.userService = userService;
        this.applicationService = applicationService;
        this.taskMapper = taskMapper;
        this.taskStatusChangeService = taskStatusChangeService;
    }

    @PostMapping
    public ResponseEntity<TaskDto> createTask(@RequestBody TaskDto dto, Authentication authentication) {
        User user = userService.getUserByUsername(authentication.getName());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Optional<Application> applicationOpt = applicationService.getApplicationById(dto.getApplicationId());
        if (applicationOpt.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        Application application = applicationOpt.get();

        if (!application.getUser().getUserId().equals(user.getUserId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Task task = taskMapper.fromDto(dto, user, application);
        Task savedTask = taskService.createTask(task);

        TaskStatusChange taskStatusChange = new TaskStatusChange(task, task.getStatus(),user);
        taskStatusChangeService.recordStatusChange(taskStatusChange);

        return ResponseEntity.ok(taskMapper.toDto(savedTask));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDto> getTask(@PathVariable Long id, Authentication authentication) {
        User user = userService.getUserByUsername(authentication.getName());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return taskService.getTaskById(id)
                .filter(task -> task.getUser().getUserId().equals(user.getUserId()))
                .map(taskMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.FORBIDDEN).build());
    }
}
