package com.example.demo.controller;

import com.example.demo.entities.Servicios;
import com.example.demo.repository.ServiciosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3002")
@RestController
@RequestMapping("/api/v1")
public class ServiciosController {

    @Autowired
    private ServiciosRepository serviciosRepository;

    // LISTAR TODOS LOS SERVICIOS
    @GetMapping("/servicios")
    public List<Servicios> listarServicios() {
        return serviciosRepository.findAll();
    }

    // CREAR SERVICIO
    @PostMapping("/servicios")
    public Servicios guardarServicio(@RequestBody Servicios servicio) {
        return serviciosRepository.save(servicio);
    }

    // BUSCAR SERVICIO POR ID
    @GetMapping("/servicios/{id}")
    public ResponseEntity<Servicios> listarServicioPorId(@PathVariable Integer id) {
        Servicios servicio = serviciosRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Servicio no encontrado: " + id));
        return ResponseEntity.ok(servicio);
    }

    // ACTUALIZAR SERVICIO
    @PutMapping("/servicios/{id}")
    public ResponseEntity<Servicios> actualizarServicio(
            @PathVariable Integer id,
            @RequestBody Servicios datosNuevos
    ) {
        Servicios servicio = serviciosRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Servicio no encontrado: " + id));

        servicio.setNombre(datosNuevos.getNombre());
        servicio.setDescripcion(datosNuevos.getDescripcion());
        servicio.setPrecio(datosNuevos.getPrecio());
        servicio.setImagen(datosNuevos.getImagen());

        Servicios servicioActualizado = serviciosRepository.save(servicio);
        return ResponseEntity.ok(servicioActualizado);
    }

    // ELIMINAR SERVICIO
    @DeleteMapping("/servicios/{id}")
    public ResponseEntity<Void> eliminarServicio(@PathVariable Integer id) {
        Servicios servicio = serviciosRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Servicio no encontrado: " + id));

        serviciosRepository.delete(servicio);

        return ResponseEntity.noContent().build(); // 204 sin contenido
    }
}
