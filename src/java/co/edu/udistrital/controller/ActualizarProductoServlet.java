/*
 * @(#)ActualizarProductoServlet.java 1.0 23/04/2026
 *
 * Copyright(C) Juan David Díaz Pérez
 *
 */
package co.edu.udistrital.controller;

import co.edu.udistrital.model.logic.Producto;
import co.edu.udistrital.model.services.ProductoRepository;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Controlador encargado de procesar las actualizaciones rápidas de inventario.
 * Gestiona el cambio de precio y stock desde la vista de tablas.
 *
 * @author Juan David Díaz Pérez
 * @version 1.0
 */
@WebServlet(name = "ActualizarProductoServlet", urlPatterns = {"/ActualizarProductoServlet"})
public class ActualizarProductoServlet extends HttpServlet {

    /**
     * Procesa la actualización de un producto.
     *
     * @param request Petición con id, stock y costo.
     * @param response Redirección al panel administrativo.
     * @throws ServletException Error de contenedor.
     * @throws IOException Error de E/S.
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int id = Integer.parseInt(request.getParameter("id"));
            int stock = Integer.parseInt(request.getParameter("stock"));
            double costo = Double.parseDouble(request.getParameter("costoDia"));

            ProductoRepository repo = new ProductoRepository();
            Producto producto = repo.getById(String.valueOf(id));

            if (producto != null) {
                producto.setStock(stock);
                producto.setCostoDia(costo);

                if (repo.update(producto)) {
                    response.sendRedirect("admin.jsp?updateSuccess=1");
                } else {
                    response.sendRedirect("admin.jsp?error=db");
                }
            }
        } catch (NumberFormatException e) {
            response.sendRedirect("admin.jsp?error=invalid_data");
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
