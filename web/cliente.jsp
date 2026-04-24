<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="co.edu.udistrital.model.logic.*"%>
<%@page import="co.edu.udistrital.model.services.*"%>
<%@page import="java.util.List"%>
<%
    // 1. Verificación de Seguridad: Solo Clientes pueden acceder
    Perfil perfil = (Perfil) session.getAttribute("usuarioLogueado");
    if (perfil == null || !(perfil instanceof Cliente)) {
        response.sendRedirect("index.jsp");
        return;
    }
    Cliente cliente = (Cliente) perfil;

    // 2. Instanciación de Repositorios
    ProductoRepository prodRepo = new ProductoRepository();
    MembresiaRepository memRepo = new MembresiaRepository();
    AlquilerRepository alqRepo = new AlquilerRepository();

    // 3. Carga de datos dinámicos (Usando getUsuario() según tu modelo Perfil)
    List<Producto> catalogo = prodRepo.getAll();
    List<Membresia> membresias = memRepo.getAll();
    List<Alquiler> misAlquileres = alqRepo.getByCustomer(cliente.getUsuario());
%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <title>Panel de Cliente - Sistema de Alquileres</title>
        <link rel="stylesheet" href="CSS/client.css">
    </head>
    <body>
        <div class="container">

            <%-- CABECERA: Perfil y Recarga --%>
            <header class="header-client card">
                <div class="user-info">
                    <h2>Bienvenido, <%= cliente.getUsuario()%></h2>
                    <div class="badges">
                        <span class="badge-mem">Nivel: <%= cliente.getMembresia().getNombre()%></span>
                        <span class="badge-saldo">Saldo: $<%= String.format("%.2f", cliente.getSaldo())%></span>
                    </div>
                    <a href="LogoutServlet" class="btn-logout">Cerrar Sesión</a>
                </div>

                <div class="recharge-section">
                    <h4>Recargar Saldo</h4>
                    <form action="ConsignarSaldoServlet" method="POST" class="form-inline">
                        <input type="number" name="valor" step="0.01" placeholder="Monto a recargar" required>
                        <button type="submit" class="btn-primary">Consignar</button>
                    </form>
                </div>
            </header>

            <div class="main-grid">

                <%-- LATERAL: Membresía y Alquileres Activos --%>
                <aside class="sidebar">
                    <div class="card section">
                        <h3>Mejorar Membresía</h3>
                        <form action="CambiarMembresiaServlet" method="POST" class="form-vertical">
                            <label>Seleccione Nivel:</label>
                            <select name="nuevaMemId" required>
                                <% for (Membresia m : membresias) {%>
                                <option value="<%= m.getId()%>" 
                                        <%= m.getId() == cliente.getMembresia().getId() ? "disabled selected" : ""%>>
                                    <%= m.getNombre()%> (<%= (int) (m.getPorcentajeDescuento() * 100)%>% Desc.)
                                </option>
                                <% } %>
                            </select>
                            <button type="submit" class="btn-secondary">Actualizar Nivel</button>
                        </form>
                    </div>

                    <div class="card section" style="margin-top: 20px;">
                        <h3>Mis Alquileres Activos</h3>
                        <div class="rented-list">
                            <% if (misAlquileres.isEmpty()) { %>
                            <p class="empty-msg">No tienes productos alquilados.</p>
                            <% } else { %>
                            <table class="styled-table mini">
                                <thead>
                                    <tr>
                                        <th>ID Producto</th>
                                        <th>Acción</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <% for (Alquiler a : misAlquileres) {%>
                                    <tr>
                                        <td>Producto #<%= a.getIdProducto()%></td>
                                        <td>
                                            <form action="DevolverServlet" method="POST" style="margin: 0;">
                                                <input type="hidden" name="idAlquiler" value="<%= a.getId()%>">
                                                <button type="submit" class="btn-delete">Devolver</button>
                                            </form>
                                        </td>
                                    </tr>
                                    <% } %>
                                </tbody>
                            </table>
                            <% } %>
                        </div>
                    </div>
                </aside>

                <%-- CONTENIDO: Catálogo de Productos --%>
                <main class="content">
                    <div class="card section">
                        <h3>Catálogo de Películas</h3>
                        <table class="styled-table">
                            <thead>
                                <tr>
                                    <th>ID</th> <%-- Nueva Columna --%>
                                    <th>Nombre</th>
                                    <th>Formato</th>
                                    <th>Duración</th>
                                    <th>Costo</th>
                                    <th>Stock</th>
                                    <th>Acción</th>
                                </tr>
                            </thead>
                            <tbody>
                                <% for (Producto p : catalogo) {
                                        if (p instanceof Pelicula) {
                                            boolean disponible = p.getStock() > 0;
                                %>
                                <tr>
                                    <td style="color: #6B7280; font-family: monospace;"><strong>#<%= p.getId()%></strong></td>
                                    <td><strong><%= p.getNombre()%></strong></td>
                                    <td><%= p.getFormato()%></td>
                                    <td><%= ((Pelicula) p).getDuracion()%></td>
                                    <td>$<%= String.format("%.2f", p.getCostoDia())%></td>
                                    <td><span class="<%= disponible ? "stock-ok" : "stock-none"%>"><%= p.getStock()%> uds</span></td>
                                    <td>
                                        <form action="AlquilerServlet" method="POST" style="margin: 0;">
                                            <input type="hidden" name="idProd" value="<%= p.getId()%>">
                                            <button type="submit" class="btn-rent" <%= !disponible ? "disabled style='background-color: #9CA3AF; cursor: not-allowed;'" : ""%>>
                                                <%= disponible ? "Alquilar" : "Agotado"%>
                                            </button>
                                        </form>
                                    </td>
                                </tr>
                                <% }
                                } %>
                            </tbody>
                        </table>

                        <h3 style="margin-top: 40px;">Catálogo de Videojuegos</h3>
                        <table class="styled-table">
                            <thead>
                                <tr>
                                    <th>ID</th> 
                                    <th>Nombre</th>
                                    <th>Plataforma</th>
                                    <th>Formato</th>
                                    <th>Costo</th>
                                    <th>Stock</th>
                                    <th>Acción</th>
                                </tr>
                            </thead>
                            <tbody>
                                <% for (Producto p : catalogo) {
                                        if (p instanceof Videojuego) {
                                            boolean disponible = p.getStock() > 0;
                                %>
                                <tr>
                                    <td style="color: #6B7280; font-family: monospace;"><strong>#<%= p.getId()%></strong></td>
                                    <td><strong><%= p.getNombre()%></strong></td>
                                    <td><%= ((Videojuego) p).getPlataforma()%></td>
                                    <td><%= p.getFormato()%></td>
                                    <td>$<%= String.format("%.2f", p.getCostoDia())%></td>
                                    <td><span class="<%= disponible ? "stock-ok" : "stock-none"%>"><%= p.getStock()%> uds</span></td>
                                    <td>
                                        <form action="AlquilerServlet" method="POST" style="margin: 0;">
                                            <input type="hidden" name="idProd" value="<%= p.getId()%>">
                                            <button type="submit" class="btn-rent" <%= !disponible ? "disabled style='background-color: #9CA3AF; cursor: not-allowed;'" : ""%>>
                                                <%= disponible ? "Alquilar" : "Agotado"%>
                                            </button>
                                        </form>
                                    </td>
                                </tr>
                                <% }
                                }%>
                            </tbody>
                        </table>
                    </div>
                </main>

            </div>
        </div>
    </body>
</html>