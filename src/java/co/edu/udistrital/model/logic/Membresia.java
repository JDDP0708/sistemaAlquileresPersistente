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

    /**
     * Obtiene el identificador de la membresía.
     *
     * @return Identificador único en base de datos.
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el identificador de la membresía.
     *
     * @param id Nuevo identificador.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre comercial de la membresía.
     *
     * @return Nombre (ej. GOLD, PREMIUM).
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre comercial de la membresía.
     *
     * @param nombre Nuevo nombre.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el porcentaje de descuento de la membresía.
     *
     * @return Porcentaje de descuento aplicable.
     */
    public double getPorcentajeDescuento() {
        return porcentajeDescuento;
    }

    /**
     * Establece el porcentaje de descuento de la membresía.
     *
     * @param porcentajeDescuento Nuevo porcentaje.
     */
    public void setPorcentajeDescuento(double porcentajeDescuento) {
        this.porcentajeDescuento = porcentajeDescuento;
    }

    /**
     * Obtiene el costo de cambio a esta membresía.
     *
     * @return Costo asociado al cambio.
     */
    public double getCostoCambio() {
        return costoCambio;
    }

    /**
     * Establece el costo de cambio a esta membresía.
     *
     * @param costoCambio Nuevo costo de cambio.
     */
    public void setCostoCambio(double costoCambio) {
        this.costoCambio = costoCambio;
    }
}
