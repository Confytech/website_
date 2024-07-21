package com.example.demo.dto.response;


import com.example.demo.enums.Role;
import lombok.Data;

@Data
public class UserDetailResponse {
    private String fullName;
    private String email;
    private Role role;
}
