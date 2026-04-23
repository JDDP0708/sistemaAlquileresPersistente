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

    /**
     * Obtiene el identificador del producto.
     *
     * @return Identificador único.
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el identificador del producto.
     *
     * @param id Nuevo identificador.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre o título del producto.
     *
     * @return Nombre del producto.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del producto.
     *
     * @param nombre Nuevo nombre.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el formato del producto.
     *
     * @return Formato (DVD, BluRay, Físico, Digital, etc.).
     */
    public String getFormato() {
        return formato;
    }

    /**
     * Establece el formato del producto.
     *
     * @param formato Nuevo formato.
     */
    public void setFormato(String formato) {
        this.formato = formato;
    }

    /**
     * Obtiene el costo por día del alquiler.
     *
     * @return Valor base por día.
     */
    public double getCostoDia() {
        return costoDia;
    }

    /**
     * Establece el costo por día del alquiler.
     *
     * @param costoDia Nuevo costo diario.
     */
    public void setCostoDia(double costoDia) {
        this.costoDia = costoDia;
    }

    /**
     * Obtiene la cantidad disponible en stock.
     *
     * @return Número de unidades disponibles.
     */
    public int getStock() {
        return stock;
    }

    /**
     * Establece la cantidad disponible en stock.
     *
     * @param stock Nuevas unidades disponibles.
     */
    public void setStock(int stock) {
        this.stock = stock;
    }
}
