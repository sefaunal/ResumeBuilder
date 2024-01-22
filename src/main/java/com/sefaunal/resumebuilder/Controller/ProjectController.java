package com.sefaunal.resumebuilder.Controller;

import com.sefaunal.resumebuilder.Model.Project;
import com.sefaunal.resumebuilder.Model.User;
import com.sefaunal.resumebuilder.Service.ProjectService;
import com.sefaunal.resumebuilder.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
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
}
