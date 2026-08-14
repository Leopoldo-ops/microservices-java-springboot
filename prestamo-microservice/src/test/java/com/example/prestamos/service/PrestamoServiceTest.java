package com.example.prestamos.service;

import com.example.prestamos.exception.PrestamoNotFoundException;
import com.example.prestamos.model.EstadoPrestamo;
import com.example.prestamos.model.Prestamo;
import com.example.prestamos.model.TipoCliente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PrestamoServiceTest {

	private PrestamoService prestamoService;

	//el beforeeach nos sirve para cada prueba tenga una lista completamente nueva y vacía
    @BeforeEach
    void setUp() {
        prestamoService = new PrestamoService();
    }
    
    @Test
    void deberiaCrearPrestamo() {

        Prestamo prestamo = new Prestamo(
                "P001",
                10000.0,
                "C001",
                LocalDate.of(2026, 8, 13),
                EstadoPrestamo.PENDIENTE
        );

        Prestamo resultado =
                prestamoService.crearPrestamo(prestamo);

        assertThat(resultado).isEqualTo(prestamo);
    }
    
    @Test
    void deberiaObtenerSoloPrestamosActivos() {

        Prestamo pendiente = new Prestamo(
                "P001",
                10000.0,
                "C001",
                LocalDate.of(2026, 8, 13),
                EstadoPrestamo.PENDIENTE
        );

        Prestamo pagado = new Prestamo(
                "P002",
                5000.0,
                "C002",
                LocalDate.of(2026, 8, 13),
                EstadoPrestamo.PAGADO
        );

        prestamoService.crearPrestamo(pendiente);
        prestamoService.crearPrestamo(pagado);

        List<Prestamo> resultado =
                prestamoService.obtenerPrestamosActivos();

        assertThat(resultado)
                .hasSize(1)
                .containsExactly(pendiente);
    }
    
    @Test
    void deberiaObtenerPrestamoPorId() {

        Prestamo prestamo = new Prestamo(
                "P001",
                10000.0,
                "C001",
                LocalDate.of(2026, 8, 13),
                EstadoPrestamo.PENDIENTE
        );

        prestamoService.crearPrestamo(prestamo);

        Prestamo resultado =
                prestamoService.obtenerPorId("P001");

        assertThat(resultado)
                .isSameAs(prestamo);
    }
    
    @Test
    void deberiaLanzarExcepcionCuandoPrestamoNoExiste() {

        assertThatThrownBy(() ->
                prestamoService.obtenerPorId("P999")
        )
        .isInstanceOf(PrestamoNotFoundException.class)
        .hasMessage("Préstamo no encontrado: P999");
    }
    
    @Test
    void deberiaActualizarEstadoDelPrestamo() {

        Prestamo prestamo = new Prestamo(
                "P001",
                10000.0,
                "C001",
                LocalDate.of(2026, 8, 13),
                EstadoPrestamo.PENDIENTE
        );

        prestamoService.crearPrestamo(prestamo);

        Prestamo resultado =
                prestamoService.actualizarEstado(
                        "P001",
                        EstadoPrestamo.PAGADO
                );

        assertThat(resultado.getEstado())
                .isEqualTo(EstadoPrestamo.PAGADO);
    }
    
    @Test
    void prestamoPagadoNoDebeAparecerComoActivo() {

        Prestamo prestamo = new Prestamo(
                "P001",
                10000.0,
                "C001",
                LocalDate.of(2026, 8, 13),
                EstadoPrestamo.PENDIENTE
        );

        prestamoService.crearPrestamo(prestamo);

        prestamoService.actualizarEstado(
                "P001",
                EstadoPrestamo.PAGADO
        );

        List<Prestamo> activos =
                prestamoService.obtenerPrestamosActivos();

        assertThat(activos)
                .isEmpty();
    }
    
    @Test
    void deberiaLanzarExcepcionAlActualizarPrestamoInexistente() {

        assertThatThrownBy(() ->
                prestamoService.actualizarEstado(
                        "P999",
                        EstadoPrestamo.PAGADO
                )
        )
        .isInstanceOf(PrestamoNotFoundException.class)
        .hasMessage("Préstamo no encontrado: P999");
    }
    
    @Test
    void deberiaEliminarPrestamo() {

        Prestamo prestamo = new Prestamo(
                "P001",
                10000.0,
                "C001",
                LocalDate.of(2026, 8, 13),
                EstadoPrestamo.PENDIENTE
        );

        prestamoService.crearPrestamo(prestamo);

        prestamoService.eliminarPrestamo("P001");

        assertThat(prestamoService.obtenerPrestamosActivos())
                .isEmpty();
    }
    
    @Test
    void deberiaLanzarExcepcionAlEliminarPrestamoInexistente() {

        assertThatThrownBy(() ->
                prestamoService.eliminarPrestamo("P999")
        )
        .isInstanceOf(PrestamoNotFoundException.class)
        .hasMessage("Préstamo no encontrado: P999");
    }
    
    @Test
    void deberiaCalcularInteresParaClienteVip() {

        Prestamo prestamo = new Prestamo(
                "P001",
                10000.0,
                "C001",
                LocalDate.of(2026, 8, 13),
                EstadoPrestamo.PENDIENTE
        );

        prestamoService.crearPrestamo(prestamo);

        double resultado =
                prestamoService.calcularMontoConInteres(
                        "P001",
                        TipoCliente.VIP
                );

        assertThat(resultado)
                .isEqualTo(10500.0);
    }
    
    @Test
    void deberiaCalcularInteresParaClienteRegular() {

        Prestamo prestamo = new Prestamo(
                "P001",
                10000.0,
                "C001",
                LocalDate.of(2026, 8, 13),
                EstadoPrestamo.PENDIENTE
        );

        prestamoService.crearPrestamo(prestamo);

        double resultado =
                prestamoService.calcularMontoConInteres(
                        "P001",
                        TipoCliente.REGULAR
                );

        assertThat(resultado)
                .isEqualTo(11000.0);
    }
    
}
