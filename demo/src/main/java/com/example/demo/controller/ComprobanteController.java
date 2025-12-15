package com.example.demo.controller;

import com.example.demo.entities.Comprobante;
import com.example.demo.repository.ComprobanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3002")
@RestController
@RequestMapping("/api/v1")
public class ComprobanteController {

    @Autowired
    private ComprobanteRepository comprobanteRepository;

    // LISTAR TODOS LOS COMPROBANTES
    @GetMapping("/comprobante")
    public List<Comprobante> listarComprobantes() {
        return comprobanteRepository.findAll();
    }

    // CREAR COMPROBANTE
    @PostMapping("/comprobante")
    public Comprobante guardarComprobante(@RequestBody Comprobante comprobante) {
        return comprobanteRepository.save(comprobante);
    }

    // BUSCAR COMPROBANTE POR ID
    @GetMapping("/comprobante/{id}")
    public ResponseEntity<Comprobante> listarComprobantePorId(@PathVariable Integer id) {
        Comprobante comprobante = comprobanteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comprobante no encontrado: " + id));
        return ResponseEntity.ok(comprobante);
    }

    // ACTUALIZAR COMPROBANTE
    @PutMapping("/comprobante/{id}")
    public ResponseEntity<Comprobante> actualizarComprobante(
            @PathVariable Integer id,
            @RequestBody Comprobante datosNuevos
    ) {
        Comprobante comprobante = comprobanteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comprobante no encontrado: " + id));

        comprobante.setNombre(datosNuevos.getNombre());
        comprobante.setFecha(datosNuevos.getFecha());
        comprobante.setHora(datosNuevos.getHora());
        comprobante.setVenta(datosNuevos.getVenta());
        comprobante.setTipoPago(datosNuevos.getTipoPago());
        comprobante.setTotal(datosNuevos.getTotal());

        Comprobante comprobanteActualizado = comprobanteRepository.save(comprobante);
        return ResponseEntity.ok(comprobanteActualizado);
    }

    // ELIMINAR COMPROBANTE
    @DeleteMapping("/comprobante/{id}")
    public ResponseEntity<Void> eliminarComprobante(@PathVariable Integer id) {
        Comprobante comprobante = comprobanteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comprobante no encontrado: " + id));

        comprobanteRepository.delete(comprobante);

        return ResponseEntity.noContent().build(); // 204 sin contenido
    }
}
