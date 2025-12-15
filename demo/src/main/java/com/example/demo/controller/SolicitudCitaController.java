package com.example.demo.controller;
import com.example.demo.entities.SolicitudCita;
import com.example.demo.repository.SolicitudCitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3002")
@RestController
@RequestMapping("/api/v1")
public class SolicitudCitaController {

    @Autowired
    private SolicitudCitaRepository solicitudCitaRepository;

    // Obtener todas las solicitudes
    @GetMapping("/solicitud")
    public List<SolicitudCita> getAllSolicitudes() {
        return solicitudCitaRepository.findAll();
    }

    // Obtener una solicitud por ID
    @GetMapping("/{id}")
    public ResponseEntity<SolicitudCita> getSolicitudById(@PathVariable Integer id) {
        Optional<SolicitudCita> solicitud = solicitudCitaRepository.findById(id);
        return solicitud.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/solicitud")
    public SolicitudCita createSolicitud(@RequestBody SolicitudCita solicitudCita) {
        return solicitudCitaRepository.save(solicitudCita);
    }


    // Actualizar una solicitud existente
    @PutMapping("/{id}")
    public ResponseEntity<SolicitudCita> updateSolicitud(@PathVariable Integer id,
                                                         @RequestBody SolicitudCita solicitudDetails) {
        Optional<SolicitudCita> optionalSolicitud = solicitudCitaRepository.findById(id);
        if (!optionalSolicitud.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        SolicitudCita solicitud = optionalSolicitud.get();
        solicitud.setNombre(solicitudDetails.getNombre());
        solicitud.setTelefono(solicitudDetails.getTelefono());
        solicitud.setEmail(solicitudDetails.getEmail());
        solicitud.setMensaje(solicitudDetails.getMensaje());
        // No modificamos fechaRegistro aquí

        SolicitudCita updatedSolicitud = solicitudCitaRepository.save(solicitud);
        return ResponseEntity.ok(updatedSolicitud);
    }

    // Eliminar una solicitud
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSolicitud(@PathVariable Integer id) {
        if (!solicitudCitaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        solicitudCitaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}