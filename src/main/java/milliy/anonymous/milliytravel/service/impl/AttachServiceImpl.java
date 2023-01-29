package milliy.anonymous.milliytravel.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import milliy.anonymous.milliytravel.dto.Result;
import milliy.anonymous.milliytravel.dto.detail.AttachDTO;
import milliy.anonymous.milliytravel.entity.AttachEntity;
import milliy.anonymous.milliytravel.exception.AppBadRequestException;
import milliy.anonymous.milliytravel.exception.ItemNotFoundException;
import milliy.anonymous.milliytravel.google.GoogleDriveServiceImp;
import milliy.anonymous.milliytravel.google.MimTypes;
import milliy.anonymous.milliytravel.repository.AttachRepository;
import milliy.anonymous.milliytravel.service.AttachService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AttachServiceImpl implements AttachService {

    private final GoogleDriveServiceImp googleDriveServiceImp;

    private final AttachRepository attachRepository;

    @Value("${attach.upload.folder}")
    private String attachFolder;

    @Value("${trip.folder}")
    private String tripFolder;


    public AttachDTO upload(MultipartFile multipartFile, String folder) {

        File file = getFile(multipartFile);

        if (file == null) {
            log.warn("<< upload File is null");
            return null;
        }

        com.google.api.services.drive.model.File uploads;

        if (Optional.ofNullable(folder).isEmpty()) {
            uploads = googleDriveServiceImp.upload(file.getName(), file.getPath(), MimTypes.JPG, tripFolder);
        } else {
            uploads = googleDriveServiceImp.upload(file.getName(), file.getPath(), MimTypes.JPG, folder);
        }

        if (uploads == null) {
            log.warn("<< upload File google is null");
            return null;
        }

        AttachEntity attachEntity = new AttachEntity();
        attachEntity.setId(uploads.getId());
        attachEntity.setWebContentLink(uploads.getWebContentLink());
        attachEntity.setWebViewLink(uploads.getWebViewLink());

        remove(file.getPath());

        attachRepository.save(attachEntity);

        return toDTO(attachEntity);
    }

    public AttachDTO toDTO(AttachEntity attachEntity) {
        return new AttachDTO(attachEntity.getId(), attachEntity.getWebContentLink());
    }


    public File getFile(MultipartFile file) {
        File folder = new File(attachFolder);

        if (!folder.exists()) {
            var mkdirs = folder.mkdirs();
            log.info("File created! {}", mkdirs);
        }

        try {
            byte[] bytes = file.getBytes();
            String extension = file.getOriginalFilename() != null ? getExtension(file.getOriginalFilename()) : MimTypes.JPG.split("/")[1];

            Path url = Paths.get(folder.getAbsolutePath() + "/" + UUID.randomUUID() + "." + extension);

            Files.write(url, bytes);
            return url.toFile();
        } catch (IOException | RuntimeException e) {
            log.warn("Cannot Upload File {} " + e, file.getOriginalFilename());
            return null;
        }

    }

    public void remove(String local) {

        File file = new File(local);

        if (file.delete()) {
            log.info("file deleted from local");
        } else {
            log.error("Cannot Delete local={}", local);
        }

    }

    public Boolean delete(String id) {
        Optional<AttachEntity> entity = attachRepository.findById(id);
        if (entity.isEmpty()) {
            log.warn("<< delete attach is null");
            return false;
        }
        attachRepository.delete(entity.get());
        return googleDriveServiceImp.delete(id);
    }

    public String getExtension(String fileName) {
        int lastIndex = fileName.lastIndexOf(".");
        return fileName.substring(lastIndex + 1);
    }

    public AttachEntity getByLink(String link) {
        return attachRepository.findByWebContentLink(link)
                .orElseThrow(() -> {
                    log.warn("Photo Not Found link={}", link);
                    return new ItemNotFoundException("Photo Not Found!");
                });
    }

}
