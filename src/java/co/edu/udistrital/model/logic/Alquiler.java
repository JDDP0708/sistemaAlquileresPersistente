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
    private int idProducto; 
    private LocalDate fechaAlquiler;
    private double costo;
    private String estado; 

    /**
     * Constructor completo para recuperar del historial.
     */
    public Alquiler(int id, String idCliente, int idProducto, LocalDate fechaAlquiler, double costo, String estado) {
        this.id = id;
        this.idCliente = idCliente;
        this.idProducto = idProducto;
        this.fechaAlquiler = fechaAlquiler;
        this.costo = costo;
        this.estado = estado;
    }

    /**
     * Constructor para nuevos registros.
     * 
     * @param idCliente Identificador del cliente que realiza el alquiler.
     * @param idProducto Identificador del producto a alquilar.
     * @param costo Costo total del alquiler.
     */
    public Alquiler(String idCliente, int idProducto, double costo) {
        this.idCliente = idCliente;
        this.idProducto = idProducto;
        this.fechaAlquiler = LocalDate.now();
        this.costo = costo;
        this.estado = "ACTIVO";
    }

    /**
     * Obtiene el identificador único del alquiler.
     *
     * @return ID del alquiler en base de datos.
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el identificador del alquiler.
     *
     * @param id Nuevo identificador del alquiler.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el identificador del cliente asociado al alquiler.
     *
     * @return ID del cliente.
     */
    public String getIdCliente() {
        return idCliente;
    }

    /**
     * Establece el identificador del cliente.
     *
     * @param idCliente Nuevo ID del cliente.
     */
    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    /**
     * Obtiene el identificador del producto alquilado.
     *
     * @return ID del producto.
     */
    public int getIdProducto() {
        return idProducto;
    }

    /**
     * Establece el identificador del producto.
     *
     * @param idProducto Nuevo ID del producto.
     */
    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    /**
     * Obtiene la fecha en que se realizó el alquiler.
     *
     * @return Fecha del alquiler.
     */
    public LocalDate getFechaAlquiler() {
        return fechaAlquiler;
    }

    /**
     * Establece la fecha del alquiler.
     *
     * @param fechaAlquiler Nueva fecha del alquiler.
     */
    public void setFechaAlquiler(LocalDate fechaAlquiler) {
        this.fechaAlquiler = fechaAlquiler;
    }

    /**
     * Obtiene el costo total del alquiler.
     *
     * @return Costo en unidades monetarias.
     */
    public double getCosto() {
        return costo;
    }

    /**
     * Establece el costo del alquiler.
     *
     * @param costo Nuevo costo del alquiler.
     */
    public void setCosto(double costo) {
        this.costo = costo;
    }

    /**
     * Obtiene el estado actual del alquiler.
     *
     * @return Estado del alquiler (ACTIVO, DEVUELTO, etc).
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Establece el estado del alquiler.
     *
     * @param estado Nuevo estado del alquiler.
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }
}
