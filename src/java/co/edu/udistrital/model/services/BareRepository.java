/*
 * @(#)BareRepository.java 1.0 21/04/2026
 *
 * Copyright(C) Juan David Díaz Pérez
 *
 */
package co.edu.udistrital.model.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

/**
 * Repositorio base que va a ser heredada para seguir los DAO
 *
 * @author Juan David Díaz Pérez
 * @version 1.0
 * @param <T> Clase que mapea el repositorio.
 */
public abstract class BareRepository<T> {

    /**
     * Constructor vacío.
     */
    public BareRepository() {
    }

    /**
     * Provee el puente de datos hacia MariaDB.
     *
     * @return Conexión activa a base de datos.
     * @throws SQLException Falla de credenciales o red.
     */
    protected Connection getConnection() throws SQLException {
        try {
            Class.forName(Config.DRIVER);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver ausente.", e);
        }
        return DriverManager.getConnection(Config.URL, Config.USER, Config.PASS);
    }

    /**
     * Persiste una entidad.
     *
     * @param entity Objeto mapeado.
     * @return Estado del registro.
     */
    public abstract boolean add(T entity);

    /**
     * Consulta única por llave primaria.
     *
     * @param id Llave de búsqueda.
     * @return Entidad extraída.
     */
    public abstract T getById(String id);

    /**
     * Extracción general de registros.
     *
     * @return Colección de entidades.
     */
    public abstract List<T> getAll();
}
