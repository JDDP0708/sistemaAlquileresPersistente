/*
 * @(#)RegistrarProductoServlet.java 1.0 21/04/2026
 *
 * Copyright(C) Juan David Díaz Pérez
 *
 */
package co.edu.udistrital.controller;

import co.edu.udistrital.model.logic.Pelicula;
import co.edu.udistrital.model.logic.Producto;
import co.edu.udistrital.model.logic.Videojuego;
import co.edu.udistrital.model.services.ProductoRepository;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Controlador para la creación dinámica de productos en el inventario.
 *
 * @author Juan David Díaz Pérez
 * @version 1.0
 */
@WebServlet(name = "RegistrarProductoServlet", urlPatterns = {"/RegistrarProductoServlet"})
public class RegistrarProductoServlet extends HttpServlet {

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

        String tipo = request.getParameter("tipo");
        String nombre = request.getParameter("nombre");
        double costoDia = Double.parseDouble(request.getParameter("costoDia"));
        int stock = Integer.parseInt(request.getParameter("stock"));
        String formato = request.getParameter("formato");

        Producto nuevoProducto = null;

        if ("PELICULA".equals(tipo)) {
            String duracion = request.getParameter("duracion");
            nuevoProducto = new Pelicula(0, nombre, formato, costoDia, stock, duracion);
        } else if ("JUEGO".equals(tipo)) {
            String plataforma = request.getParameter("plataforma");
            nuevoProducto = new Videojuego(0, nombre, formato, costoDia, stock, plataforma);
        }

        if (nuevoProducto != null) {
            ProductoRepository repo = new ProductoRepository();
            repo.add(nuevoProducto);
            response.sendRedirect("admin.jsp?registroProducto=1");
        } else {
            response.sendRedirect("admin.jsp?error=tipo_invalido");
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
