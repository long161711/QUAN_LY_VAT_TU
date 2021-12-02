package com.example.demo.Controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping(value = "")
    public String index() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
        if (auth.isAuthenticated()) {
            return "redirect:/dashboard";
        } else {
            return "redirect:/sign-in";
        }
    }
    @GetMapping(value = "/taotaikhoan")
    public String ttk() {
    	return "layouts/front/taotaikhoan";
    }
    
}
