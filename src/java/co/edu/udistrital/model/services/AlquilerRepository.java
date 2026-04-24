/*
 * @(#)AlquilerRepository.java 1.0 23/04/2026
 *
 * Copyright(C) Juan David Díaz Pérez
 *
 */
package co.edu.udistrital.model.services;

import co.edu.udistrital.model.logic.Alquiler;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Repositorio encargado de gestionar el historial transaccional de alquileres.
 *
 * @author Juan David Díaz Pérez
 * @version 1.0
 */
public class AlquilerRepository extends BareRepository<Alquiler> {

    /**
     * Agrega un nuevo registro de alquiler a la base de datos.
     *
     * @param entity Objeto Alquiler a insertar con información de la transacción.
     * @return Verdadero si la inserción fue exitosa.
     * @throws SQLException Si hay error en la operación de base de datos.
     */
    @Override
    public boolean add(Alquiler entity) {

        String sql = "INSERT INTO alquileres (id_cliente, id_producto, fecha_alquiler, costo, estado) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, entity.getIdCliente());
            ps.setInt(2, entity.getIdProducto());
            ps.setDate(3, Date.valueOf(entity.getFechaAlquiler()));
            ps.setDouble(4, entity.getCosto());
            ps.setString(5, entity.getEstado());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error BD Add Alquiler: " + e.getMessage());
            return false;
        }
    }

    /**
     * Recupera todos los alquileres activos de un cliente específico.
     *
     * @param username Identificador del cliente.
     * @return Lista de alquileres.
     */
    public List<Alquiler> getByCustomer(String username) {
        List<Alquiler> lista = new ArrayList<>();
        String sql = "SELECT * FROM alquileres WHERE id_cliente = ? AND estado = 'ACTIVO'";
        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(new Alquiler(
                            rs.getInt("id"),
                            rs.getString("id_cliente"),
                            rs.getInt("id_producto"),
                            rs.getDate("fecha_alquiler").toLocalDate(),
                            rs.getDouble("costo"),
                            rs.getString("estado")
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error en AlquilerRepository.getByCustomer: " + e.getMessage());
        }
        return lista;
    }

    /**
     * Obtiene todos los registros de alquileres en la base de datos.
     *
     * @return Lista completa de alquileres históricos.
     * @throws SQLException Si hay error en la operación de base de datos.
     */
    @Override
    public List<Alquiler> getAll() {
        List<Alquiler> lista = new ArrayList<>();
        String sql = "SELECT * FROM alquileres";
        try (Connection con = getConnection(); Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Alquiler(
                        rs.getInt("id"),
                        rs.getString("id_cliente"),
                        rs.getInt("id_producto"),
                        rs.getDate("fecha_alquiler").toLocalDate(),
                        rs.getDouble("costo"),
                        rs.getString("estado")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error en AlquilerRepository.getAll: " + e.getMessage());
        }
        return lista;
    }

    /**
     * Actualiza el estado de un alquiler existente.
     *
     * @param entity Objeto Alquiler con el nuevo estado.
     * @return Verdadero si la actualización fue exitosa.
     * @throws SQLException Si hay error en la operación de base de datos.
     */
    @Override
    public boolean update(Alquiler entity) {
        String sql = "UPDATE alquileres SET estado = ? WHERE id = ?";
        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, entity.getEstado());
            ps.setInt(2, entity.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * Obtiene un alquiler específico por su identificador.
     * Nota: Esta operación no está implementada en la lógica actual.
     *
     * @param id Identificador del alquiler a buscar.
     * @return Null - operación no implementada.
     */
    @Override
    public Alquiler getById(String id) {
        return null;
    }

    /**
     * Elimina un alquiler de la base de datos.
     * Nota: Esta operación no está implementada en la lógica actual.
     *
     * @param id Identificador del alquiler a eliminar.
     * @return Falso - operación no implementada.
     */
    @Override
    public boolean delete(String id) {
        return false;
    }
}
