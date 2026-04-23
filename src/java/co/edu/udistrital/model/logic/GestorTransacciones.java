/*
 * @(#)GestorTransacciones.java 1.0 21/04/2026
 *
 * Copyright(C) Juan David Díaz Pérez
 *
 */
package co.edu.udistrital.model.logic;

import co.edu.udistrital.model.services.UsuarioRepository;

/**
 * Orquestador principal que coordina alquileres y devoluciones.
 *
 * @author Juan David Díaz Pérez
 * @version 1.0
 */
public class GestorTransacciones {

    private UsuarioRepository usuarioRepo;
    private CalculadoraAlquiler calculadora;

    /**
     * Constructor que inicializa las utilidades necesarias.
     */
    public GestorTransacciones() {
        this.usuarioRepo = new UsuarioRepository();
        this.calculadora = new CalculadoraAlquiler();
    }

    /**
     * Valida y ejecuta el préstamo de un ítem.
     *
     * @param p Producto solicitado.
     * @param c Cliente solicitante.
     * @param dias Días pactados.
     * @return true si se registra con éxito.
     * @throws Exception Si falla la validación de negocio.
     */
    public boolean procesarAlquiler(Producto p, Cliente c, int dias) throws Exception {
        if (p.getStock() <= 0) {
            throw new Exception("Sin existencias del producto.");
        }

        double total = calculadora.calcularTotal(p, c, dias);

        if (c.getSaldo() < total) {
            throw new Exception("Saldo insuficiente.");
        }

        c.descontarSaldo(total);
        p.setStock(p.getStock() - 1);

        return true;
    }

    /**
     * Obtiene el repositorio de usuarios.
     *
     * @return Repositorio de usuarios.
     */
    public UsuarioRepository getUsuarioRepo() {
        return usuarioRepo;
    }

    /**
     * Establece el repositorio de usuarios.
     *
     * @param usuarioRepo Nuevo repositorio de usuarios.
     */
    public void setUsuarioRepo(UsuarioRepository usuarioRepo) {
        this.usuarioRepo = usuarioRepo;
    }

    /**
     * Obtiene la calculadora de alquileres.
     *
     * @return Calculadora configurada.
     */
    public CalculadoraAlquiler getCalculadora() {
        return calculadora;
    }

    /**
     * Establece la calculadora de alquileres.
     *
     * @param calculadora Nueva calculadora.
     */
    public void setCalculadora(CalculadoraAlquiler calculadora) {
        this.calculadora = calculadora;
    }
}
