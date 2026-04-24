/*
 * @(#)Alquiler.java 1.0 23/04/2026
 *
 * Copyright(C) Juan David Díaz Pérez
 *
 */
package co.edu.udistrital.model.logic;

import java.time.LocalDate;

/**
 * Entidad que representa la transacción de renta de un producto. Sincronizada
 * exactamente con el esquema de MariaDB.
 *
 * @author Juan David Díaz Pérez
 * @version 1.0
 */
public class Alquiler {

    private int id;
    private String idCliente;
    private int idProducto; // Mapea a INT en la BD
    private LocalDate fechaAlquiler;
    private String estado; // ACTIVO, DEVUELTO

    /**
     * Constructor completo para recuperar del historial.
     */
    public Alquiler(int id, String idCliente, int idProducto, LocalDate fechaAlquiler, String estado) {
        this.id = id;
        this.idCliente = idCliente;
        this.idProducto = idProducto;
        this.fechaAlquiler = fechaAlquiler;
        this.estado = estado;
    }

    /**
     * Constructor para nuevos registros.
     */
    public Alquiler(String idCliente, int idProducto) {
        this.idCliente = idCliente;
        this.idProducto = idProducto;
        this.fechaAlquiler = LocalDate.now();
        this.estado = "ACTIVO";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public LocalDate getFechaAlquiler() {
        return fechaAlquiler;
    }

    public void setFechaAlquiler(LocalDate fechaAlquiler) {
        this.fechaAlquiler = fechaAlquiler;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
