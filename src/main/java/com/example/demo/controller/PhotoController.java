package com.example.demo.controller;


import com.example.demo.enums.GalleryType;
import com.example.demo.model.Photo;
import com.example.demo.service.photoService.IPhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/photos")
public class PhotoController {

    @Autowired
    private IPhotoService iPhotoService;

    @PostMapping("/upload")
    public ResponseEntity<Photo> uploadPhoto(@RequestParam("title") String title,
                                             @RequestParam("description") String description,
                                             @RequestParam("galleryType") GalleryType galleryType,
                                             @RequestParam("file") MultipartFile file) {
        Photo uploadedPhoto = iPhotoService.uploadPhoto(title, description, galleryType, file);
        return ResponseEntity.status(201).body(uploadedPhoto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePhotoById(@PathVariable String id) {
        iPhotoService.deletePhotoById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Photo> getPhotoById(@PathVariable String id) {
        Photo photo = iPhotoService.getPhotoById(id);
        return ResponseEntity.ok(photo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Photo> updatePhotoById(@PathVariable String id,
                                                 @RequestParam(required = false) String title,
                                                 @RequestParam(required = false) String description,
                                                 @RequestParam(required = false) GalleryType galleryType,
                                                 @RequestParam(required = false) MultipartFile file) {
        Photo updatedPhoto = iPhotoService.updatePhotoById(id, title, description, galleryType, file);
        return ResponseEntity.ok(updatedPhoto);
    }


}