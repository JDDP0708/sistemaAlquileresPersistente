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
 * DAO especializado en inventario polimórfico.
 *
 * @author Juan David Díaz Pérez
 * @version 1.0
 */
public class ProductoRepository extends BareRepository<Producto> {

    /**
     * Recupera el catálogo, discriminando constructores.
     *
     * @return Catálogo mezclado.
     */
    @Override
    public List<Producto> getAll() {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT * FROM productos";

        try (Connection con = getConnection(); Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                String tipo = rs.getString("tipo_producto");
                Producto p;

                if ("PELICULA".equals(tipo)) {
                    p = new Pelicula(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getString("formato"),
                            rs.getDouble("costo_dia"),
                            rs.getInt("stock"),
                            rs.getString("duracion")
                    );
                } else {
                    p = new Videojuego(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getString("formato"),
                            rs.getDouble("costo_dia"),
                            rs.getInt("stock"),
                            rs.getString("plataforma")
                    );
                }
                productos.add(p);
            }
        } catch (SQLException e) {
            System.err.println("Excepción: " + e.getMessage());
        }
        return productos;
    }

    /**
     * @param entity Producto a ingresar.
     * @return Booleano.
     */
    @Override
    public boolean add(Producto entity) {
        return false;
    }

    /**
     * @param id ID único.
     * @return Producto unitario.
     */
    @Override
    public Producto getById(String id) {
        return null;
    }
}
