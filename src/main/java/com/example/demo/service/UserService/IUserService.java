package com.example.demo.service.UserService;


import com.example.demo.dto.request.UserLoginRequest;
import com.example.demo.dto.response.GenericResponse;
import com.example.demo.dto.response.LoginResponse;
import com.example.demo.dto.response.UserDetailResponse;
import com.example.demo.exceptions.CommonApplicationException;
import jakarta.servlet.http.HttpServletRequest;

public interface IUserService {
    GenericResponse<LoginResponse> login(UserLoginRequest loginDTO);
    UserDetailResponse getUserDetails(String authorizationHeader) throws CommonApplicationException;

    GenericResponse<String> logout(HttpServletRequest request, String authorizationHeader) throws CommonApplicationException;
}
