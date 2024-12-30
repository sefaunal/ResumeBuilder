package com.sefaunal.resumebuilder.Controller;

import com.sefaunal.resumebuilder.Model.Project;
import com.sefaunal.resumebuilder.Model.User;
import com.sefaunal.resumebuilder.Request.ProjectRequest;
import com.sefaunal.resumebuilder.Service.ProjectService;
import com.sefaunal.resumebuilder.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;

/**
 * @author github.com/sefaunal
 * @since 2024-01-23
 */
@Controller
@RequestMapping("/user/project")
@RequiredArgsConstructor
public class ProjectController {
    private final UserService userService;

    private final ProjectService projectService;

    @PostMapping("/add")
    public RedirectView addProject(@ModelAttribute Project project,
                                   @RequestParam MultipartFile projectImage,
                                   Principal principal) {
        User user = userService.findUserByUsername(principal.getName());

        projectService.addProject(project, projectImage, user.getID());

        return new RedirectView("/user/resume/details");
    }

    @GetMapping("/delete")
    public RedirectView deleteProjectByID(@RequestParam String ID, Principal principal) {
        User user = userService.findUserByUsername(principal.getName());

        projectService.deleteRecordByID(ID, user.getID());

        return new RedirectView("/user/resume/details");
    }

    @GetMapping("/update")
    public ModelAndView updateProjectPage(@RequestParam String ID, Model model, Principal principal) {
        User user = userService.findUserByUsername(principal.getName());
        Project project = projectService.findRecordByID(ID);

        model.addAttribute("user", user);
        model.addAttribute("project", project);
        model.addAttribute("updateType", "PROJECT");

        return new ModelAndView("UpdatePage");
    }

    @PostMapping("/update")
    public RedirectView updateProjectRecord(@ModelAttribute ProjectRequest projectRequest,
                                            @RequestParam MultipartFile projectImage,
                                            Principal principal) {
        User user = userService.findUserByUsername(principal.getName());

        projectService.updateRecordByID(projectRequest, projectImage, user.getPassword(), user.getID());

        return new RedirectView("/user/resume/details");
    }

    @GetMapping("/details")
    public ModelAndView viewProjectDetails(@RequestParam String ID, Principal principal, Model model) {
        User user = userService.findUserByUsername(principal.getName());

        Project project = projectService.findRecordByID(ID);

        model.addAttribute("project", project);
        model.addAttribute("user", user);

        return new ModelAndView("ProjectDetails");
    }
}
