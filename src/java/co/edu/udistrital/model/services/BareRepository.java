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
 * Clase base abstracta que define el contrato de persistencia de datos. Aplica
 * el Principio de Responsabilidad Única (SRP) y DRY.
 *
 * @author Juan David Díaz Pérez
 * @version 1.0
 * @param <T> Tipo de entidad genérica que maneja el repositorio.
 */
public abstract class BareRepository<T> {

    /**
     * Obtiene una conexión activa a la base de datos MariaDB. Diseñado para
     * usarse dentro de bloques Try-with-Resources.
     *
     * @return Connection Objeto de conexión JDBC.
     * @throws SQLException Si hay un error de red o credenciales.
     */
    protected Connection getConnection() throws SQLException {
        try {
            Class.forName(Config.DRIVER);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver MariaDB no encontrado.", e);
        }
        return DriverManager.getConnection(Config.URL, Config.USER, Config.PASS);
    }

    /**
     * Agrega un nuevo registro a la base de datos.
     *
     * @param entity Entidad a persistir.
     * @return Verdadero si se guardó exitosamente.
     */
    public abstract boolean add(T entity);

    /**
     * Obtiene un registro por su identificador primario.
     *
     * @param id Llave primaria (puede ser numérico en formato String o el
     * Username).
     * @return Entidad encontrada o null.
     */
    public abstract T getById(String id);

    /**
     * Obtiene todos los registros de una tabla.
     *
     * @return Lista de entidades.
     */
    public abstract List<T> getAll();

    /**
     * Actualiza un registro existente.
     *
     * @param entity Entidad con los datos nuevos.
     * @return Verdadero si se actualizó.
     */
    public abstract boolean update(T entity);

    /**
     * Elimina un registro por su llave primaria.
     *
     * @param id Llave primaria del registro.
     * @return Verdadero si se eliminó.
     */
    public abstract boolean delete(String id);
}
