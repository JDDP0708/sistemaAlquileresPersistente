/*
 * @(#)EliminarAdminServlet.java 1.0 23/04/2026
 *
 * Copyright(C) Juan David Díaz Pérez
 *
 */
package co.edu.udistrital.controller;

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
 * Controlador especializado en la eliminación de perfiles administrativos.
 *
 * @author Juan David Díaz Pérez
 * @version 1.0
 */
@WebServlet(name = "EliminarAdminServlet", urlPatterns = {"/EliminarAdminServlet"})
public class EliminarAdminServlet extends HttpServlet {

    /**
     * Procesa la eliminación de un administrador.
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

        String userAEliminar = request.getParameter("usuario");

        if ("admin".equals(userAEliminar)) {
            response.sendRedirect("admin.jsp?error=protected");
            return;
        }

        UsuarioRepository repo = new UsuarioRepository();
        if (repo.delete(userAEliminar)) {
            response.sendRedirect("admin.jsp?delAdmin=1");
        } else {
            response.sendRedirect("admin.jsp?error=db_del");
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
