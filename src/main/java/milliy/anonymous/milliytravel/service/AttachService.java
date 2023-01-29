package milliy.anonymous.milliytravel.service;

import milliy.anonymous.milliytravel.dto.Result;
import milliy.anonymous.milliytravel.dto.detail.AttachDTO;
import milliy.anonymous.milliytravel.entity.AttachEntity;
import org.springframework.web.multipart.MultipartFile;


public interface AttachService {
     AttachDTO upload(MultipartFile multipartFile, String folder);
     AttachEntity getByLink(String link);
     Boolean delete(String id);
}
