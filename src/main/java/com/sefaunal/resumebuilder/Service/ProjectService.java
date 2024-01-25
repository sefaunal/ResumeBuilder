package com.sefaunal.resumebuilder.Service;

import com.sefaunal.resumebuilder.Exception.PasswordException;
import com.sefaunal.resumebuilder.Model.Project;
import com.sefaunal.resumebuilder.Repository.ProjectRepository;
import com.sefaunal.resumebuilder.Request.ProjectRequest;
import com.sefaunal.resumebuilder.Utils.CommonUtils;
import com.sefaunal.resumebuilder.Utils.ImageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.Collection;
import java.util.Objects;

/**
 * @author github.com/sefaunal
 * @since 2024-01-23
 */
@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;

    public void addProject(Project project, MultipartFile projectImage, String userID) {
        String uniqueFilename = ImageUtils.generateUniqueFilename(
                CommonUtils.getUserInfo(),
                Objects.requireNonNull(projectImage.getContentType())
        );
        String imageURI = ImageUtils.firebaseUploadImage(projectImage, uniqueFilename);

        project.setUserID(userID);
        project.setImageURI(imageURI);
        project.setAdditionDate(Instant.now());

        projectRepository.save(project);
    }

    public Project findRecordByID(String ID) {
        return projectRepository.findByID(ID).orElseThrow();
    }

    public Collection<Project> findAllProjectsByUserID(String userID) {
        return projectRepository.findAllByUserID(userID);
    }

    public void deleteRecordByID(String projectID, String userID) {
        Project project = projectRepository.findByID(projectID).orElseThrow();

        if (!project.getUserID().equals(userID)) {
            throw new AccessDeniedException("IDs Don't Match. You Are Not Authorized!");
        }

        projectRepository.deleteById(projectID);
    }

    public void updateRecordByID(ProjectRequest projectRequest, MultipartFile projectImage, String userPassword, String userID) {
        Project project = projectRepository.findByID(projectRequest.getID()).orElseThrow();

        if (!project.getUserID().equals(userID)) {
            throw new AccessDeniedException("IDs Don't Match. You Are Not Authorized!");
        }

        if (!CommonUtils.checkPasswordsMatch(projectRequest.getPassword(), userPassword)) {
            throw new PasswordException("Passwords Does Not Match");
        }

        if (!projectImage.isEmpty()) {
            String uniqueFilename = ImageUtils.generateUniqueFilename(
                    CommonUtils.getUserInfo(),
                    Objects.requireNonNull(projectImage.getContentType())
            );

            String imageURI = ImageUtils.firebaseUploadImage(projectImage, uniqueFilename);

            project.setImageURI(imageURI);
        }

        project.setTitle(projectRequest.getTitle());
        project.setDescription(projectRequest.getTitle());

        projectRepository.save(project);
    }
}
