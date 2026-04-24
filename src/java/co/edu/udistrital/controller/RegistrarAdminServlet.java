/*
 * @(#)RegistrarAdminServlet.java 1.0 23/04/2026
 *
 * Copyright(C) Juan David Díaz Pérez
 *
 */
package co.edu.udistrital.controller;

import co.edu.udistrital.model.logic.Administrador;
import co.edu.udistrital.model.logic.Perfil;
import co.edu.udistrital.model.services.UsuarioRepository;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Controlador especializado en el registro de nuevo personal administrativo.
 *
 * @author Juan David Díaz Pérez
 * @version 1.0
 */
@WebServlet(name = "RegistrarAdminServlet", urlPatterns = {"/RegistrarAdminServlet"})
public class RegistrarAdminServlet extends HttpServlet {

    /**
     * Procesa la creación de un nuevo administrador.
     *
     * @param request Datos de la petición.
     * @param response Redirección.
     * @throws ServletException Error de contenedor.
     * @throws IOException Error de E/S.
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Perfil autor = (Perfil) session.getAttribute("usuarioLogueado");

        if (autor == null || !autor.getUsuario().equals("admin")) {
            response.sendRedirect("index.jsp?error=unauthorized");
            return;
        }

        String user = request.getParameter("usuario");
        String pass = request.getParameter("contrasena");

        UsuarioRepository repo = new UsuarioRepository();

        if (repo.getById(user) != null) {
            response.sendRedirect("admin.jsp?error=exists");
        } else {
            if (repo.add(new Administrador(user, pass))) {
                response.sendRedirect("admin.jsp?successAdmin=1");
            } else {
                response.sendRedirect("admin.jsp?error=db");
            }
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
