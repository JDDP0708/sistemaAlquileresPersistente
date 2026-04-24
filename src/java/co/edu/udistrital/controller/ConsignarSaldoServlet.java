/*
 * @(#)ConsignarSaldoServlet.java 1.0 23/04/2026
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
import jakarta.servlet.http.HttpSession;

/**
 * Controlador encargado de procesar las recargas de saldo de los clientes.
 *
 * @author Juan David Díaz Pérez
 * @version 1.0
 */
@WebServlet(name = "ConsignarSaldoServlet", urlPatterns = {"/ConsignarSaldoServlet"})
public class ConsignarSaldoServlet extends HttpServlet {

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

        // Seguridad: Verificar que el usuario exista y sea cliente
        if (cliente == null) {
            response.sendRedirect("index.jsp");
            return;
        }

        try {
            double valorRecarga = Double.parseDouble(request.getParameter("valor"));

            if (valorRecarga > 0) {

                cliente.setSaldo(cliente.getSaldo() + valorRecarga);

                UsuarioRepository repo = new UsuarioRepository();
                if (repo.update(cliente)) {
                    session.setAttribute("usuarioLogueado", cliente);
                    response.sendRedirect("cliente.jsp?saldo=success");
                } else {
                    response.sendRedirect("cliente.jsp?error=db");
                }
            } else {
                response.sendRedirect("cliente.jsp?error=valor_invalido");
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
