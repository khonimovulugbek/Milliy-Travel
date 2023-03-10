package milliy.anonymous.milliytravel.google;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Collections;

@Service
@Slf4j
public class GoogleDriveServiceImp implements GoogleDriverService {

    @Value("${service.account.email}")
    private String serviceAccountEmail;

    @Value("${application.name}")
    private String applicationName;

    @Value("${service.account.key}")
    private String serviceAccountKey;


    @Value("${drive.key.location}")
    private String location;

    @Value("${server.key.location}")
    private String serverLocation;


    public Drive getDriveService() {
        Drive service = null;
        try {
            java.io.File key = null;

            key = Paths.get(serverLocation + this.serviceAccountKey).toFile();
            if (!key.exists()) {
                URL resource = GoogleDriveServiceImp.class.getResource(location + this.serviceAccountKey);
                assert resource != null;
                key = Paths.get(resource.toURI()).toFile();
            }


            HttpTransport httpTransport = new NetHttpTransport();
            JacksonFactory jsonFactory = new JacksonFactory();

            GoogleCredential credential = new GoogleCredential.Builder().setTransport(httpTransport)
                    .setJsonFactory(jsonFactory).setServiceAccountId(serviceAccountEmail)
                    .setServiceAccountScopes(Collections.singleton(DriveScopes.DRIVE))
                    .setServiceAccountPrivateKeyFromP12File(key).build();
            service = new Drive.Builder(httpTransport, jsonFactory, credential).setApplicationName(applicationName)
                    .setHttpRequestInitializer(credential).build();
        } catch (Exception e) {
            log.error(e.getMessage());

        }

        return service;

    }


    @Override
    public File upload(String fileName, String filePath, String fileType) {
        return null;
    }

    @Override
    public File upload(String fileName, String filePath, String fileType, String folder) {

        File file = new File();
        try {


            java.io.File fileUpload = new java.io.File(filePath);

            File fileMetadata = new File();
            fileMetadata.setMimeType(fileType);
            fileMetadata.setName(fileName);
            fileMetadata.setParents(Collections.singletonList(folder));

            FileContent fileContent = new FileContent(fileType, fileUpload);

            file = getDriveService().files().create(fileMetadata, fileContent)
                    .setFields("id,webContentLink,webViewLink").execute();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return file;
    }

    public Boolean delete(String fileId) {
        try {
            getDriveService().files().delete(fileId).execute();
            return true;
        } catch (IOException e) {
            log.error("An error occurred: " + e);
        }
        return false;
    }
}
