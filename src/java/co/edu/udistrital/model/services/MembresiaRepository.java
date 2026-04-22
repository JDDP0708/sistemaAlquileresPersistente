/*
* @(#)MembresiaRepository.java 1.0 21/04/2026
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
 * Repositorio con los CRUD para la tabla membresia
 *
 * @author Juan David Díaz Pérez
 * @version 1.0
 */
public class MembresiaRepository extends BareRepository<Membresia> {

    @Override
    public Membresia getById(String id) {
        String sql = "SELECT * FROM membresias WHERE id = ?";
        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, Integer.parseInt(id));
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Membresia(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getDouble("porcentaje_descuento"),
                            rs.getDouble("costo_cambio")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Membresia> getAll() {
        List<Membresia> lista = new ArrayList<>();
        String sql = "SELECT * FROM membresias";
        try (Connection con = getConnection(); Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Membresia(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getDouble("porcentaje_descuento"),
                        rs.getDouble("costo_cambio")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    // Implementar add, update y delete según sea necesario...
    @Override
    public boolean add(Membresia entity) {
        return false;
    }

    @Override
    public boolean update(Membresia entity) {
        return false;
    }

    @Override
    public boolean delete(String id) {
        return false;
    }
}
