/*
 * @(#)AlquilerRepository.java 1.0 21/04/2026
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
 * Repositorio de la tabla transaccional alquileres.
 *
 * @author Juan David Díaz Pérez
 * @version 1.0
 */
public class AlquilerRepository extends BareRepository<Alquiler> {

    @Override
    public boolean add(Alquiler entity) {
        String sql = "INSERT INTO alquileres (id_cliente, id_producto, fecha_inicio, fecha_devolucion, costo, estado) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, Integer.parseInt(entity.getIdCliente()));
            ps.setInt(2, Integer.parseInt(entity.getIdProducto()));
            ps.setDate(3, Date.valueOf(entity.getFechaAlquiler()));
            ps.setDate(4, Date.valueOf(entity.getFechaPactada()));
            ps.setDouble(5, entity.getCostoTotal());
            ps.setString(6, entity.getEstado());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error en Alquiler.add: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Alquiler> getAll() {
        List<Alquiler> historial = new ArrayList<>();
        String sql = "SELECT * FROM alquileres";
        try (Connection con = getConnection(); Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                historial.add(new Alquiler(
                        rs.getInt("id"),
                        rs.getString("id_cliente"),
                        rs.getString("id_producto"),
                        rs.getDate("fecha_inicio").toLocalDate(),
                        rs.getDate("fecha_devolucion").toLocalDate(),
                        rs.getDouble("costo"),
                        rs.getString("estado")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error en Alquiler.getAll: " + e.getMessage());
        }
        return historial;
    }

    @Override
    public Alquiler getById(String id) {
        return null;
    }

    @Override
    public boolean update(Alquiler entity) {
        String sql = "UPDATE alquileres SET estado = ? WHERE id = ?";
        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, entity.getEstado());
            ps.setInt(2, entity.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error en Alquiler.update: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(String id) {
        return false;
    }
}
