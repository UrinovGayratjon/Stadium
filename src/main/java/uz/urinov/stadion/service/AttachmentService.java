package uz.urinov.stadion.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import uz.urinov.stadion.dto.ApiResponse;
import uz.urinov.stadion.entity.AttachmentEntity;
import uz.urinov.stadion.entity.StadiumEntity;
import uz.urinov.stadion.repository.AttachmentRepository;
import uz.urinov.stadion.repository.StadiumRepository;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.UUID;

@Service
public class AttachmentService {

    @Autowired
    private AttachmentRepository attachmentRepository;

    @Autowired
    private StadiumRepository stadiumRepository;

    private static final String uploadDirectory = "resourcesDirectory";
    private final Path path = Paths.get(uploadDirectory);

    public AttachmentEntity upload(MultipartFile file) throws IOException {
        if (file != null) {
            String originalFilename = file.getOriginalFilename();
            AttachmentEntity attachment = new AttachmentEntity();
            attachment.setFileOriginalName(originalFilename);
            attachment.setContentType(file.getContentType());
            attachment.setSize(file.getSize());

            String[] split = originalFilename.split("\\.");
            String name = UUID.randomUUID() + "." + split[split.length - 1];
            attachment.setName(name);
            AttachmentEntity savedAttachment = attachmentRepository.save(attachment);
            Path path2 = Paths.get(uploadDirectory + "/" + name);
            Files.createDirectories(path);
            InputStream inputStream = file.getInputStream();
            Files.copy(inputStream, path2,
                    StandardCopyOption.REPLACE_EXISTING);
            return savedAttachment;
        }
        throw new FileNotFoundException();
    }

    public Resource getFileById(Long id) throws IOException {
        AttachmentEntity attachment = attachmentRepository.findById(id).orElseThrow();
        try {
            Path file = load(attachment.getName());
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
        } catch (MalformedURLException e) {

        }
        return null;
    }

    public Path load(String filename) {
        return path.resolve(filename);
    }

    public void upload(Long stadiumId, MultipartFile file) throws IOException {
        AttachmentEntity upload = upload(file);
        StadiumEntity stadiumEntity = stadiumRepository.findById(stadiumId).orElseThrow();
        stadiumEntity.getAttachmentEntities().add(upload);
        stadiumRepository.save(stadiumEntity);
    }

    public void deleteAttachment(Long id) {
        AttachmentEntity attachment = attachmentRepository.findById(id).orElseThrow();
        attachmentRepository.delete(attachment);
        Path load = load(attachment.getName());
        load.toFile().delete();
    }

}
