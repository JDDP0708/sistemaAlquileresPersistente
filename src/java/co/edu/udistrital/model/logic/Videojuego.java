/*
 * @(#)Videojuego.java 1.0 21/04/2026
 *
 * Copyright(C) Juan David Díaz Pérez
 *
 */
package co.edu.udistrital.model.logic;

/**
 * Entidad específica para productos interactivos.
 *
 * @author Juan David Díaz Pérez
 * @version 1.0
 */
public class Videojuego extends Producto {

    private String plataforma;

    /**
     * Constructor de Videojuego.
     *
     * @param id Identificador.
     * @param nombre Título.
     * @param formato Físico o digital.
     * @param costoDia Costo por día.
     * @param stock Existencias.
     * @param plataforma Consola compatible.
     */
    public Videojuego(int id, String nombre, String formato, double costoDia, int stock, String plataforma) {
        super(id, nombre, formato, costoDia, stock);
        this.plataforma = plataforma;
    }

    /**
     * Constructor para setteos.
     */
    public Videojuego() {
    }

    public String getPlataforma() {
        return plataforma;
    }

    public void setPlataforma(String plataforma) {
        this.plataforma = plataforma;
    }
}
