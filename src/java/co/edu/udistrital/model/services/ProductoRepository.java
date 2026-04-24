/*
 * @(#)ProductoRepository.java 1.0 23/04/2026
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
 * Repositorio especializado en la gestión física de Películas y Videojuegos.
 * Implementa Single Table Inheritance (STI) para el mapeo polimórfico.
 *
 * @author Juan David Díaz Pérez
 * @version 1.0
 */
public class ProductoRepository extends BareRepository<Producto> {

    /**
     * Registra un nuevo producto discriminando su tipo para las columnas
     * específicas.
     *
     * @param entity Producto a guardar.
     * @return booleano resultado.
     */
    @Override
    public boolean add(Producto entity) {
        String sql = "INSERT INTO productos (nombre, costo_dia, stock, tipo_producto, formato, duracion, plataforma) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, entity.getNombre());
            ps.setDouble(2, entity.getCostoDia());
            ps.setInt(3, entity.getStock());
            ps.setString(5, entity.getFormato());

            if (entity instanceof Pelicula pelicula) {
                ps.setString(4, "PELICULA");
                ps.setString(6, pelicula.getDuracion());
                ps.setNull(7, Types.VARCHAR);
            } else {
                ps.setString(4, "JUEGO");
                ps.setNull(6, Types.VARCHAR);
                ps.setString(7, ((Videojuego) entity).getPlataforma());
            }
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error en ProductoRepository.add: " + e.getMessage());
            return false;
        }
    }

    /**
     * Actualiza el stock y el costo por día de un producto existente.
     *
     * @param entity Objeto con los nuevos valores.
     * @return booleano resultado.
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

    /**
     * Recupera el catálogo completo instanciando la subclase correspondiente.
     *
     * @return Listado de productos.
     */
    @Override
    public List<Producto> getAll() {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT * FROM productos";
        try (Connection con = getConnection(); Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                String tipo = rs.getString("tipo_producto");
                if ("PELICULA".equals(tipo)) {
                    lista.add(new Pelicula(rs.getInt("id"), rs.getString("nombre"), rs.getString("formato"),
                            rs.getDouble("costo_dia"), rs.getInt("stock"), rs.getString("duracion")));
                } else {
                    lista.add(new Videojuego(rs.getInt("id"), rs.getString("nombre"), rs.getString("formato"),
                            rs.getDouble("costo_dia"), rs.getInt("stock"), rs.getString("plataforma")));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error en ProductoRepository.getAll: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public Producto getById(String id) {
        String sql = "SELECT * FROM productos WHERE id = ?";
        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, Integer.parseInt(id));
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String tipo = rs.getString("tipo_producto");
                    if ("PELICULA".equals(tipo)) {
                        return new Pelicula(rs.getInt("id"), rs.getString("nombre"), rs.getString("formato"),
                                rs.getDouble("costo_dia"), rs.getInt("stock"), rs.getString("duracion"));
                    } else {
                        return new Videojuego(rs.getInt("id"), rs.getString("nombre"), rs.getString("formato"),
                                rs.getDouble("costo_dia"), rs.getInt("stock"), rs.getString("plataforma"));
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error en ProductoRepository.getById: " + e.getMessage());
        }
        return null;
    }

    @Override
    public boolean delete(String id) {
        return false;
    }
}
