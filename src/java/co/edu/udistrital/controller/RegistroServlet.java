/*
 * @(#)RegistroServlet.java 1.0 21/04/2026
 *
 * Copyright(C) Juan David Díaz Pérez
 *
 */
package co.edu.udistrital.controller;

import co.edu.udistrital.model.logic.Cliente;
import co.edu.udistrital.model.services.UsuarioRepository;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Controlador encargado de procesar la inscripción de nuevos usuarios.
 * Intercepta los datos del formulario público y aplica las reglas de seguridad
 * para evitar la creación no autorizada de perfiles administrativos.
 *
 * @author Juan David Díaz Pérez
 * @version 1.0
 */
@WebServlet(name = "RegistroServlet", urlPatterns = {"/RegistroServlet"})
public class RegistroServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String user = request.getParameter("usuario");
        String pass = request.getParameter("contrasena");
        String rol = request.getParameter("rol");

        if (rol != null && rol.equalsIgnoreCase("admin")) {
            response.sendRedirect("registro.jsp?error=unauthorized");
            return;
        }

        UsuarioRepository repo = new UsuarioRepository();

        if (repo.getById(user) != null) {
            response.sendRedirect("registro.jsp?error=exists");
            return;
        }

        Cliente nuevoCliente = new Cliente(user, pass);

        if (repo.add(nuevoCliente)) {
            response.sendRedirect("index.jsp?registro=1");
        } else {
            response.sendRedirect("registro.jsp?error=db");
        }
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
