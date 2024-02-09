package com.sefaunal.resumebuilder.Service;

import com.sefaunal.resumebuilder.Exception.PasswordException;
import com.sefaunal.resumebuilder.Model.User;
import com.sefaunal.resumebuilder.Model.UserAboutMe;
import com.sefaunal.resumebuilder.Model.UserVisibilitySettings;
import com.sefaunal.resumebuilder.Repository.UserRepository;
import com.sefaunal.resumebuilder.Request.UserRequest;
import com.sefaunal.resumebuilder.Utils.CommonUtils;
import com.sefaunal.resumebuilder.Utils.Constants;
import com.sefaunal.resumebuilder.Utils.ImageUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.Objects;

/**
 * @author github.com/sefaunal
 * @since 2024-01-14
 */
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("No user found with " + username));
    }

    public User findUserByID(String ID) {
        return userRepository.findById(ID)
                .orElseThrow(() -> new UsernameNotFoundException("No user found with " + ID));
    }

    public boolean isEmailFree(String email) {
        return userRepository.findByEmail(email).isEmpty();
    }

    public boolean isUsernameFree(String username) {
        return userRepository.findByUsername(username).isEmpty();
    }

    public void createUser(User user) {
        UserVisibilitySettings visibilitySettings = new UserVisibilitySettings();
        UserAboutMe aboutMe = new UserAboutMe();

        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        user.setRole("USER");
        user.setAccountCreationDate(Instant.now());
        user.setProfileImageURI(Constants.DEFAULT_IMAGE_URL);
        user.setVisibilitySettings(visibilitySettings);
        user.setAboutMe(aboutMe);
        user.setAccountEnabled(true);
        user.setAccountNonLocked(true);

        userRepository.save(user);
    }

    public User updateUserProfileImage(MultipartFile profileImage) {
        String uniqueFilename = ImageUtils.generateUniqueFilename(CommonUtils.getUserInfo(), Objects.requireNonNull(profileImage.getContentType()));

        String imageURI = ImageUtils.firebaseUploadImage(profileImage, uniqueFilename);

        User user = findUserByUsername(CommonUtils.getUserInfo());
        user.setProfileImageURI(imageURI);

        userRepository.save(user);

        return user;
    }

    public Boolean updateUserPassword(String oldPassword, String newPassword) {
        User user = findUserByUsername(CommonUtils.getUserInfo());

        if (CommonUtils.checkPasswordsMatch(oldPassword, user.getPassword())) {
            user.setPassword(new BCryptPasswordEncoder().encode(newPassword));
            userRepository.save(user);
            return true;
        }

        return false;
    }

    public Boolean updateUserProfile(UserRequest userRequest) {
        User originalUser = findUserByUsername(CommonUtils.getUserInfo());

        if (CommonUtils.checkPasswordsMatch(userRequest.getPassword(), originalUser.getPassword())) {
            originalUser.setFirstName(userRequest.getFirstName());
            originalUser.setLastName(userRequest.getLastName());
            originalUser.setEmail(userRequest.getEmail());

            userRepository.save(originalUser);
            return true;
        }
        return false;
    }

    public boolean updateVisibilitySettings(UserVisibilitySettings visibilityRequest, String confirmationPassword) {
        User originalUser = findUserByUsername(CommonUtils.getUserInfo());

        if (CommonUtils.checkPasswordsMatch(confirmationPassword, originalUser.getPassword())) {
            originalUser.setVisibilitySettings(visibilityRequest);
            userRepository.save(originalUser);
            return true;
        }
        return false;
    }

    public void addAboutMe(UserAboutMe aboutMe) {
        User user = findUserByUsername(CommonUtils.getUserInfo());

        if (!user.getAboutMe().isEmpty()) {
            throw new AccessDeniedException("About Me Already Exists");
        }

        user.setAboutMe(aboutMe);
        userRepository.save(user);
    }

    public void updateUserAboutMe(UserAboutMe userAboutMe, String confirmationPassword) {
        User user = findUserByUsername(CommonUtils.getUserInfo());

        if (!CommonUtils.checkPasswordsMatch(confirmationPassword, user.getPassword())) {
            throw new PasswordException("Passwords Does Not Match");
        }

        user.setAboutMe(userAboutMe);
        userRepository.save(user);
    }

    public void deleteAboutMe(User user) {
        user.setAboutMe(null);
        userRepository.save(user);
    }

    public Boolean deactivateUser(HttpServletRequest httpServletRequest, String confirmPassword) {
        User user = findUserByUsername(CommonUtils.getUserInfo());

        if (CommonUtils.checkPasswordsMatch(confirmPassword, user.getPassword())) {
            userRepository.deleteByUsername(CommonUtils.getUserInfo());

            // Clear SecurityContext
            SecurityContextHolder.clearContext();

            // Invalidate Session
            HttpSession session = httpServletRequest.getSession(false);
            if (session != null) {
                session.invalidate();
            }
        }
        return false;
    }

    public Page<User> findByRoleWithPageable(String role, Pageable pageable) {
        return userRepository.findByRole(role, pageable);
    }

    public void grantAdminPrivileges(User user) {
        user.setRole("ADMIN");
        userRepository.save(user);
    }

    public void revokeAdminPrivileges(User user) {
        user.setRole("USER");
        userRepository.save(user);
    }

    public void banUser(User user) {
        user.setRole("BANNED");
        user.setAccountNonLocked(false);
        user.setAccountEnabled(false);

        userRepository.save(user);
    }

    public void unbanUser(User user) {
        user.setRole("USER");
        user.setAccountNonLocked(true);
        user.setAccountEnabled(true);

        userRepository.save(user);
    }
}