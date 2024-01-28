package com.sefaunal.resumebuilder.Controller;

import com.sefaunal.resumebuilder.Model.Experience;
import com.sefaunal.resumebuilder.Model.Project;
import com.sefaunal.resumebuilder.Model.Skill;
import com.sefaunal.resumebuilder.Model.User;
import com.sefaunal.resumebuilder.Model.UserAboutMe;
import com.sefaunal.resumebuilder.Model.UserVisibilitySettings;
import com.sefaunal.resumebuilder.Request.UserRequest;
import com.sefaunal.resumebuilder.Service.ExperienceService;
import com.sefaunal.resumebuilder.Service.ProjectService;
import com.sefaunal.resumebuilder.Service.SkillService;
import com.sefaunal.resumebuilder.Service.UserService;
import com.sefaunal.resumebuilder.Utils.CommonUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.util.Collection;

/**
 * @author github.com/sefaunal
 * @since 2024-01-14
 */
@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    private final SkillService skillService;

    private final ExperienceService experienceService;

    private final ProjectService projectService;

    @GetMapping("/resume")
    public ModelAndView resumePage(Principal principal, Model model) {
        setUserRelatedInfoToModel(principal, model);

        return new ModelAndView("ResumePage");
    }

    @GetMapping("/profile")
    public ModelAndView profilePage(Principal principal, Model model) {
        User user = userService.findUserByUsername(principal.getName());

        model.addAttribute("user", user);
        return new ModelAndView("AccountPage");
    }

    @GetMapping("/visibility")
    public ModelAndView visibilityPage(Principal principal, Model model) {
        User user = userService.findUserByUsername(principal.getName());

        model.addAttribute("user", user);
        return new ModelAndView("VisibilitySettings");
    }

    @GetMapping("/resume/details")
    public RedirectView redirectToResumeDetailsPage() {
        return new RedirectView("/user/resume/details/none");
    }

    @GetMapping("/resume/details/{errorStatus}")
    public ModelAndView resumeDetailsPage(@PathVariable String errorStatus, Principal principal, Model model) {
        setUserRelatedInfoToModel(principal, model);

        model.addAttribute("errorStatus", errorStatus);

        return new ModelAndView("ResumeDetails");
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

    @PostMapping("/visibility/update")
    public ModelAndView updateVisibilitySettings(@ModelAttribute UserVisibilitySettings visibilityRequest,
                                                 @RequestParam String confirmationPassword,
                                                 Model model) {
        if (userService.updateVisibilitySettings(visibilityRequest, confirmationPassword)) {
            model.addAttribute("visibilitySettingsUpdated", true);
        } else {
            model.addAttribute("visibilitySettingsUpdateFail", true);
        }

        model.addAttribute("user", userService.findUserByUsername(CommonUtils.getUserInfo()));
        return new ModelAndView("VisibilitySettings");
    }

    @PostMapping("/about/add")
    public RedirectView addAboutMe(@ModelAttribute UserAboutMe aboutMe) {
        userService.addAboutMe(aboutMe);

        return new RedirectView("/user/resume/details");
    }

    @GetMapping("/about/update")
    public ModelAndView updateAboutMePage(Principal principal, Model model) {
        User user = userService.findUserByUsername(principal.getName());

        model.addAttribute("user", user);
        model.addAttribute("updateType", "ABOUT");

        return new ModelAndView("UpdatePage");
    }

    @PostMapping("/about/update")
    public RedirectView updateAboutMe(@ModelAttribute UserAboutMe userAboutMe, String confirmationPassword) {
        userService.updateUserAboutMe(userAboutMe, confirmationPassword);

        return new RedirectView("/user/resume/details/success");
    }

    @GetMapping("/about/delete")
    public RedirectView deleteAboutMe(Principal principal) {
        User user = userService.findUserByUsername(principal.getName());

        userService.deleteAboutMe(user);

        return new RedirectView("/user/resume/details/success");
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

    private void setUserRelatedInfoToModel(Principal principal, Model model) {
        User user = userService.findUserByUsername(principal.getName());

        Collection<Skill> coreSkills = skillService.findAllCoreSkillsByUserID(user.getID());
        Collection<Skill> otherSkills = skillService.findAllOtherSkillsByUserID(user.getID());
        Collection<Experience> experiences = experienceService.findAllExperiencesByUserID(user.getID());
        Collection<Project> projects = projectService.findAllProjectsByUserID(user.getID());

        model.addAttribute("user", user);
        model.addAttribute("coreSkills", coreSkills);
        model.addAttribute("otherSkills", otherSkills);
        model.addAttribute("workExperiences", experiences);
        model.addAttribute("latestWorks", projects);
    }
}