/*
 * @(#)CambiarMembresiaServlet.java 1.0 23/04/2026
 *
 * Copyright(C) Juan David Díaz Pérez
 *
 */
package co.edu.udistrital.controller;

import co.edu.udistrital.model.logic.Cliente;
import co.edu.udistrital.model.logic.Membresia;
import co.edu.udistrital.model.services.MembresiaRepository;
import co.edu.udistrital.model.services.UsuarioRepository;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Controlador para gestionar el "Upgrade" o cambio de nivel de membresía del
 * cliente. Implementa la validación de fondos y el cobro por cambio de nivel.
 *
 * @author Juan David Díaz Pérez
 * @version 1.0
 */
@WebServlet(name = "CambiarMembresiaServlet", urlPatterns = {"/CambiarMembresiaServlet"})
public class CambiarMembresiaServlet extends HttpServlet {

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

        try {
            int idNuevaMembresia = Integer.parseInt(request.getParameter("nuevaMemId"));

            MembresiaRepository memRepo = new MembresiaRepository();
            Membresia nuevaMembresia = memRepo.getById(String.valueOf(idNuevaMembresia));

            if (nuevaMembresia != null) {
                if (cliente.getSaldo() >= nuevaMembresia.getCostoCambio()) {

                    cliente.descontarSaldo(nuevaMembresia.getCostoCambio());

                    cliente.setMembresia(nuevaMembresia);

                    UsuarioRepository userRepo = new UsuarioRepository();
                    if (userRepo.update(cliente)) {
                        session.setAttribute("usuarioLogueado", cliente);
                        response.sendRedirect("cliente.jsp?membresia=success");
                    } else {
                        response.sendRedirect("cliente.jsp?error=db");
                    }
                } else {
                    response.sendRedirect("cliente.jsp?error=saldo_insuficiente_mem");
                }
            } else {
                response.sendRedirect("cliente.jsp?error=membresia_no_encontrada");
            }
        } catch (NumberFormatException e) {
            response.sendRedirect("cliente.jsp?error=datos_invalidos");
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
