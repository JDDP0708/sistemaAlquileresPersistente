/*
 * @(#)Administrador.java 1.0 21/04/2026
 *
 * Copyright(C) Juan David Díaz Pérez
 *
 */
package co.edu.udistrital.model.logic;

/**
 * Clase que representa a un usuario encargado de la tienda de laquileres.
 *
 * @author Juan David Díaz Pérez
 * @version 1.0
 */
public class Administrador extends Perfil {

    /**
     * Constructor de Administrador.
     *
     * @param usuario Nombre de usuario.
     * @param contrasena Contraseña.
     */
    public Administrador(String usuario, String contrasena) {
        super(usuario, contrasena);
    }

    /**
     * Constructor para setteos.
     */
    public Administrador() {
    }
;
}
