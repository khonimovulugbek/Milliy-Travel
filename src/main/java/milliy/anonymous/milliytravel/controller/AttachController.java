package milliy.anonymous.milliytravel.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import milliy.anonymous.milliytravel.dto.Result;
import milliy.anonymous.milliytravel.dto.detail.AttachDTO;
import milliy.anonymous.milliytravel.service.AttachService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static milliy.anonymous.milliytravel.util.Utils.createResponse;

@Slf4j
@RestController
@RequestMapping("/api/v1/attach")
@RequiredArgsConstructor
public class AttachController {

    private final AttachService attachService;

    /**
     * PUBLIC
     */

    @PostMapping("/upload")
    @PreAuthorize("hasAnyRole('GUIDE','TOURIST')")
    public ResponseEntity<AttachDTO> upload(@RequestParam MultipartFile file) {
        log.info("Upload File fileName=" + file.getOriginalFilename() + ", fileSize={}", file.getSize());
        AttachDTO upload = attachService.upload(file, null);
        return upload != null ? ResponseEntity.ok(upload) : ResponseEntity.ok().build();
    }


    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyRole('GUIDE','TOURIST')")
    public ResponseEntity<Boolean> delete(@PathVariable("id") String id) {
        log.info("Delete File fileId={}", id);
        return ResponseEntity.ok(attachService.delete(id));
    }

}
