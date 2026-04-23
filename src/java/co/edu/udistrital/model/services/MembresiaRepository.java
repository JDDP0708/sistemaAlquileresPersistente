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
 * DAO para administrar membresias.
 *
 * @author Juan David Díaz Pérez
 * @version 1.0
 */
public class MembresiaRepository extends BareRepository<Membresia> {

    /**
     * Recupera una membresía usando su ID físico.
     *
     * @param id String parseable a entero.
     * @return Objeto hidratado.
     */
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
            System.err.println("Excepción: " + e.getMessage());
        }
        return null;
    }

    /**
     * Extrae todas las membresías activas.
     *
     * @return Listado de configuración.
     */
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
            System.err.println("Excepción: " + e.getMessage());
        }
        return lista;
    }

    /**
     * @param entity Membresía a guardar.
     * @return Booleano.
     */
    @Override
    public boolean add(Membresia entity) {
        return false;
    }
}
