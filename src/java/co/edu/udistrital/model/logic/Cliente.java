/*
 * @(#)Cliente.java 1.0 21/04/2026
 *
 * Copyright(C) Juan David Díaz Pérez
 *
 */
package co.edu.udistrital.model.logic;

/**
 * Entidad que representa a un cliente con saldo y beneficios.
 *
 * @author Juan David Díaz Pérez
 * @version 1.0
 */
public class Cliente extends Perfil {

    private double saldo;
    private Membresia membresia;

    /**
     * Constructor de Cliente.
     *
     * @param usuario Nombre de usuario.
     * @param contrasena Contraseña.
     */
    public Cliente(String usuario, String contrasena) {
        super(usuario, contrasena);
        this.saldo = 0.0;
    }
    
    /**
     * Constructor para setteos.
     */
    public Cliente(){};

    /**
     * Reduce el saldo actual tras una transacción.
     *
     * @param monto Valor a restar.
     */
    public void descontarSaldo(double monto) {
        this.saldo -= monto;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public Membresia getMembresia() {
        return membresia;
    }

    public void setMembresia(Membresia membresia) {
        this.membresia = membresia;
    }
}
