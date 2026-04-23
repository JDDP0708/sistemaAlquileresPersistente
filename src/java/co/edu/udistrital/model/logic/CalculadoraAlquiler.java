/*
 * @(#)CalculadoraAlquiler.java 1.0 21/04/2026
 *
 * Copyright(C) Juan David Díaz Pérez
 *
 */
package co.edu.udistrital.model.logic;

/**
 * Servicio de dominio responsable de liquidar los costos.
 *
 * @author Juan David Díaz Pérez
 * @version 1.0
 */
public class CalculadoraAlquiler {

    /**
     * Genera el cobro aplicando reglas de negocio y membresías.
     *
     * @param p Producto a alquilar.
     * @param c Cliente que solicita.
     * @param dias Tiempo del alquiler.
     * @return Valor monetario final.
     */
    public double calcularTotal(Producto p, Cliente c, int dias) {
        double subtotal = p.getCostoDia() * dias;
        if (c.getMembresia() != null) {
            return c.getMembresia().aplicarDescuento(subtotal);
        }
        return subtotal;
    }
}
