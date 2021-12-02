package com.example.demo.Controller.errors;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController {

	@GetMapping(value = "/403")
	public String e403() {
		if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			return new String("layouts/admin/pages/403");
		}
		return new String("layouts/admin/front/sign-in");
	}

}
