/*
 * @(#)DevolverServlet.java 1.0 23/04/2026
 *
 * Copyright(C) Juan David Díaz Pérez
 *
 */
package co.edu.udistrital.controller;

import co.edu.udistrital.model.logic.Alquiler;
import co.edu.udistrital.model.logic.Cliente;
import co.edu.udistrital.model.logic.Producto;
import co.edu.udistrital.model.services.AlquilerRepository;
import co.edu.udistrital.model.services.ProductoRepository;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;

/**
 * Controlador encargado de procesar la devolución de productos. Finaliza el
 * ciclo de renta cambiando el estado y restaurando el stock.
 *
 * @author Juan David Díaz Pérez
 * @version 1.0
 */
@WebServlet(name = "DevolverServlet", urlPatterns = {"/DevolverServlet"})
public class DevolverServlet extends HttpServlet {

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

        // 1. Verificación de sesión de cliente
        HttpSession session = request.getSession();
        Cliente cliente = (Cliente) session.getAttribute("usuarioLogueado");

        if (cliente == null) {
            response.sendRedirect("index.jsp");
            return;
        }

        // 2. Captura del parámetro enviado desde la tabla encapsulada en cliente.jsp
        String idAlquilerStr = request.getParameter("idAlquiler");

        // Protección contra valores nulos o vacíos
        if (idAlquilerStr == null || idAlquilerStr.trim().isEmpty()) {
            response.sendRedirect("cliente.jsp?error=datos_vacios");
            return;
        }

        try {
            int idAlquiler = Integer.parseInt(idAlquilerStr);

            AlquilerRepository alqRepo = new AlquilerRepository();
            ProductoRepository prodRepo = new ProductoRepository();

            // 3. Buscar el alquiler exacto dentro de los activos del cliente
            // Usamos getByCustomer para mayor seguridad y evitar buscar en toda la tabla
            List<Alquiler> alquileresActivos = alqRepo.getByCustomer(cliente.getUsuario());
            Alquiler alquilerDestino = null;

            for (Alquiler a : alquileresActivos) {
                if (a.getId() == idAlquiler) {
                    alquilerDestino = a;
                    break;
                }
            }

            if (alquilerDestino != null) {

                // 4. Cambiar el estado a DEVUELTO y actualizar en BD
                alquilerDestino.setEstado("DEVUELTO");

                if (alqRepo.update(alquilerDestino)) {

                    // 5. Recuperar el producto de la base de datos y sumar 1 al stock
                    Producto producto = prodRepo.getById(String.valueOf(alquilerDestino.getIdProducto()));

                    if (producto != null) {
                        producto.setStock(producto.getStock() + 1);
                        prodRepo.update(producto);
                    }

                    // Devolución exitosa, redirige para que la tabla se recargue
                    response.sendRedirect("cliente.jsp?devolucion=success");

                } else {
                    response.sendRedirect("cliente.jsp?error=falla_bd");
                }
            } else {
                // Si el ID del alquiler no le pertenece al cliente o ya fue devuelto
                response.sendRedirect("cliente.jsp?error=alquiler_no_encontrado");
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
