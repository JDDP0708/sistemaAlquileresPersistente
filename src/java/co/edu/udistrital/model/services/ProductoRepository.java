/*
 * @(#)ProductoRepository.java 1.0 21/04/2026
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
 * Repositorio de Inventario. Soporta Single Table Inheritance (STI).
 *
 * @author Juan David Díaz Pérez
 * @version 1.0
 */
public class ProductoRepository extends BareRepository<Producto> {

    @Override
    public boolean add(Producto entity) {
        String sql = "INSERT INTO productos (nombre, costo_dia, stock, tipo_producto, formato, duracion, plataforma) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, entity.getNombre());
            ps.setDouble(2, entity.getCostoDia());
            ps.setInt(3, entity.getStock());
            ps.setString(5, entity.getFormato());

            if (entity instanceof Pelicula) {
                ps.setString(4, "PELICULA");
                ps.setString(6, ((Pelicula) entity).getDuracion());
                ps.setNull(7, Types.VARCHAR);
            } else if (entity instanceof Videojuego) {
                ps.setString(4, "JUEGO");
                ps.setNull(6, Types.VARCHAR);
                ps.setString(7, ((Videojuego) entity).getPlataforma());
            }
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error en Producto.add: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Producto> getAll() {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT * FROM productos";
        try (Connection con = getConnection(); Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                String tipo = rs.getString("tipo_producto");
                if ("PELICULA".equals(tipo)) {
                    productos.add(new Pelicula(rs.getInt("id"), rs.getString("nombre"), rs.getString("formato"), rs.getDouble("costo_dia"), rs.getInt("stock"), rs.getString("duracion")));
                } else {
                    productos.add(new Videojuego(rs.getInt("id"), rs.getString("nombre"), rs.getString("formato"), rs.getDouble("costo_dia"), rs.getInt("stock"), rs.getString("plataforma")));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error en Producto.getAll: " + e.getMessage());
        }
        return productos;
    }

    @Override
    public Producto getById(String id) {
        String sql = "SELECT * FROM productos WHERE id = ?";
        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, Integer.parseInt(id));
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    if ("PELICULA".equals(rs.getString("tipo_producto"))) {
                        return new Pelicula(rs.getInt("id"), rs.getString("nombre"), rs.getString("formato"), rs.getDouble("costo_dia"), rs.getInt("stock"), rs.getString("duracion"));
                    } else {
                        return new Videojuego(rs.getInt("id"), rs.getString("nombre"), rs.getString("formato"), rs.getDouble("costo_dia"), rs.getInt("stock"), rs.getString("plataforma"));
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error en Producto.getById: " + e.getMessage());
        }
        return null;
    }

    /**
     * Actualiza los valores de stock y precio de un producto existente.
     *
     * @param entity Objeto producto con los nuevos valores cargados.
     * @return verdadero si la fila fue afectada en la base de datos.
     */
    @Override
    public boolean update(Producto entity) {
        String sql = "UPDATE productos SET stock = ?, costo_dia = ? WHERE id = ?";
        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, entity.getStock());
            ps.setDouble(2, entity.getCostoDia());
            ps.setInt(3, entity.getId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error en ProductoRepository.update: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(String id) {
        return false;
    }
}
