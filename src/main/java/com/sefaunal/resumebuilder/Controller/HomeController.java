package com.sefaunal.resumebuilder.Controller;

import com.sefaunal.resumebuilder.Model.User;
import com.sefaunal.resumebuilder.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;

/**
 * @author github.com/sefaunal
 * @since 2024-01-14
 */
@Controller
@RequiredArgsConstructor
public class HomeController {
    private final UserService userService;

    @GetMapping("/")
    public RedirectView redirectToHome() {
        return new RedirectView("/home");
    }

    @GetMapping("/home")
    public ModelAndView returnHome(Principal principal, Model model) {
        if (principal == null) {
            model.addAttribute("user", null);
        } else {
            User user = userService.findUserByUsername(principal.getName());
            model.addAttribute("user", user);
        }
        return new ModelAndView("Home");
    }
}