/*
 * @(#)Pelicula.java 1.0 21/04/2026
 *
 * Copyright(C) Juan David Díaz Pérez
 *
 */
package co.edu.udistrital.model.logic;

/**
 * Entidad específica para productos de tipo película.
 *
 * @author Juan David Díaz Pérez
 * @version 1.0
 */
public class Pelicula extends Producto {

    private String duracion;

    /**
     * Constructor de Pelicula.
     *
     * @param id Identificador.
     * @param nombre Título.
     * @param formato Formato (DVD, BluRay).
     * @param costoDia Costo.
     * @param stock Unidades.
     * @param duracion Tiempo de la película.
     */
    public Pelicula(int id, String nombre, String formato, double costoDia, int stock, String duracion) {
        super(id, nombre, formato, costoDia, stock);
        this.duracion = duracion;
    }
    
    /**
     * Constructor para setteos.
     */
    public Pelicula() {
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }
}
