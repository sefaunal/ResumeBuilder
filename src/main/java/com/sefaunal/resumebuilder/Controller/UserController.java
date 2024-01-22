package com.sefaunal.resumebuilder.Controller;

import com.sefaunal.resumebuilder.Model.User;
import com.sefaunal.resumebuilder.Request.UserRequest;
import com.sefaunal.resumebuilder.Service.UserService;
import com.sefaunal.resumebuilder.Utils.CommonUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;

/**
 * @author github.com/sefaunal
 * @since 2024-01-14
 */
@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/resume")
    public ModelAndView resumePage(Principal principal, Model model) {
        User user = userService.findUserByUsername(principal.getName());

        model.addAttribute("user", user);
        return new ModelAndView("ResumePage");
    }

    @GetMapping("/profile")
    public ModelAndView profilePage(Principal principal, Model model) {
        User user = userService.findUserByUsername(principal.getName());

        model.addAttribute("user", user);
        return new ModelAndView("AccountPage");
    }

    @PostMapping("/profile/password/update")
    public ModelAndView updatePassword(@RequestParam String oldPassword, @RequestParam String newPassword, Model model) {
        if (userService.updateUserPassword(oldPassword, newPassword)) {
            model.addAttribute("passwordChanged", true);
        } else {
            model.addAttribute("passwordChangedFail", true);
        }

        model.addAttribute("user", userService.findUserByUsername(CommonUtils.getUserInfo()));
        return new ModelAndView("AccountPage");
    }

    @PostMapping("/profile/details/update")
    public ModelAndView updateProfile(@Valid @ModelAttribute UserRequest userRequest, Model model) {
        if  (userService.updateUserProfile(userRequest)) {
            model.addAttribute("profileUpdated", true);
        } else {
            model.addAttribute("profileUpdatedFail", true);
        }
        model.addAttribute("user", userService.findUserByUsername(CommonUtils.getUserInfo()));
        return new ModelAndView("AccountPage");
    }

    @PostMapping("/profile/photo/update")
    public ModelAndView updatePhoto(@RequestParam MultipartFile profileImage, Model model) {
        User user = userService.updateUserProfileImage(profileImage);

        model.addAttribute("user", user);
        return new ModelAndView("AccountPage");
    }

    @PostMapping("/profile/account/deactivate")
    public ModelAndView deactivateAccount(HttpServletRequest httpServletRequest, @RequestParam String confirmPassword, Model model) {
        if (userService.deactivateUser(httpServletRequest, confirmPassword)) {
            model.addAttribute("user", null);
            return new ModelAndView("Home");
        }

        model.addAttribute("user", userService.findUserByUsername(CommonUtils.getUserInfo()));
        model.addAttribute("accountDeletionFail", true);
        return new ModelAndView("AccountPage");
    }
}