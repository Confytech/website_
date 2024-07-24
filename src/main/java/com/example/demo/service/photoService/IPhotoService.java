package com.example.demo.service.photoService;

import com.example.demo.enums.GalleryType;
import com.example.demo.model.Photo;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IPhotoService {

    Photo uploadPhoto(String title, String description, GalleryType galleryType, MultipartFile file);

    Photo getPhotoById(String id);

    Page<Photo> getPhotosByGalleryType(String galleryId, int page, int size);
    List<Photo> getAllPhotos();

    void deletePhotoById(String id);

    Photo updatePhotoById(String id, String title, String description, GalleryType galleryType, MultipartFile file);
}
