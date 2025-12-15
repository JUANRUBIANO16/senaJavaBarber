package com.example.demo.controller;


import com.example.demo.entities.Usuarios;
import com.example.demo.repository.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3002/")
@RestController
@RequestMapping("/api/v1")
public class UsuariosController {

    @Autowired
    private UsuariosRepository usuariosRepository;

    // LISTAR TODOS LOS USUARIOS
    @GetMapping("/usuarios")
    public List<Usuarios> listarUsuarios() {
        return usuariosRepository.findAll();
    }

    // CREAR USUARIO
    @PostMapping("/usuarios")
    public Usuarios guardarUsuario(@RequestBody Usuarios usuario) {
        return usuariosRepository.save(usuario);
    }

    // BUSCAR USUARIO POR ID
    @GetMapping("/usuarios/{id}")
    public ResponseEntity<Usuarios> listarUsuarioPorId(@PathVariable Integer id) {
        Usuarios usuario = usuariosRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + id));
        return ResponseEntity.ok(usuario);
    }

    // ACTUALIZAR USUARIO
    @PutMapping("/usuarios/{id}")
    public ResponseEntity<Usuarios> actualizarUsuario(
            @PathVariable Integer id,
            @RequestBody Usuarios datosNuevos
    ) {
        Usuarios usuario = usuariosRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + id));

        usuario.setNombre(datosNuevos.getNombre());
        usuario.setApellido(datosNuevos.getApellido());
        usuario.setEmail(datosNuevos.getEmail());
        usuario.setTelefono(datosNuevos.getTelefono());
        usuario.setDireccion(datosNuevos.getDireccion());
        usuario.setPassword(datosNuevos.getPassword());
        usuario.setTipoUsuario(datosNuevos.getTipoUsuario());
        usuario.setFechaRegistro(datosNuevos.getFechaRegistro());

        Usuarios usuarioActualizado = usuariosRepository.save(usuario);
        return ResponseEntity.ok(usuarioActualizado);
    }

    // ELIMINAR USUARIO
    @DeleteMapping("/usuarios/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Integer id) {
        Usuarios usuario = usuariosRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + id));

        usuariosRepository.delete(usuario);

        return ResponseEntity.noContent().build(); // 204 sin contenido
    }
}
