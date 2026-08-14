package com.example.prestamos.model;

import java.time.LocalDate;

public class Prestamo {

    private String id;
    private double monto;
    private String clienteId;
    private LocalDate fecha;
    private EstadoPrestamo estado;

    public Prestamo() {
    }

    public Prestamo(
            String id,
            double monto,
            String clienteId,
            LocalDate fecha,
            EstadoPrestamo estado) {

        this.id = id;
        this.monto = monto;
        this.clienteId = clienteId;
        this.fecha = fecha;
        this.estado = estado;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String getClienteId() {
        return clienteId;
    }

    public void setClienteId(String clienteId) {
        this.clienteId = clienteId;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public EstadoPrestamo getEstado() {
        return estado;
    }

    public void setEstado(EstadoPrestamo estado) {
        this.estado = estado;
    }
}
