package com.sefaunal.resumebuilder.Controller;

import com.sefaunal.resumebuilder.Model.Experience;
import com.sefaunal.resumebuilder.Model.Skill;
import com.sefaunal.resumebuilder.Model.User;
import com.sefaunal.resumebuilder.Request.ExperienceRequest;
import com.sefaunal.resumebuilder.Service.ExperienceService;
import com.sefaunal.resumebuilder.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;

/**
 * @author github.com/sefaunal
 * @since 2024-01-23
 */
@Controller
@RequestMapping("/user/experience")
@RequiredArgsConstructor
public class ExperienceController {
    private final UserService userService;

    private final ExperienceService experienceService;

    @PostMapping("/add")
    public RedirectView addExperience(@ModelAttribute Experience experience, Principal principal) {
        User user = userService.findUserByUsername(principal.getName());

        experienceService.addExperience(experience, user.getID());

        return new RedirectView("/user/resume/details");
    }

    @GetMapping("/delete")
    public RedirectView deleteExperienceByID(@RequestParam String ID, Principal principal) {
        User user = userService.findUserByUsername(principal.getName());

        experienceService.deleteRecordByID(ID, user.getID());

        return new RedirectView("/user/resume/details");
    }

    @GetMapping("/update")
    public ModelAndView updateExperiencePage(@RequestParam String ID, Model model, Principal principal) {
        User user = userService.findUserByUsername(principal.getName());
        Experience experience = experienceService.findRecordByID(ID);

        model.addAttribute("user", user);
        model.addAttribute("experience", experience);
        model.addAttribute("updateType", "EXPERIENCE");

        return new ModelAndView("UpdatePage");
    }

    @PostMapping("/update")
    public RedirectView updateExperienceRecord(@ModelAttribute ExperienceRequest experienceRequest, Principal principal) {
        User user = userService.findUserByUsername(principal.getName());

        experienceService.updateRecordByID(experienceRequest, user.getPassword(), user.getID());

        return new RedirectView("/user/resume/details");
    }
}
