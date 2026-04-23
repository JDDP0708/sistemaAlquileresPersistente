/*
 * @(#)UsuarioRepository.java 1.0 21/04/2026
 *
 * Copyright(C) Juan David Díaz Pérez
 *
 */
package co.edu.udistrital.model.services;

import co.edu.udistrital.model.logic.*;
import java.sql.*;
import java.util.List;

/**
 * DAO para administrar pefiles de usuario.
 *
 * @author Juan David Díaz Pérez
 * @version 1.0
 */
public class UsuarioRepository extends BareRepository<Perfil> {

    /**
     * Busca un usuario y carga sus dependencias relacionales (JOIN).
     *
     * @param username Login del usuario.
     * @return Perfil instanciado según su rol en BD.
     */
    @Override
    public Perfil getById(String username) {
        String sql = "SELECT u.*, m.nombre AS nom_mem, m.porcentaje_descuento, m.costo_cambio "
                + "FROM usuarios u LEFT JOIN membresias m ON u.id_membresia = m.id "
                + "WHERE u.username = ?";

        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String tipo = rs.getString("tipo_perfil");
                    if ("ADMIN".equals(tipo)) {
                        return new Administrador(rs.getString("username"), rs.getString("password"));
                    } else {
                        Membresia mem = new Membresia(
                                rs.getInt("id_membresia"),
                                rs.getString("nom_mem"),
                                rs.getDouble("porcentaje_descuento"),
                                rs.getDouble("costo_cambio")
                        );
                        Cliente c = new Cliente(rs.getString("username"), rs.getString("password"));
                        c.setSaldo(rs.getDouble("saldo"));
                        c.setMembresia(mem);
                        return c;
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Excepción: " + e.getMessage());
        }
        return null;
    }

    /**
     * @param entity Usuario a persistir.
     * @return Booleano.
     */
    @Override
    public boolean add(Perfil entity) {
        return false;
    }

    /**
     * @return Lista vacía por defecto.
     */
    @Override
    public List<Perfil> getAll() {
        return null;
    }
}
