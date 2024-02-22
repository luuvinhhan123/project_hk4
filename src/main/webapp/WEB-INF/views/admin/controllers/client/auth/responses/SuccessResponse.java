package com.aptech.eProject.controllers.client.auth.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class SuccessResponse<T> {

	public String message;
	public T data;
	public int code;
}
