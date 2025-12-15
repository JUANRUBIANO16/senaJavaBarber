package com.example.demo.config;

import com.example.demo.entities.Usuarios;
import com.example.demo.repository.AuthRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initUser(AuthRepository authRepository) {
        return args -> {

            String emailDefault = "admin@admin.com";

            if (authRepository.findByEmail(emailDefault) == null) {

                Usuarios admin = new Usuarios();
                admin.setNombre("Administrador");
                admin.setApellido("Admin");
                admin.setEmail(emailDefault);

                // ⚠️ CAMPOS OBLIGATORIOS
                admin.setTelefono("3000000000");     // UNIQUE
                admin.setDireccion("Sistema");
                admin.setPassword("1234");
                admin.setTipoUsuario(Usuarios.TipoUsuario.admin);

                // opcional pero recomendable
                admin.setFechaRegistro(LocalDateTime.now());

                authRepository.save(admin);

                System.out.println("✅ Usuario admin creado por defecto");
            }
        };
    }
}
