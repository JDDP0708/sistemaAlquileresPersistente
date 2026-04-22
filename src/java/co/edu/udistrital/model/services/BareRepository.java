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
 */
public abstract class BareRepository<T> {

    /**
     * Coneccion que va a ser usada en las implementaciones
     */
    protected Connection getConnection() throws SQLException {
        try {
            Class.forName(Config.DRIVER);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver de MariaDB no encontrado", e);
        }
        return DriverManager.getConnection(Config.URL, Config.USER, Config.PASS);
    }

    // Métodos CRUD
    public abstract boolean add(T entity);
    public abstract List<T> getAll();
    public abstract T getById(String id);
    public abstract boolean update(T entity);
    public abstract boolean delete(String id);
}