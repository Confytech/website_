package com.example.demo.service.photoService;

import com.example.demo.enums.GalleryType;
import com.example.demo.model.Photo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface PhotoService {

    Photo uploadPhoto(String title, String description, GalleryType galleryType, MultipartFile file);

    Page<Photo> getPhotosByGalleryId(String galleryId, int page, int size);
}
