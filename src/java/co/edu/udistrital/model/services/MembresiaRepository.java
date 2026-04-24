/*
 * @(#)MembresiaRepository.java 1.0 23/04/2026
 *
 * Copyright(C) Juan David Díaz Pérez
 *
 */
package co.edu.udistrital.model.services;

import co.edu.udistrital.model.logic.Membresia;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Repositorio encargado de la persistencia de los tipos de membresía.
 *
 * @author Juan David Díaz Pérez
 * @version 1.0
 */
public class MembresiaRepository extends BareRepository<Membresia> {

    /**
     * Agrega una nueva membresía a la base de datos.
     *
     * @param entity Objeto Membresia a insertar.
     * @return Verdadero si la inserción fue exitosa.
     * @throws SQLException Si hay error en la operación de base de datos.
     */
    @Override
    public boolean add(Membresia entity) {
        String sql = "INSERT INTO membresias (nombre, porcentaje_descuento, costo_cambio) VALUES (?, ?, ?)";
        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, entity.getNombre());
            ps.setDouble(2, entity.getPorcentajeDescuento());
            ps.setDouble(3, entity.getCostoCambio());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error en Membresia.add: " + e.getMessage());
            return false;
        }
    }

    /**
     * Actualiza el porcentaje de descuento y el costo de una membresía.
     * El nombre no se actualiza para preservar la integridad referencial histórica.
     *
     * @param entity Objeto Membresia con los nuevos datos.
     * @return Verdadero si la actualización fue exitosa.
     * @throws SQLException Si hay error en la operación de base de datos.
     */
    @Override
    public boolean update(Membresia entity) {
        String sql = "UPDATE membresias SET porcentaje_descuento = ?, costo_cambio = ? WHERE id = ?";
        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDouble(1, entity.getPorcentajeDescuento());
            ps.setDouble(2, entity.getCostoCambio());
            ps.setInt(3, entity.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * Obtiene todas las membresías registradas en la base de datos.
     *
     * @return Lista de todos los objetos Membresia.
     * @throws SQLException Si hay error en la operación de base de datos.
     */
    @Override
    public List<Membresia> getAll() {
        List<Membresia> lista = new ArrayList<>();
        String sql = "SELECT * FROM membresias";
        try (Connection con = getConnection(); Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Membresia(rs.getInt("id"), rs.getString("nombre"), rs.getDouble("porcentaje_descuento"), rs.getDouble("costo_cambio")));
            }
        } catch (SQLException e) {
            System.err.println("Error en MembresiaRepository.getAll: " + e.getMessage());
        }
        return lista;
    }

    /**
     * Obtiene una membresía específica por su identificador.
     *
     * @param id Identificador de la membresía a buscar.
     * @return Objeto Membresia si existe, null en caso contrario.
     * @throws SQLException Si hay error en la operación de base de datos.
     */
    @Override
    public Membresia getById(String id) {
        String sql = "SELECT * FROM membresias WHERE id = ?";
        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, Integer.parseInt(id));
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Membresia(rs.getInt("id"), rs.getString("nombre"), rs.getDouble("porcentaje_descuento"), rs.getDouble("costo_cambio"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error en MembresiaRepository.getById: " + e.getMessage());
        }
        return null;
    }

    /**
     * Elimina una membresía de la base de datos.
     * No permite eliminar la membresía con id=1 para mantener integridad de datos.
     *
     * @param id Identificador de la membresía a eliminar.
     * @return Verdadero si la eliminación fue exitosa.
     * @throws SQLException Si hay error en la operación de base de datos.
     */
    @Override
    public boolean delete(String id) {
        String sql = "DELETE FROM membresias WHERE id = ? AND id != 1";
        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, Integer.parseInt(id));
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error en Membresia.delete: " + e.getMessage());
            return false;
        }
    }
}
