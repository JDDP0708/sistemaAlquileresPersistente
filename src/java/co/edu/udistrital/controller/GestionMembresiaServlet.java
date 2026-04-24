/*
 * @(#)GestionMembresiaServlet.java 1.0 23/04/2026
 *
 * Copyright(C) Juan David Díaz Pérez
 *
 */
package co.edu.udistrital.controller;

import co.edu.udistrital.model.logic.Administrador;
import co.edu.udistrital.model.logic.Membresia;
import co.edu.udistrital.model.logic.Perfil;
import co.edu.udistrital.model.services.MembresiaRepository;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Controlador especializado en la administración de niveles de membresía.
 * Soporta creación, actualización y eliminación lógica.
 *
 * @author Juan David Díaz Pérez
 * @version 1.0
 */
@WebServlet(name = "GestionMembresiaServlet", urlPatterns = {"/GestionMembresiaServlet"})
public class GestionMembresiaServlet extends HttpServlet {

    /**
     * Procesa las peticiones del módulo de membresías.
     *
     * @param request Datos del formulario.
     * @param response Redirección a la vista.
     * @throws jakarta.servlet.ServletException
     * @throws java.io.IOException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
            Perfil autor = (Perfil) session.getAttribute("usuarioLogueado");

            if (autor == null || !(autor instanceof Administrador)) {
                response.sendRedirect("index.jsp?error=unauthorized");
                return;
            }

            String accion = request.getParameter("accion");
            MembresiaRepository repo = new MembresiaRepository();

            try {
                if (null != accion) {
                    switch (accion) {
                        case "crear" -> {
                            String nombre = request.getParameter("nombre");
                            double descuento = Double.parseDouble(request.getParameter("descuento"));
                            double costo = Double.parseDouble(request.getParameter("costo"));

                            if (repo.add(new Membresia(0, nombre, descuento, costo))) {
                                response.sendRedirect("admin.jsp?memSuccess=created");
                            } else {
                                response.sendRedirect("admin.jsp?error=db");
                            }
                        }
                        case "actualizar" -> {
                            int id = Integer.parseInt(request.getParameter("id"));
                            double descuento = Double.parseDouble(request.getParameter("descuento"));
                            double costo = Double.parseDouble(request.getParameter("costo"));
                            Membresia m = repo.getById(String.valueOf(id));
                            if (m != null && id != 1 && !m.getNombre().equalsIgnoreCase("Normal")) {
                                m.setPorcentajeDescuento(descuento);
                                m.setCostoCambio(costo);
                                repo.update(m);
                                response.sendRedirect("admin.jsp?memSuccess=updated");
                            } else {
                                response.sendRedirect("admin.jsp?error=protected");
                            }
                        }
                        case "eliminar" -> {
                            int id = Integer.parseInt(request.getParameter("id"));
                            Membresia m = repo.getById(String.valueOf(id));
                            if (m != null && id != 1 && !m.getNombre().equalsIgnoreCase("Normal")) {
                                repo.delete(String.valueOf(id));
                                response.sendRedirect("admin.jsp?memSuccess=deleted");
                            } else {
                                response.sendRedirect("admin.jsp?error=protected");
                            }
                        }
                        default -> {
                        }
                    }
                }
            } catch (IOException | NumberFormatException e) {
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
