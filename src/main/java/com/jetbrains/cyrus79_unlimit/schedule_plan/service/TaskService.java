package com.jetbrains.cyrus79_unlimit.schedule_plan.service;

import com.jetbrains.cyrus79_unlimit.schedule_plan.dto.CreateTaskRequest;
import com.jetbrains.cyrus79_unlimit.schedule_plan.entity.Task;
import com.jetbrains.cyrus79_unlimit.schedule_plan.entity.User;
import com.jetbrains.cyrus79_unlimit.schedule_plan.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

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

    public List<Task> getTasksByUserId(Long userId) {
        return taskRepository.findAllByUserId(userId);
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
