package com.example.demo.service.photoService;


import com.example.demo.enums.GalleryType;
import com.example.demo.model.Photo;
import com.example.demo.repository.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class PhotoServiceImpl implements PhotoService {

    @Autowired
    private PhotoRepository photoRepository;

    @Value("${upload.dir}")
    private String uploadDir;

    public List<Photo> getAllPhotos() {
        return photoRepository.findAll();
    }

    @Override
    public Photo uploadPhoto(String title, String description, GalleryType galleryType, MultipartFile file) {
        try {
            String fileId = UUID.randomUUID().toString();
            String filePath = uploadDir + "/" + fileId + "_" + file.getOriginalFilename();
            saveFile(file, filePath);

            Photo photo = new Photo();
            photo.setTitle(title);
            photo.setDescription(description);
            photo.setImageUrl(filePath);
            photo.setUploadedAt(LocalDateTime.now());
            photo.setUploadedBy("Admin");
            photo.setGalleryType(galleryType);
            photoRepository.save(photo);
            return photo;
        } catch (Exception e){
            throw new RuntimeException("Failed to upload file", e);
        }
    }

    private void saveFile(MultipartFile file, String filePath) throws IOException {
        Path path = Paths.get(filePath);
        Files.createDirectories(path.getParent());
        Files.write(path, file.getBytes());
    }

    @Override
    public Page<Photo> getPhotosByGalleryId(String galleryId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return photoRepository.findByGalleryType(galleryId, pageable);
    }
}
