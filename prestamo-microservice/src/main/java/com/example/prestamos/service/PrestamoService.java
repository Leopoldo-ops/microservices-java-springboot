package com.example.prestamos.service;

import com.example.prestamos.exception.PrestamoNotFoundException;
import com.example.prestamos.model.EstadoPrestamo;
import com.example.prestamos.model.Prestamo;
import com.example.prestamos.model.TipoCliente;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PrestamoService {

    private final List<Prestamo> prestamos = new ArrayList<>();

    public Prestamo crearPrestamo(Prestamo prestamo) {

        prestamos.add(prestamo);

        return prestamo;
    }
    
    public List<Prestamo> obtenerPrestamosActivos() {

        return prestamos.stream()
                .filter(prestamo ->
                        prestamo.getEstado() ==
                        EstadoPrestamo.PENDIENTE
                )
                .toList();
    }
    
    public Prestamo obtenerPorId(String id) {

        return prestamos.stream()
                .filter(prestamo ->
                        prestamo.getId().equals(id)
                )
                .findFirst()
                .orElseThrow(() ->
                        new PrestamoNotFoundException(
                                "Préstamo no encontrado: " + id
                        )
                );
    }
    
    public Prestamo actualizarEstado(
            String id,
            EstadoPrestamo nuevoEstado) {

        Prestamo prestamo = obtenerPorId(id);

        prestamo.setEstado(nuevoEstado);

        return prestamo;
    }
    
    public void eliminarPrestamo(String id) {

        boolean eliminado = prestamos.removeIf(
                prestamo -> prestamo.getId().equals(id)
        );

        if (!eliminado) {

            throw new PrestamoNotFoundException(
                    "Préstamo no encontrado: " + id
            );
        }
    }
    
    public double calcularMontoConInteres(
            String id,
            TipoCliente tipoCliente) {

        Prestamo prestamo = obtenerPorId(id);

        double tasa = switch (tipoCliente) {

            case VIP -> 0.05;

            case REGULAR -> 0.10;
        };

        return prestamo.getMonto() * (1 + tasa);
    }
    
}