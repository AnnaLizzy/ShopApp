package com.example.shopapp.controller;

import com.example.shopapp.models.Role;
import com.example.shopapp.services.RoleServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/roles")
@RequiredArgsConstructor
public class RoleController {
    private final RoleServices roleServices;
    @GetMapping("")
    public ResponseEntity<?> getAllRoles(){
        List<Role> roles = roleServices.getAllRoles();
        return ResponseEntity.ok(roles);
    }
}
