package com.aptech.eProject.controllers.client.auth.responses.auth;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AuthResponse {
	private String token;
}
