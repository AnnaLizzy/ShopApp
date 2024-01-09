package com.example.shopapp.controller;


import com.example.shopapp.dtos.UserDTO;
import com.example.shopapp.dtos.UserLoginDTO;
import com.example.shopapp.responses.LoginResponse;
import com.example.shopapp.responses.RegisterResponse;
import com.example.shopapp.services.IUserServices;
import com.example.shopapp.components.LocalizationUtils;
import com.example.shopapp.utils.Messagekey;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/user")
@RequiredArgsConstructor
public class UserController {
    private final IUserServices userServices;
    private final LocalizationUtils localizationUtils;
    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> createUser(@Valid @RequestBody UserDTO userDTO,
                                                       BindingResult result) {
        try {
            if (result.hasErrors()) {
                List<String> errorMessage = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(
                        RegisterResponse.builder()
                                .message(localizationUtils.getLocalizedMessage(Messagekey.REGISTER_ERROR,errorMessage))
                                .build()
                );
            }
            if (!userDTO.getPassword().equals(userDTO.getRetypePassword())) {
                return ResponseEntity.badRequest().body(
                        RegisterResponse.builder()
                                .message(localizationUtils.getLocalizedMessage(Messagekey.PASSWORD_NOT_MATCH))
                                .build()
                );
            }
            userServices.createUser(userDTO);
            return ResponseEntity.ok(RegisterResponse.builder()
                            .message(
                                    localizationUtils.getLocalizedMessage(Messagekey.REGISTER_SUCCESS)
                            )
                    .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    RegisterResponse.builder()
                            .message(localizationUtils.getLocalizedMessage(Messagekey.REGISTER_FAILED,e.getMessage()))
                            .build()
            );
        }

    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody UserLoginDTO userLoginDTO)

    {
        try {
            String token = userServices.login(
                    userLoginDTO.getPhoneNumber(),
                    userLoginDTO.getPassword(),
                    userLoginDTO.getRoleId());
            return ResponseEntity.ok(LoginResponse.builder()
                            .message( localizationUtils.getLocalizedMessage(Messagekey.LOGIN_SUCCESS))
                            .token(token)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    LoginResponse.builder()
                            .message(localizationUtils.getLocalizedMessage(Messagekey.LOGIN_FAILED,e.getMessage()))
                            .build()
            );
        }
    }
}
