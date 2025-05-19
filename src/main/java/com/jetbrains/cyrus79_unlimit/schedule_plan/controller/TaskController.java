package com.jetbrains.cyrus79_unlimit.schedule_plan.controller;

import com.jetbrains.cyrus79_unlimit.schedule_plan.dto.response.TaskDto;
import com.jetbrains.cyrus79_unlimit.schedule_plan.security.CustomUserDetails;
import com.jetbrains.cyrus79_unlimit.schedule_plan.dto.request.CreateTaskRequest;
import com.jetbrains.cyrus79_unlimit.schedule_plan.service.TaskService;
import com.jetbrains.cyrus79_unlimit.schedule_plan.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tasks")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    // Create Task
    @PostMapping
    public TaskDto createTask(@Valid @RequestBody CreateTaskRequest createTaskRequest,
                              @AuthenticationPrincipal CustomUserDetails userDetails) {
        return taskService.createTask(userDetails.getUsername(),createTaskRequest);
    }

    // Get User's Tasks
    @GetMapping("/my-tasks")
    public Page<TaskDto> getMyTasks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) Boolean completed,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        return taskService.getUserTasks(userDetails.getUsername(),completed, PageRequest.of(page,size));
    }

    // Get Task by ID
//    @PreAuthorize("hasRole('ADMIN')")
//    @GetMapping("/{id}")
//    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
//        return taskService.getTaskById(id)
//                .map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build());
//    }
//
//    // Delete Task
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
//        taskService.deleteTask(id);
//        return ResponseEntity.noContent().build();
//    }
//
//    // Mark Task as Completed
//    @PutMapping("/{id}/complete")
//    public ResponseEntity<Task> completeTask(@PathVariable Long id) {
//        return ResponseEntity.ok(taskService.markTaskAsCompleted(id));
//    }
}
