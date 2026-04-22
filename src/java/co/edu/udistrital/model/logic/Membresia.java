/*
 * @(#)Membresia.java 1.0 21/04/2026
 *
 * Copyright(C) Juan David Díaz Pérez
 *
 */
package co.edu.udistrital.model.logic;

/**
 * Clase que representa la membresia que posee cada cliente
 *
 * @author Juan David Díaz Pérez
 * @version 1.0
 */
public class Membresia {

    private int id;
    private String nombre;
    private double porcentajeDescuento;
    private double costoCambio;

    /**
     * Constructor completo de la membresía.
     *
     * @param id Identificador en base de datos.
     * @param nombre Nombre comercial (ej. GOLD, PREMIUM).
     * @param porcentajeDescuento Descuento aplicable.
     * @param costoCambio Costo por cambiar a esta membresía.
     */
    public Membresia(int id, String nombre, double porcentajeDescuento, double costoCambio) {
        this.id = id;
        this.nombre = nombre;
        this.porcentajeDescuento = porcentajeDescuento;
        this.costoCambio = costoCambio;
    }

    /**
     * Calcula el valor final aplicando el descuento de la membresía.
     *
     * @param precioBase Precio antes de descuento.
     * @return Precio con descuento aplicado.
     */
    public double aplicarDescuento(double precioBase) {
        return precioBase - (precioBase * porcentajeDescuento);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPorcentajeDescuento() {
        return porcentajeDescuento;
    }

    public void setPorcentajeDescuento(double porcentajeDescuento) {
        this.porcentajeDescuento = porcentajeDescuento;
    }

    public double getCostoCambio() {
        return costoCambio;
    }

    public void setCostoCambio(double costoCambio) {
        this.costoCambio = costoCambio;
    }
}
