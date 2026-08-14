package com.example.prestamos.controller;

import com.example.prestamos.model.EstadoPrestamo;
import com.example.prestamos.model.Prestamo;
import com.example.prestamos.model.TipoCliente;
import com.example.prestamos.service.PrestamoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prestamos")
public class PrestamoController {

    private final PrestamoService prestamoService;

    public PrestamoController(
            PrestamoService prestamoService) {

        this.prestamoService = prestamoService;
    }

    @PostMapping
    public ResponseEntity<Prestamo> crearPrestamo(
            @RequestBody Prestamo prestamo) {

        return ResponseEntity.ok(
                prestamoService.crearPrestamo(prestamo)
        );
    }

    @GetMapping
    public ResponseEntity<List<Prestamo>> obtenerPrestamosActivos() {

        return ResponseEntity.ok(
                prestamoService.obtenerPrestamosActivos()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Prestamo> obtenerPorId(
            @PathVariable String id) {

        return ResponseEntity.ok(
                prestamoService.obtenerPorId(id)
        );
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<Prestamo> actualizarEstado(
            @PathVariable String id,
            @RequestParam EstadoPrestamo estado) {

        return ResponseEntity.ok(
                prestamoService.actualizarEstado(
                        id,
                        estado
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPrestamo(
            @PathVariable String id) {

        prestamoService.eliminarPrestamo(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/interes")
    public ResponseEntity<Double> calcularInteres(
            @PathVariable String id,
            @RequestParam TipoCliente tipoCliente) {

        return ResponseEntity.ok(
                prestamoService.calcularMontoConInteres(
                        id,
                        tipoCliente
                )
        );
    }
}
