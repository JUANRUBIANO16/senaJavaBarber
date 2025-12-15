package com.example.demo.controller;

import com.example.demo.entities.Usuarios;
import com.example.demo.repository.AuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:3002")
public class AuthController {

    @Autowired
    private AuthRepository authRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuarios loginData) {

        // Buscar usuario por email
        Usuarios usuario = authRepository.findByEmail(loginData.getEmail());

        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Usuario no encontrado"));
        }

        // Validación de contraseña
        if (!usuario.getPassword().equals(loginData.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Contraseña incorrecta"));
        }

        // Login correcto — NO enviamos la contraseña
        return ResponseEntity.ok(Map.of(
                "id", usuario.getId(),
                "email", usuario.getEmail(),
                "nombre", usuario.getNombre()
        ));
    }
}
