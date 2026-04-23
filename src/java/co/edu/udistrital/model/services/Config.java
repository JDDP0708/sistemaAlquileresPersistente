/*
 * @(#)Config.java 1.0 21/04/2026
 *
 * Copyright(C) Juan David Díaz Pérez
 *
 */
package co.edu.udistrital.model.services;

/**
 * Centralización de credenciales y rutas de conexión.
 *
 * @author Juan David Díaz Pérez
 * @version 1.0
 */
public class Config {

    public static final String SERVIDOR = "localhost";
    public static final String PUERTO = "3306";
    public static final String BD = "sistemaalquileres";
    public static final String USER = "root";
    public static final String PASS = "admin";

    public static final String URL = "jdbc:mariadb://" + SERVIDOR + ":" + PUERTO + "/" + BD;
    public static final String DRIVER = "org.mariadb.jdbc.Driver";

    /**
     * Constructor privado para evitar instancias de clase estática.
     */
    private Config() {
    }
}
