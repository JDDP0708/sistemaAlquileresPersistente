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
        // SQL ajustado a las 5 columnas de la BD
        String sql = "INSERT INTO alquileres (id_cliente, id_producto, fecha_alquiler, estado) VALUES (?, ?, ?, ?)";
        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, entity.getIdCliente());
            ps.setInt(2, entity.getIdProducto());
            ps.setDate(3, Date.valueOf(entity.getFechaAlquiler()));
            ps.setString(4, entity.getEstado());
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
                            rs.getString("estado")
                    ));
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
                lista.add(new Alquiler(
                        rs.getInt("id"),
                        rs.getString("id_cliente"),
                        rs.getInt("id_producto"),
                        rs.getDate("fecha_alquiler").toLocalDate(),
                        rs.getString("estado")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error en AlquilerRepository.getAll: " + e.getMessage());
        }
        return lista;
    }

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

    @Override
    public Alquiler getById(String id) {
        return null;
    }

    @Override
    public boolean delete(String id) {
        return false;
    }
}
