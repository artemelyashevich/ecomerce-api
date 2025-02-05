package com.elyashevich.user.api.mapper;

import com.elyashevich.user.api.dto.UserDto;
import com.elyashevich.user.api.dto.UserRequestDto;
import com.elyashevich.user.domain.entity.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toUserDto(final User user);

    UserRequestDto toUserRequestDto(final User user);

    User toUser(final UserDto userDto);

    User toUser(final UserRequestDto userRequestDto);

    List<UserDto> toUserDtoList(final List<User> userDtoList);
}
