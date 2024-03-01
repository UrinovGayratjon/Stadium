package uz.urinov.stadion.controller;

import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.urinov.stadion.service.AttachmentService;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/attachment")
@AllArgsConstructor
public class AttachmentController {
    private final AttachmentService attachmentService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        return ResponseEntity.ok(attachmentService.upload(file));
    }

    @GetMapping("/download/{id}")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable Long id) throws IOException {
        Resource file = attachmentService.getFileById(id);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @PostMapping("/upload/{stadiumId}")
    public ResponseEntity<?> uploadFileForStadium(
            @PathVariable Long stadiumId,
            @RequestParam("file") MultipartFile file) throws IOException {
        attachmentService.upload(stadiumId, file);
        return ResponseEntity.ok("");
    }

    @DeleteMapping("/id")
    public ResponseEntity<?> deleteAttachment(@PathVariable Long id) {
        attachmentService.deleteAttachment(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
