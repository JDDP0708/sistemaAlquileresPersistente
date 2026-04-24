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

    /** Host del servidor MariaDB */
    public static final String SERVIDOR = "localhost";
    
    /** Puerto de acceso al servidor MariaDB */
    public static final String PUERTO = "3306";
    
    /** Nombre de la base de datos */
    public static final String BD = "sistemaalquileres";
    
    /** Usuario de autenticación para conectar a MariaDB */
    public static final String USER = "root";
    
    /** Contraseña de autenticación para conectar a MariaDB */
    public static final String PASS = "admin";

    /** URL de conexión JDBC construida dinámicamente con los parámetros de conexión */
    public static final String URL = "jdbc:mariadb://" + SERVIDOR + ":" + PUERTO + "/" + BD;
    
    /** Nombre de la clase driver JDBC para MariaDB */
    public static final String DRIVER = "org.mariadb.jdbc.Driver";

    /**
     * Constructor privado para evitar instancias de clase estática.
     */
    private Config() {
    }
}
