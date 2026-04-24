/*
 * @(#)AlquilerServlet.java 1.1 23/04/2026
 *
 * Copyright(C) Juan David Díaz Pérez
 */
package co.edu.udistrital.controller;

import co.edu.udistrital.model.logic.*;
import co.edu.udistrital.model.services.*;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet encargado de la gestión del alquiler de productos.
 * Valida disponibilidad de stock, saldo del cliente, calcula costo total
 * considerando membresías y actualiza inventario tras confirmar alquiler.
 *
 * @author Juan David Díaz Pérez
 * @version 1.1
 */
@WebServlet(name = "AlquilerServlet", urlPatterns = {"/AlquilerServlet"})
public class AlquilerServlet extends HttpServlet {

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

        HttpSession session = request.getSession();
        Cliente cliente = (Cliente) session.getAttribute("usuarioLogueado");

        if (cliente == null) {
            response.sendRedirect("index.jsp");
            return;
        }

        String idProdStr = request.getParameter("idProd");

        if (idProdStr == null || idProdStr.trim().isEmpty()) {
            response.sendRedirect("cliente.jsp?error=datos_vacios");
            return;
        }

        try {
            int idProducto = Integer.parseInt(idProdStr);

            ProductoRepository prodRepo = new ProductoRepository();
            UsuarioRepository userRepo = new UsuarioRepository();
            AlquilerRepository alqRepo = new AlquilerRepository();

            Producto producto = prodRepo.getById(String.valueOf(idProducto));

            if (producto != null && producto.getStock() > 0) {

                CalculadoraAlquiler calc = new CalculadoraAlquiler();
                double totalPagar = calc.calcularTotal(producto, cliente, 1);

                if (cliente.getSaldo() >= totalPagar) {

                    cliente.descontarSaldo(totalPagar);
                    userRepo.update(cliente);

                    producto.setStock(producto.getStock() - 1);
                    prodRepo.update(producto);

                    Alquiler nuevoAlquiler = new Alquiler(cliente.getUsuario(), producto.getId(), totalPagar);

                    if (alqRepo.add(nuevoAlquiler)) {
                        session.setAttribute("usuarioLogueado", cliente);
                        response.sendRedirect("cliente.jsp?alquiler=success");
                    } else {
                        response.sendRedirect("cliente.jsp?error=falla_bd");
                    }
                } else {
                    response.sendRedirect("cliente.jsp?error=saldo_insuficiente");
                }
            } else {
                response.sendRedirect("cliente.jsp?error=agotado");
            }
        } catch (NumberFormatException e) {
            response.sendRedirect("cliente.jsp?error=formato_invalido");
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
