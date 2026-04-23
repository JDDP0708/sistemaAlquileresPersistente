/*
 * @(#)Perfil.java 1.0 21/04/2026
 *
 * Copyright(C) Juan David Díaz Pérez
 *
 */
package co.edu.udistrital.model.logic;

/**
 * Clase abstracta base para los usuarios del sistema.
 *
 * @author Juan David Díaz Pérez
 * @version 1.0
 */
public abstract class Perfil {

    protected String usuario;
    protected String contrasena;

    /**
     * Constructor base.
     *
     * @param usuario Nombre de usuario.
     * @param contrasena Contraseña.
     */
    public Perfil(String usuario, String contrasena) {
        this.usuario = usuario;
        this.contrasena = contrasena;
    }

    /**
     * Constructor para settear elementos.
     */
    public Perfil() {
    }

    ;

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

}
