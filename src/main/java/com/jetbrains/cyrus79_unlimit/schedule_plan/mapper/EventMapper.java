package com.jetbrains.cyrus79_unlimit.schedule_plan.mapper;

import com.jetbrains.cyrus79_unlimit.schedule_plan.dto.response.EventDto;
import com.jetbrains.cyrus79_unlimit.schedule_plan.entity.Event;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventMapper {
    private final ModelMapper modelMapper;

    public EventDto toDto(Event event) {
        return modelMapper.map(event,EventDto.class);
    }
}
