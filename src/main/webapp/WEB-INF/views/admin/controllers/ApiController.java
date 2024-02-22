package com.aptech.eProject.controllers;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApiController {

	/**
	 * Parse field errors
	 *
	 * @param rs
	 * @return
	 */
	public Map<String, String> parseFieldErrors(BindingResult rs) {
		List<FieldError> fieldErrors = rs.getFieldErrors();
		Map<String, String> errors = new HashMap<>();

		for (FieldError error : fieldErrors) {
			errors.put(error.getField(), error.getDefaultMessage());
		}
		return errors;
	}
}
