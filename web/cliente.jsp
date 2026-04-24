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

    // 3. Carga de datos dinámicos
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
            <%-- MÓDULO 1 Y 2: Información Básica y Recarga --%>
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
                <aside class="sidebar">
                    <%-- MÓDULO 3: Cambio de Membresía --%>
                    <div class="card section">
                        <h3>Mejorar Membresía</h3>
                        <p class="text-small">Obtenga mayores descuentos en sus alquileres.</p>
                        <form action="CambiarMembresiaServlet" method="POST" class="form-vertical">
                            <label>Seleccione Nivel:</label>
                            <select name="nuevaMemId" required>
                                <% for (Membresia m : membresias) {%>
                                <option value="<%= m.getId()%>" 
                                        <%= m.getId() == cliente.getMembresia().getId() ? "disabled" : ""%>>
                                    <%= m.getNombre()%> (<%= (int) (m.getPorcentajeDescuento() * 100)%>% Desc. | Costo: $<%= m.getCostoCambio()%>)
                                </option>
                                <% } %>
                            </select>
                            <button type="submit" class="btn-secondary">Actualizar Nivel</button>
                        </form>
                    </div>

                    <%-- MÓDULO 5: Productos Alquilados --%>
                    <div class="card section" style="margin-top: 3%;">
                        <h3>Mis Alquileres Activos</h3>
                        <div class="rented-list">
                            <% if (misAlquileres.isEmpty()) { %>
                            <p class="empty-msg">No tiene productos alquilados actualmente.</p>
                            <% } else { %>
                            <table class="styled-table mini">
                                <thead>
                                    <tr><th>Producto</th><th>Estado</th><th>Acción</th></tr>
                                </thead>
                                <tbody>
                                    <% for (Alquiler a : misAlquileres) {
                                            String fDevId = "fDev" + a.getId();
                                    %>
                                    <tr>
                                        <td>ID: <%= a.getIdProducto()%></td>
                                        <td><span class="tag-active"><%= a.getEstado()%></span></td>
                                        <td>
                                            <form id="<%= fDevId%>" action="DevolverServlet" method="POST" style="display:none;">
                                                <input type="hidden" name="idAlquiler" value="<%= a.getId()%>">
                                            </form>
                                            <button type="submit" form="<%= fDevId%>" class="btn-delete">Devolver</button>
                                        </td>
                                    </tr>
                                    <% } %>
                                </tbody>
                            </table>
                            <% } %>
                        </div>
                    </div>
                </aside>

                <main class="content">
                    <%-- MÓDULO 4: Catálogo de Películas con Stock --%>
                    <div class="card section">
                        <h3>Catálogo de Películas</h3>
                        <table class="styled-table">
                            <thead>
                                <tr>
                                    <th>Nombre</th>
                                    <th>Formato</th>
                                    <th>Duración</th>
                                    <th>Costo/Día</th>
                                    <th>Stock</th> <%-- Nueva Columna --%>
                                    <th>Acción</th>
                                </tr>
                            </thead>
                            <tbody>
                                <% for (Producto p : catalogo) {
                                        if (p instanceof Pelicula) {
                                            String fAlqId = "fAlq" + p.getId();
                                            boolean disponible = p.getStock() > 0;
                                %>
                                <tr>
                                    <td><strong><%= p.getNombre()%></strong></td>
                                    <td><%= p.getFormato()%></td>
                                    <td><%= ((Pelicula) p).getDuracion()%></td>
                                    <td>$<%= String.format("%.2f", p.getCostoDia())%></td>
                                    <td>
                                        <span class="<%= disponible ? "stock-ok" : "stock-none"%>">
                                            <%= p.getStock()%> uds
                                        </span>
                                    </td>
                                    <td>
                                        <form id="<%= fAlqId%>" action="AlquilerServlet" method="POST" style="display:none;">
                                            <input type="hidden" name="idProd" value="<%= p.getId()%>">
                                        </form>
                                        <button type="submit" form="<%= fAlqId%>" 
                                                class="btn-rent" <%= !disponible ? "disabled style='background-color: #9CA3AF; cursor: not-allowed;'" : ""%>>
                                            <%= disponible ? "Alquilar" : "Agotado"%>
                                        </button>
                                    </td>
                                </tr>
                                <% }
                    } %>
                            </tbody>
                        </table>

                        <%-- Catálogo de Videojuegos con Stock --%>
                        <h3 style="margin-top: 40px;">Catálogo de Videojuegos</h3>
                        <table class="styled-table">
                            <thead>
                                <tr>
                                    <th>Nombre</th>
                                    <th>Plataforma</th>
                                    <th>Formato</th>
                                    <th>Costo/Día</th>
                                    <th>Stock</th> <%-- Nueva Columna --%>
                                    <th>Acción</th>
                                </tr>
                            </thead>
                            <tbody>
                                <% for (Producto p : catalogo) {
                                        if (p instanceof Videojuego) {
                                            String fAlqId = "fAlq" + p.getId();
                                            boolean disponible = p.getStock() > 0;
                                %>
                                <tr>
                                    <td><strong><%= p.getNombre()%></strong></td>
                                    <td><%= ((Videojuego) p).getPlataforma()%></td>
                                    <td><%= p.getFormato()%></td>
                                    <td>$<%= String.format("%.2f", p.getCostoDia())%></td>
                                    <td>
                                        <span class="<%= disponible ? "stock-ok" : "stock-none"%>">
                                            <%= p.getStock()%> uds
                                        </span>
                                    </td>
                                    <td>
                                        <form id="<%= fAlqId%>" action="AlquilerServlet" method="POST" style="display:none;">
                                            <input type="hidden" name="idProd" value="<%= p.getId()%>">
                                        </form>
                                        <button type="submit" form="<%= fAlqId%>" 
                                                class="btn-rent" <%= !disponible ? "disabled style='background-color: #9CA3AF; cursor: not-allowed;'" : ""%>>
                                            <%= disponible ? "Alquilar" : "Agotado"%>
                                        </button>
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