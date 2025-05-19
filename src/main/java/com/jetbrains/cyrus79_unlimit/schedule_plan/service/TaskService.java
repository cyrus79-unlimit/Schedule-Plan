package com.jetbrains.cyrus79_unlimit.schedule_plan.service;

import com.jetbrains.cyrus79_unlimit.schedule_plan.dto.request.CreateTaskRequest;
import com.jetbrains.cyrus79_unlimit.schedule_plan.entity.Task;
import com.jetbrains.cyrus79_unlimit.schedule_plan.entity.User;
import com.jetbrains.cyrus79_unlimit.schedule_plan.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;

    @Autowired
    private UserService userService;

    public Task createTask(CreateTaskRequest createTaskRequest, String username)
    {
        User user = userService.findByUsername(username);

        Task newTask = new Task();
        newTask.setTitle(createTaskRequest.getTitle());
        newTask.setDescription(createTaskRequest.getDescription());
        newTask.setDueDate(createTaskRequest.getDueDate());
        newTask.setCompleted(false);
        newTask.setUser(user);
        return taskRepository.save(newTask);
    }

    public Page<Task> getUserTasks(String username, Boolean completed, Pageable pageable) {
        User user = userService.findByUsername(username);

        if (completed == null) {
            return taskRepository.findAllByUser_Id(user.getId(),pageable);
        } else {
            return taskRepository.findTaskByUserIdAndCompleted(user.getId(),completed,pageable);
        }
    }

    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    public Task markTaskAsCompleted(Long id) {
        Task task = taskRepository.findById(id).orElseThrow();
        task.setCompleted(true);
        return taskRepository.save(task);
    }
}
