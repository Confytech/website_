package com.example.demo.controller;


import com.example.demo.dto.request.UserLoginRequest;
import com.example.demo.dto.response.GenericResponse;
import com.example.demo.dto.response.LoginResponse;
import com.example.demo.dto.response.UserDetailResponse;
import com.example.demo.exceptions.CommonApplicationException;
import com.example.demo.model.Photo;
import com.example.demo.service.UserService.IUserService;
import com.example.demo.service.photoService.IPhotoService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/auth")
public class AuthController {

    private final IUserService userService;
    private final IPhotoService iPhotoService;

    @PostMapping("/login")
    public ResponseEntity<GenericResponse<LoginResponse>> loginUser(@RequestBody @Valid UserLoginRequest request) {
        log.info("Request to login user");
        GenericResponse<LoginResponse> response = userService.login(request);
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    @GetMapping("/userdetails")
    public ResponseEntity<GenericResponse<UserDetailResponse>> getUserDetails(@RequestHeader("Authorization") String authorizationHeader) throws CommonApplicationException {
        log.info("Received request with Authorization Header: {}", authorizationHeader);
        UserDetailResponse detailResponse = userService.getUserDetails(authorizationHeader);
        GenericResponse<UserDetailResponse> genericResponse = new GenericResponse<>("00", "User detail retrieved successfully", detailResponse, "success");
        return new ResponseEntity<>(genericResponse, HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<GenericResponse<String>> logout(HttpServletRequest request, @RequestHeader("Authorization") String authorizationHeader) throws CommonApplicationException {
        log.info("Received request with Authorization Header: {}", authorizationHeader);
        GenericResponse<String> response = userService.logout(request, authorizationHeader);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @GetMapping("/gallery/{galleryType}")
    public ResponseEntity<Page<Photo>> getPhotosByGalleryType(@PathVariable String galleryType,
                                                            @RequestParam(defaultValue = "0") int page,
                                                            @RequestParam(defaultValue = "10") int size) {
        Page<Photo> photos = iPhotoService.getPhotosByGalleryType(galleryType, page, size);
        return ResponseEntity.ok(photos);
    }

    @GetMapping("/")
    public List<Photo> getAllPhoto(){
        return iPhotoService.getAllPhotos();
    }
}
