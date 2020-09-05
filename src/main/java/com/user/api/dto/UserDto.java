package com.user.api.dto;

import javax.annotation.Nonnull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Nonnull
public class UserDto {
	private String firstname;
	private String middlename;
	private String lastname;
}
