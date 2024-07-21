package com.example.demo.controller;


import com.example.demo.enums.GalleryType;
import com.example.demo.model.Photo;
import com.example.demo.service.photoService.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/photos")
public class PhotoController {

    @Autowired
    private PhotoService photoService;

    @PostMapping("/upload")
    public ResponseEntity<Photo> uploadPhoto(@RequestParam("title") String title,
                                             @RequestParam("description") String description,
                                             @RequestParam("galleryType") GalleryType galleryType,
                                             @RequestParam("file") MultipartFile file) {
        Photo uploadedPhoto = photoService.uploadPhoto(title, description, galleryType, file);
        return ResponseEntity.status(201).body(uploadedPhoto);
    }

    @GetMapping("/gallery/{galleryId}")
    public ResponseEntity<Page<Photo>> getPhotosByGalleryId(@PathVariable String galleryId,
                                                            @RequestParam(defaultValue = "0") int page,
                                                            @RequestParam(defaultValue = "10") int size) {
        Page<Photo> photos = photoService.getPhotosByGalleryId(galleryId, page, size);
        return ResponseEntity.ok(photos);
    }
}