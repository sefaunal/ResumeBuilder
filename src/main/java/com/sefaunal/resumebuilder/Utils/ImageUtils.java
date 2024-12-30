package com.sefaunal.resumebuilder.Utils;

import com.google.auth.Credentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.auth.oauth2.GoogleCredentials;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author github.com/sefaunal
 * @since 2024-01-18
 */
public class ImageUtils {
    private static final Logger LOG = LoggerFactory.getLogger(ImageUtils.class);

    private static final String BUCKET_NAME = "resumebuilder-f10a4.appspot.com";

    private static final String DOWNLOAD_URL = "https://firebasestorage.googleapis.com/v0/b/" + BUCKET_NAME + "/o/%s?alt=media";

    public static String generateUniqueFilename(String username, String contentType) {
        // Extract the file extension from the content type
        String fileExtension = contentType.substring(contentType.lastIndexOf("/") + 1);

        // Get the current timestamp
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String timestamp = dateFormat.format(now);

        // Generate a unique filename using the user's ID and timestamp
        return username + "_" + timestamp + "." + fileExtension;
    }

    public static Credentials firebaseCredentials() {
        try {
            InputStream serviceAccount = new ClassPathResource("/cloud/firebase.json").getInputStream();
            return GoogleCredentials.fromStream(serviceAccount);
        } catch (Exception e) {
            LOG.error("Error getting Firebase Credentials: " + e.getMessage());
            throw new RuntimeException("Error occurred during getting Firebase Credentials");
        }
    }

    public static String firebaseUploadImage(MultipartFile profileImage, String uniqueFilename) {
        try {
            String destinationPath = "images/" + uniqueFilename;

            BlobId blobId = BlobId.of(BUCKET_NAME, destinationPath);

            BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                    .setContentType(profileImage.getContentType())
                    .build();

            Storage storage = StorageOptions.newBuilder().setCredentials(firebaseCredentials()).build().getService();
            storage.create(blobInfo, profileImage.getBytes());

            return String.format(DOWNLOAD_URL, URLEncoder.encode(destinationPath, StandardCharsets.UTF_8));
        } catch (Exception e) {
            LOG.error("Error uploading image to Firebase Storage: " + e.getMessage());
            throw new RuntimeException("Error occurred during uploading image to Firebase");
        }
    }
}
