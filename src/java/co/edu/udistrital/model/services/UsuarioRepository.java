/*
 * @(#)UsuarioRepository.java 1.0 21/04/2026
 *
 * Copyright(C) Juan David Díaz Pérez
 *
 */
package co.edu.udistrital.model.services;

import co.edu.udistrital.model.logic.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Repositorio polimórfico para gestionar Clientes y Administradores.
 *
 * @author Juan David Díaz Pérez
 * @version 1.0
 */
public class UsuarioRepository extends BareRepository<Perfil> {

    @Override
    public boolean add(Perfil entity) {
        String sql = "INSERT INTO usuarios (username, password, tipo_perfil, saldo, id_membresia) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, entity.getUsuario());
            ps.setString(2, entity.getContrasena());

            if (entity instanceof Administrador) {
                ps.setString(3, "ADMIN");
                ps.setNull(4, Types.DECIMAL);
                ps.setNull(5, Types.INTEGER);
            } else {
                ps.setString(3, "CLIENTE");
                ps.setDouble(4, ((Cliente) entity).getSaldo());
                // Por defecto, se le asigna la membresía básica (id 1)
                ps.setInt(5, 1);
            }
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error en Usuario.add: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Perfil getById(String username) {
        String sql = "SELECT u.*, m.nombre AS nom_mem, m.porcentaje_descuento, m.costo_cambio "
                + "FROM usuarios u LEFT JOIN membresias m ON u.id_membresia = m.id WHERE u.username = ?";
        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    if ("ADMIN".equals(rs.getString("tipo_perfil"))) {
                        return new Administrador(rs.getString("username"), rs.getString("password"));
                    } else {
                        Membresia mem = new Membresia(rs.getInt("id_membresia"), rs.getString("nom_mem"), rs.getDouble("porcentaje_descuento"), rs.getDouble("costo_cambio"));
                        Cliente c = new Cliente(rs.getString("username"), rs.getString("password"));
                        c.setSaldo(rs.getDouble("saldo"));
                        c.setMembresia(mem);
                        return c;
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error en Usuario.getById: " + e.getMessage());
        }
        return null;
    }

    /**
     * Método exclusivo para obtener solo la lista de administradores en la
     * vista del SuperAdmin.
     *
     * @return Lista de Perfiles de tipo Administrador.
     */
    public List<Perfil> getAllAdmins() {
        List<Perfil> admins = new ArrayList<>();
        String sql = "SELECT * FROM usuarios WHERE tipo_perfil = 'ADMIN'";
        try (Connection con = getConnection(); Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                admins.add(new Administrador(rs.getString("username"), rs.getString("password")));
            }
        } catch (SQLException e) {
            System.err.println("Error en Usuario.getAllAdmins: " + e.getMessage());
        }
        return admins;
    }

    /**
     * @return Lista vacía por defecto.
     */
    @Override
    public List<Perfil> getAll() {
        return null;
        /* Implementar si se requiere listar a todos los usuarios */ }

    @Override
    public boolean update(Perfil entity) {
        if (entity instanceof Cliente cliente) {
            String sql = "UPDATE usuarios SET saldo = ?, id_membresia = ? WHERE username = ?";
            try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setDouble(1, cliente.getSaldo());
                ps.setInt(2, cliente.getMembresia().getId());
                ps.setString(3, entity.getUsuario());
                return ps.executeUpdate() > 0;
            } catch (SQLException e) {
                System.err.println("Error en Usuario.update: " + e.getMessage());
            }
        }
        return false;
    }

    @Override
    public boolean delete(String username) {
        String sql = "DELETE FROM usuarios WHERE username = ?";
        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, username);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error en Usuario.delete: " + e.getMessage());
            return false;
        }
    }
}
