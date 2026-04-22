/*
* @(#)Config.java 1.0 21/04/2026
*
* Copyright(C) Juan David Díaz Pérez
*
 */
package co.edu.udistrital.model.services;

/**
 * Archivo de conexion con la base de datos
 *
 * @author Juan David Díaz Pérez
 * @version 1.0
 */
public class Config {
    // Parámetros de conexión
    public static final String SERVIDOR = "localhost";
    public static final String PUERTO = "3306";
    public static final String BD = "sistemaalquileres";
    public static final String USER = "root";
    public static final String PASS = "admin"; 

    // URL de conexión JDBC
    public static final String URL = "jdbc:mariadb://" + SERVIDOR + ":" + PUERTO + "/" + BD;

    public static final String DRIVER = "org.mariadb.jdbc.Driver";
}
