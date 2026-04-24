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

    @Override
    public boolean add(Alquiler entity) {
        String sql = "INSERT INTO alquileres (id_cliente, id_producto, fecha_inicio, fecha_devolucion, costo, estado) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            // El mapeo de IDs de usuario a int se maneja internamente en la lógica o mediante el username
            ps.setString(1, entity.getIdCliente());
            ps.setString(2, entity.getIdProducto());
            ps.setDate(3, Date.valueOf(entity.getFechaAlquiler()));
            ps.setDate(4, Date.valueOf(entity.getFechaPactada()));
            ps.setDouble(5, entity.getCostoTotal());
            ps.setString(6, entity.getEstado());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
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
                    lista.add(new Alquiler(rs.getInt("id"), rs.getString("id_cliente"), rs.getString("id_producto"),
                            rs.getDate("fecha_inicio").toLocalDate(), rs.getDate("fecha_devolucion").toLocalDate(),
                            rs.getDouble("costo"), rs.getString("estado")));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error en AlquilerRepository.getByCustomer: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public List<Alquiler> getAll() {
        List<Alquiler> lista = new ArrayList<>();
        String sql = "SELECT * FROM alquileres";
        try (Connection con = getConnection(); Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Alquiler(rs.getInt("id"), rs.getString("id_cliente"), rs.getString("id_producto"),
                        rs.getDate("fecha_inicio").toLocalDate(), rs.getDate("fecha_devolucion").toLocalDate(),
                        rs.getDouble("costo"), rs.getString("estado")));
            }
        } catch (SQLException e) {
            System.err.println("Error en AlquilerRepository.getAll: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public Alquiler getById(String id) {
        return null;
    }

    @Override
    public boolean update(Alquiler entity) {
        return false;
    }

    @Override
    public boolean delete(String id) {
        return false;
    }
}
