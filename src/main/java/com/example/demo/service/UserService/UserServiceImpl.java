package com.example.demo.service.UserService;

import com.example.demo.dto.request.UserLoginRequest;
import com.example.demo.dto.response.GenericResponse;
import com.example.demo.dto.response.LoginResponse;
import com.example.demo.dto.response.UserDetailResponse;
import com.example.demo.enums.Role;
import com.example.demo.exceptions.CommonApplicationException;
import com.example.demo.exceptions.UserExistException;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.securities.AppUserDetailsService;
import com.example.demo.securities.JWTService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.AbstractMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Slf4j
@Service
public class UserServiceImpl implements IUserService {
    private final JWTService jwtService;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final AppUserDetailsService userDetailsService;


    @Override
    public GenericResponse<LoginResponse> login(UserLoginRequest loginDTO) {
        log.info("Request to login at the service layer");

        Authentication authenticationUser;
        try {
            authenticationUser = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword())
            );
            log.info("Authenticated the User by the Authentication manager");
        } catch (DisabledException es) {
            return Stream.of(
                            new AbstractMap.SimpleEntry<>("message", "Disabled exception occurred"),
                            new AbstractMap.SimpleEntry<>("status", "failure"),
                            new AbstractMap.SimpleEntry<>("httpStatus", HttpStatus.BAD_REQUEST)
                    )
                    .collect(
                            Collectors.collectingAndThen(
                                    Collectors.toMap(AbstractMap.SimpleEntry::getKey, entry -> entry.getValue()),
                                    map -> new GenericResponse((Map<String, String>) map)
                            )
                    );

        } catch (BadCredentialsException e) {
            throw new UserExistException("Invalid email or password", HttpStatus.BAD_REQUEST);
        }
        // Tell securityContext that this user is in the context
        SecurityContextHolder.getContext().setAuthentication(authenticationUser);
        // Retrieve the user from the repository
        User appUser = userRepository.findByEmail(loginDTO.getEmail()).orElseThrow(() ->
                new UserExistException("User not found", HttpStatus.BAD_REQUEST));
        // Update the lastLoginDate field
        appUser.setLastLogin(LocalDateTime.now());
        log.info("last-login date updated");
        // Save the updated user entity
        User user = userRepository.save(appUser);
        log.info("user saved back to database");
        // Generate and send token
        String tokenGenerated = "Bearer " + jwtService.generateToken(authenticationUser);
        log.info("Jwt token generated for the user " + tokenGenerated);
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(tokenGenerated);
        GenericResponse<LoginResponse> apiResponse = new GenericResponse<>("00", "Success", loginResponse, "Successfully logged in", HttpStatus.OK);

        apiResponse.setData(loginResponse);

        return apiResponse;
    }
    @Override
    public UserDetailResponse getUserDetails(String authorizationHeader) throws CommonApplicationException {
        String token = authorizationHeader.substring(7);
        Map<String, String> userDetails = jwtService.validateTokenAndReturnDetail(token);

        String userEmail = userDetails.get("email");
        String userName = userDetails.get("name");
        Role userRole = Role.valueOf(userDetails.get("role"));

        UserDetailResponse detailResponse = new UserDetailResponse();
        detailResponse.setFullName(userName);
        detailResponse.setEmail(userEmail);
        detailResponse.setRole(userRole);

        return detailResponse;
    }

    @Override
    public GenericResponse<String> logout(HttpServletRequest request, String authorizationHeader) throws CommonApplicationException {
        String token = authorizationHeader.substring(7);
        Map<String, String> userDetails = jwtService.validateTokenAndReturnDetail(token);
        log.info("Request to logout");
        try {
            SecurityContextHolder.getContext().setAuthentication(null);
            return new GenericResponse<>("00", "Success", "Successfully logged out", "You have been logged out", HttpStatus.OK);
        } catch (Exception e) {
            return new GenericResponse<>("01", "Failure", "Logout failed", "An error occurred while logging out", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
