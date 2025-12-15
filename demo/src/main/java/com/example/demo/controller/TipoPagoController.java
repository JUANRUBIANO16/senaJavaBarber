package com.example.demo.controller;

import com.example.demo.entities.TipoPago;
import com.example.demo.repository.TipoPagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3002")
@RestController
@RequestMapping("/api/v1")
public class TipoPagoController {

    @Autowired
    private TipoPagoRepository tipoPagoRepository;

    // LISTAR TODOS LOS TIPOS DE PAGO
    @GetMapping("/tipo-pago")
    public List<TipoPago> listarTiposPago() {
        return tipoPagoRepository.findAll();
    }

    // CREAR TIPO DE PAGO
    @PostMapping("/tipo-pago")
    public TipoPago guardarTipoPago(@RequestBody TipoPago tipoPago) {
        return tipoPagoRepository.save(tipoPago);
    }

    // BUSCAR TIPO DE PAGO POR ID
    @GetMapping("/tipo-pago/{id}")
    public ResponseEntity<TipoPago> listarTipoPagoPorId(@PathVariable Integer id) {
        TipoPago tipoPago = tipoPagoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tipo de pago no encontrado: " + id));
        return ResponseEntity.ok(tipoPago);
    }

    // ACTUALIZAR TIPO DE PAGO
    @PutMapping("/tipo-pago/{id}")
    public ResponseEntity<TipoPago> actualizarTipoPago(
            @PathVariable Integer id,
            @RequestBody TipoPago datosNuevos
    ) {
        TipoPago tipoPago = tipoPagoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tipo de pago no encontrado: " + id));

        tipoPago.setMetodo(datosNuevos.getMetodo());

        TipoPago tipoPagoActualizado = tipoPagoRepository.save(tipoPago);
        return ResponseEntity.ok(tipoPagoActualizado);
    }

    // ELIMINAR TIPO DE PAGO (CON MANEJO DE ERROR)
    @DeleteMapping("/tipo-pago/{id}")
    public ResponseEntity<?> eliminarTipoPago(@PathVariable Integer id) {

        TipoPago tipoPago = tipoPagoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tipo de pago no encontrado: " + id));

        try {
            tipoPagoRepository.delete(tipoPago);
            return ResponseEntity.ok("Eliminado correctamente");

        } catch (DataIntegrityViolationException e) {
            return ResponseEntity
                    .status(409) // CONFLICT
                    .body("No se puede eliminar: este tipo de pago está siendo usado en otra tabla.");
        }
    }
}
