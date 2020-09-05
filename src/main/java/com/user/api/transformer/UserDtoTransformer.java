package com.user.api.transformer;

import com.user.api.dto.UserDto;
import com.user.db.entity.User;

public class UserDtoTransformer {
	public static User transform(UserDto userDto) {
		return User.builder()
		        .firstname(userDto.getFirstname())
		        .middlename(userDto.getMiddlename())
		        .lastname(userDto.getLastname())
		        .build();
	}
}
