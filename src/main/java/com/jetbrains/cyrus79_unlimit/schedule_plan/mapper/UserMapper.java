package com.jetbrains.cyrus79_unlimit.schedule_plan.mapper;

import com.jetbrains.cyrus79_unlimit.schedule_plan.dto.response.UserDto;
import com.jetbrains.cyrus79_unlimit.schedule_plan.entity.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {
    private final ModelMapper modelMapper;

    public UserDto toDto(User user) {
        return  modelMapper.map(user,UserDto.class);
    }

}
