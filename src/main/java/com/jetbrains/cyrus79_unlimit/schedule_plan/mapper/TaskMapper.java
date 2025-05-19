package com.jetbrains.cyrus79_unlimit.schedule_plan.mapper;

import com.jetbrains.cyrus79_unlimit.schedule_plan.dto.response.TaskDto;
import com.jetbrains.cyrus79_unlimit.schedule_plan.entity.Task;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TaskMapper {
    private final ModelMapper modelMapper;

    public TaskDto toDto(Task task) {
        return modelMapper.map(task,TaskDto.class);
    }
}
