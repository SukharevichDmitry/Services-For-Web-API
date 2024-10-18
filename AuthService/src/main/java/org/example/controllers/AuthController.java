package org.example.controllers;

import io.swagger.v3.oas.annotations.Operation;
import org.example.dtos.AuthResponseDTO;
import org.example.dtos.UserDTO;
import org.example.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    @Operation(summary = "Залогиниться", description = "Авторизация по логину и паролю")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody UserDTO userDTO) {
        try {
            String token = authService.authenticate(userDTO);
            return ResponseEntity.ok(new AuthResponseDTO(token));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new AuthResponseDTO(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new AuthResponseDTO("Internal Server Error"));
        }
    }


    @PostMapping("/register")
    @Operation(summary = "Зарегистрироваться", description = "регистрация по имени и паролю")
    public ResponseEntity<Void> register(@RequestBody UserDTO userDTO) {
        authService.register(userDTO);
        return ResponseEntity.ok().build();
    }

}
