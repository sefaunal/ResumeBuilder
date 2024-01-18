package com.sefaunal.resumebuilder.Service;

import com.sefaunal.resumebuilder.Model.User;
import com.sefaunal.resumebuilder.Repository.UserRepository;
import com.sefaunal.resumebuilder.Request.UserRequest;
import com.sefaunal.resumebuilder.Utils.CommonUtils;
import com.sefaunal.resumebuilder.Utils.ImageUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
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

    public User findUserByEmail(String userMail) {
        return userRepository.findByEmail(userMail)
                .orElseThrow(() -> new UsernameNotFoundException("No user found with " + userMail));
    }

    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("No user found with " + username));
    }

    public boolean isEmailFree(String email) {
        return userRepository.findByEmail(email).isEmpty();
    }

    public boolean isUsernameFree(String username) {
        return userRepository.findByUsername(username).isEmpty();
    }

    public void createUser(User user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        user.setRole("USER");
        user.setAccountCreationDate(Instant.now());
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
        boolean isOldPasswordCorrect = new BCryptPasswordEncoder().matches(oldPassword, user.getPassword());

        if (isOldPasswordCorrect) {
            user.setPassword(new BCryptPasswordEncoder().encode(newPassword));
            userRepository.save(user);
        }

        return false;
    }

    public Boolean updateUserProfile(UserRequest userRequest) {
        User originalUser = findUserByUsername(CommonUtils.getUserInfo());
        boolean isOldPasswordCorrect = new BCryptPasswordEncoder().matches(userRequest.getPassword(), originalUser.getPassword());

        if (isOldPasswordCorrect) {
            originalUser.setFirstName(userRequest.getFirstName());
            originalUser.setLastName(userRequest.getLastName());
            originalUser.setEmail(userRequest.getEmail());

            userRepository.save(originalUser);
        }
        return false;
    }

    public Boolean deactivateUser(HttpServletRequest httpServletRequest, String confirmPassword) {
        User user = findUserByUsername(CommonUtils.getUserInfo());
        boolean isConfirmPasswordCorrect = new BCryptPasswordEncoder().matches(confirmPassword, user.getPassword());

        if (isConfirmPasswordCorrect) {
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
}