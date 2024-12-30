package com.sefaunal.resumebuilder.Controller;

import com.sefaunal.resumebuilder.Model.Skill;
import com.sefaunal.resumebuilder.Model.User;
import com.sefaunal.resumebuilder.Request.SkillRequest;
import com.sefaunal.resumebuilder.Service.SkillService;
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
 * @since 2024-01-22
 */
@Controller
@RequestMapping("/user/skill")
@RequiredArgsConstructor
public class SkillController {
    private final UserService userService;

    private final SkillService skillService;

    @PostMapping("/core/add")
    public RedirectView addCoreSkill(@RequestParam String skillName, Principal principal) {
        User user = userService.findUserByUsername(principal.getName());

        skillService.addSkill(skillName, "CORE", user.getID());

        return new RedirectView("/user/resume/details");
    }

    @PostMapping("/other/add")
    public RedirectView addOtherSkill(@RequestParam String skillName, Principal principal) {
        User user = userService.findUserByUsername(principal.getName());

        skillService.addSkill(skillName, "OTHER", user.getID());

        return new RedirectView("/user/resume/details");
    }

    @GetMapping("/delete")
    public RedirectView deleteSkillByID(@RequestParam String ID, Principal principal) {
        User user = userService.findUserByUsername(principal.getName());

        skillService.deleteRecordByID(ID, user.getID());

        return new RedirectView("/user/resume/details");
    }

    @GetMapping("/update")
    public ModelAndView updateSkillPage(@RequestParam String ID, Model model, Principal principal) {
        User user = userService.findUserByUsername(principal.getName());
        Skill skill = skillService.findRecordByID(ID);

        model.addAttribute("user", user);
        model.addAttribute("skill", skill);
        model.addAttribute("updateType", "SKILL");

        return new ModelAndView("UpdatePage");
    }

    @PostMapping("/update")
    public RedirectView updateSkillRecord(@ModelAttribute SkillRequest skillRequest, Principal principal) {
        User user = userService.findUserByUsername(principal.getName());

        skillService.updateRecordByID(skillRequest, user.getPassword(), user.getID());

        return new RedirectView("/user/resume/details");
    }
}
