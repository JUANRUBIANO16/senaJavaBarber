package com.example.demo.controller;

import com.example.demo.entities.Cita;
import com.example.demo.repository.CitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins ="http://localhost:3002")
@RestController
@RequestMapping("/apis/v1")
public class CitaController {

    @Autowired
    private CitaRepository citaRepository;

    // LISTAR TODAS LAS CITAS
    @GetMapping("/cita")
    public List<Cita> listarCitas() {
        return citaRepository.findAll();
    }

    // CREAR CITA
    @PostMapping("/cita")
    public Cita guardarCita(@RequestBody Cita cita) {
        return citaRepository.save(cita);
    }

    // BUSCAR CITA POR ID
    @GetMapping("/cita/{id}") // ← AQUÍ estaba el error
    public ResponseEntity<Cita> listarCitaPorId(@PathVariable Integer id) {
        Cita cita = citaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada: " + id));
        return ResponseEntity.ok(cita);
    }

    // ACTUALIZAR CITA
    @PutMapping("/cita/{id}")
    public ResponseEntity<Cita> actualizarCita(
            @PathVariable Integer id,
            @RequestBody Cita datosNuevos
    ) {
        Cita cita = citaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada: " + id));

        cita.setFecha(datosNuevos.getFecha());
        cita.setHora(datosNuevos.getHora());
        cita.setObservacion(datosNuevos.getObservacion());
        cita.setUsuario(datosNuevos.getUsuario());
        cita.setServicio(datosNuevos.getServicio());

        Cita citaActualizada = citaRepository.save(cita);
        return ResponseEntity.ok(citaActualizada);
    }

    // ELIMINAR CITA
    @DeleteMapping("/cita/{id}")
    public ResponseEntity<Void> eliminarCita(@PathVariable Integer id) {
        Cita cita = citaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada: " + id));

        citaRepository.delete(cita);

        return ResponseEntity.noContent().build(); // 204 sin contenido
    }
}
