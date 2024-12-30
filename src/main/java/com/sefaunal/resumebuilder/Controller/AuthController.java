package com.sefaunal.resumebuilder.Controller;

import com.sefaunal.resumebuilder.Model.User;
import com.sefaunal.resumebuilder.Service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

/**
 * @author github.com/sefaunal
 * @since 2024-01-14
 */
@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    private static final Logger LOG = LoggerFactory.getLogger(AuthController.class);

    @GetMapping("/login")
    public String loginPage(HttpServletResponse httpServletResponse, Principal principal) {
        if (principal != null){
            try {
                User user = userService.findUserByUsername(principal.getName());
                if (user.getRole().equals("ADMIN")){
                    httpServletResponse.sendRedirect("/admin/panel");
                } else {
                    httpServletResponse.sendRedirect("/home");
                }
            } catch (Exception e) {
                LOG.error("Error Occurred {}", e.getMessage());
            }
        }
        return "LoginPage";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "RegisterPage";
    }

    @PostMapping("/register")
    public ModelAndView completeRegistration(@Valid @ModelAttribute User user, Model model) {
        userService.createUser(user);

        model.addAttribute("registered", true);
        return new ModelAndView("LoginPage");
    }

    @GetMapping("/reset/password")
    public String forgotPasswordPage() {
        return "ResetPassword";
    }

    @PostMapping("/reset/password")
    public String resetPassword() {
        return "MaintenancePage";
    }
}