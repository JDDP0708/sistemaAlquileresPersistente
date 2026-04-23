/*
 * @(#)Alquiler.java 1.0 21/04/2026
 *
 * Copyright(C) Juan David Díaz Pérez
 *
 */
package co.edu.udistrital.model.logic;

import java.time.LocalDate;

/**
 * Entidad que representa la transacción de renta de un producto.
 *
 * @author Juan David Díaz Pérez
 * @version 1.0
 */
public class Alquiler {
    private int id;
    private String idCliente;
    private String idProducto;
    private LocalDate fechaAlquiler;
    private LocalDate fechaPactada;
    private double costoTotal;
    private String estado; // ACTIVO, DEVUELTO, RETRASADO

    /**
     * Constructor completo para historial.
     */
    public Alquiler(int id, String idCliente, String idProducto, LocalDate fechaAlquiler, LocalDate fechaPactada, double costoTotal, String estado) {
        this.id = id;
        this.idCliente = idCliente;
        this.idProducto = idProducto;
        this.fechaAlquiler = fechaAlquiler;
        this.fechaPactada = fechaPactada;
        this.costoTotal = costoTotal;
        this.estado = estado;
    }

    /**
     * Constructor para nuevos registros.
     */
    public Alquiler(String idCliente, String idProducto, double costoTotal) {
        this.idCliente = idCliente;
        this.idProducto = idProducto;
        this.costoTotal = costoTotal;
        this.fechaAlquiler = LocalDate.now();
        this.fechaPactada = LocalDate.now().plusDays(3);
        this.estado = "ACTIVO";
    }

    /**
     * Obtiene el identificador del alquiler.
     * @return int id.
     */
    public int getId() { return id; }

    /**
     * Establece el identificador del alquiler.
     * @param id identificador único.
     */
    public void setId(int id) { this.id = id; }

    /**
     * Obtiene el usuario del cliente.
     * @return String usuario.
     */
    public String getIdCliente() { return idCliente; }

    /**
     * Establece el usuario del cliente.
     * @param idCliente usuario.
     */
    public void setIdCliente(String idCliente) { this.idCliente = idCliente; }

    /**
     * Obtiene el id del producto.
     * @return String id producto.
     */
    public String getIdProducto() { return idProducto; }

    /**
     * Establece el id del producto.
     * @param idProducto id.
     */
    public void setIdProducto(String idProducto) { this.idProducto = idProducto; }

    /**
     * Obtiene la fecha de inicio.
     * @return LocalDate fecha.
     */
    public LocalDate getFechaAlquiler() { return fechaAlquiler; }

    /**
     * Establece la fecha de inicio.
     * @param fechaAlquiler fecha.
     */
    public void setFechaAlquiler(LocalDate fechaAlquiler) { this.fechaAlquiler = fechaAlquiler; }

    /**
     * Obtiene la fecha pactada de entrega.
     * @return LocalDate fecha pactada.
     */
    public LocalDate getFechaPactada() { return fechaPactada; }

    /**
     * Establece la fecha pactada de entrega.
     * @param fechaPactada fecha.
     */
    public void setFechaPactada(LocalDate fechaPactada) { this.fechaPactada = fechaPactada; }

    /**
     * Obtiene el costo total.
     * @return double costo.
     */
    public double getCostoTotal() { return costoTotal; }

    /**
     * Establece el costo total.
     * @param costoTotal valor.
     */
    public void setCostoTotal(double costoTotal) { this.costoTotal = costoTotal; }

    /**
     * Obtiene el estado del alquiler.
     * @return String estado.
     */
    public String getEstado() { return estado; }

    /**
     * Establece el estado del alquiler.
     * @param estado estado actual.
     */
    public void setEstado(String estado) { this.estado = estado; }
}