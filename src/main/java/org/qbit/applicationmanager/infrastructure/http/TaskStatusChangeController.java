package org.qbit.applicationmanager.infrastructure.http;

import org.qbit.applicationmanager.domain.model.Task;
import org.qbit.applicationmanager.domain.model.TaskStatus;
import org.qbit.applicationmanager.domain.model.TaskStatusChange;
import org.qbit.applicationmanager.domain.model.User;
import org.qbit.applicationmanager.domain.service.TaskService;
import org.qbit.applicationmanager.domain.service.TaskStatusChangeService;
import org.qbit.applicationmanager.domain.service.UserService;
import org.qbit.applicationmanager.infrastructure.http.dto.TaskStatusChangeDto;
import org.qbit.applicationmanager.infrastructure.http.dto.mapper.TaskStatusChangeMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/task-status-changes")
public class TaskStatusChangeController {

    private final TaskStatusChangeService taskStatusChangeService;
    private final TaskService taskService;
    private final UserService userService;
    private final TaskStatusChangeMapper taskStatusChangeMapper;

    public TaskStatusChangeController(TaskStatusChangeService taskStatusChangeService, TaskService taskService, UserService userService, TaskStatusChangeMapper taskStatusChangeMapper) {
        this.taskStatusChangeService = taskStatusChangeService;
        this.taskService = taskService;
        this.userService = userService;
        this.taskStatusChangeMapper = taskStatusChangeMapper;
    }

    @GetMapping("/task/{taskId}")
    public ResponseEntity<List<TaskStatusChangeDto>> getStatusChangesByTaskId(@PathVariable Long taskId, Authentication authentication) {
        User user = userService.getUserByUsername(authentication.getName());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Task task = taskService.getTaskById(taskId).orElse(null);
        if (task == null || !task.getUser().getUserId().equals(user.getUserId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        List<TaskStatusChange> changes = taskStatusChangeService.getStatusHistoryForTask(taskId);
        List<TaskStatusChangeDto> dtoList = changes.stream().map(taskStatusChangeMapper::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(dtoList);
    }

    @PostMapping("/task/{taskId}/status")
    public ResponseEntity<TaskStatusChangeDto> recordStatusChange(
            @PathVariable Long taskId,
            @RequestBody TaskStatusChangeDto request,
            Authentication authentication) {

        User user = userService.getUserByUsername(authentication.getName());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Task task = taskService.getTaskById(taskId).orElse(null);
        if (task == null || !task.getUser().getUserId().equals(user.getUserId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        TaskStatus status = TaskStatus.valueOf(request.getStatus());
        task.setStatus(status);
        taskService.updateTask(task);
        TaskStatusChange change = taskStatusChangeService.recordStatusChange(task, status, user);
        return ResponseEntity.ok(taskStatusChangeMapper.toDto(change));
    }
}
