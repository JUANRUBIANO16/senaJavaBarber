package com.example.demo.controller;

import com.example.demo.entities.Venta;
import com.example.demo.repository.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3002/")
@RestController
@RequestMapping("/api/v1")
public class VentaController {

    @Autowired
    private VentaRepository ventaRepository;

    // LISTAR TODAS LAS VENTAS
    @GetMapping("/venta")
    public List<Venta> listarVentas() {
        return ventaRepository.findAll();
    }

    // CREAR VENTA
    @PostMapping("/venta")
    public Venta guardarVenta(@RequestBody Venta venta) {
        return ventaRepository.save(venta);
    }

    // BUSCAR VENTA POR ID
    @GetMapping("/venta/{id}")
    public ResponseEntity<Venta> listarVentaPorId(@PathVariable Integer id) {
        Venta venta = ventaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada: " + id));
        return ResponseEntity.ok(venta);
    }

    // ACTUALIZAR VENTA
    @PutMapping("/venta/{id}")
    public ResponseEntity<Venta> actualizarVenta(
            @PathVariable Integer id,
            @RequestBody Venta datosNuevos
    ) {
        Venta venta = ventaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada: " + id));

        venta.setValor(datosNuevos.getValor());
        venta.setCantidad(datosNuevos.getCantidad());
        venta.setTotal(datosNuevos.getTotal());
        venta.setCita(datosNuevos.getCita());

        Venta ventaActualizada = ventaRepository.save(venta);
        return ResponseEntity.ok(ventaActualizada);
    }

    // ELIMINAR VENTA
    @DeleteMapping("/venta/{id}")
    public ResponseEntity<Void> eliminarVenta(@PathVariable Integer id) {
        Venta venta = ventaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada: " + id));

        ventaRepository.delete(venta);

        return ResponseEntity.noContent().build(); // 204 sin contenido
    }
}
