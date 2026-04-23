/*
 * @(#)Producto.java 1.0 21/04/2026
 *
 * Copyright(C) Juan David Díaz Pérez
 *
 */
package co.edu.udistrital.model.logic;

/**
 * Clase abstracta que define un artículo alquilable.
 *
 * @author Juan David Díaz Pérez
 * @version 1.0
 */
public abstract class Producto {

    protected int id;
    protected String nombre;
    protected String formato;
    protected double costoDia;
    protected int stock;

    /**
     * Constructor base de Producto.
     *
     * @param id Identificador.
     * @param nombre Título del producto.
     * @param formato Formato físico o digital.
     * @param costoDia Valor base por día.
     * @param stock Cantidad disponible.
     */
    public Producto(int id, String nombre, String formato, double costoDia, int stock) {
        this.id = id;
        this.nombre = nombre;
        this.formato = formato;
        this.costoDia = costoDia;
        this.stock = stock;
    }

    /**
     * Constructor para setteos.
     */
    public Producto() {
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

    public String getFormato() {
        return formato;
    }

    public void setFormato(String formato) {
        this.formato = formato;
    }

    public double getCostoDia() {
        return costoDia;
    }

    public void setCostoDia(double costoDia) {
        this.costoDia = costoDia;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
