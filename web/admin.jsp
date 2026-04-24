<%-- 
    Document   : admin.jsp
    Author     : Juan David Diaz Perez
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="co.edu.udistrital.model.logic.*"%>
<%@page import="co.edu.udistrital.model.services.*"%>
<%@page import="java.util.List"%>
<%

    Perfil usuarioActual = (Perfil) session.getAttribute("usuarioLogueado");
    if (usuarioActual == null || !(usuarioActual instanceof Administrador)) {
        response.sendRedirect("index.jsp");
        return;
    }

    ProductoRepository prodRepo = new ProductoRepository();
    MembresiaRepository memRepo = new MembresiaRepository();
    AlquilerRepository alqRepo = new AlquilerRepository();
    UsuarioRepository userRepo = new UsuarioRepository();

    List<Producto> inventario = prodRepo.getAll();
%>
<!DOCTYPE html>
<html>
    <head>
        <title>Panel Administrativo - Sistema Alquileres</title>
        <link rel="stylesheet" href="CSS/admin.css">
        <script src="JS/admin.js"></script>
    </head>
    <body>
        <div class="container">
            <header class="header-admin">
                <h2>Bienvenido, <%= usuarioActual.getUsuario()%></h2>
                <a href="LogoutServlet" class="btn-logout">Cerrar Sesion</a>
            </header>

            <% if (usuarioActual.getUsuario().equals("admin")) { %>
            <section class="card section" id="modulo-administradores" style="margin-top: 40px;">
                <header class="section-header">
                    <h3>Módulo de Gestión de Personal Administrativo</h3>
                    <p>Desde aquí puede dar de alta nuevos perfiles o revocar accesos al sistema.</p>
                </header>

                <div class="admin-management-grid" style="display: grid; grid-template-columns: 1fr 1.5fr; gap: 30px; margin-top: 20px;">

                    <%-- Formulario de Registro --%>
                    <div class="management-form" style="padding-right: 20px; border-right: 1px solid #D1D5DB;">
                        <h4>Registrar Nuevo Administrador</h4>
                        <form action="RegistrarAdminServlet" method="POST">
                            <input type="hidden" name="accion" value="crear">

                            <div class="form-group">
                                <label for="reg_user">Usuario</label>
                                <input type="text" id="reg_user" name="usuario" 
                                       placeholder="Nombre de usuario" required minlength="4">
                            </div>

                            <div class="form-group">
                                <label for="reg_pass">Contraseña</label>
                                <input type="password" id="reg_pass" name="contrasena" 
                                       placeholder="Contraseña segura" required minlength="6">
                            </div>

                            <button type="submit" class="btn-primary" style="margin-top: 15px;">
                                Crear Perfil Administrativo
                            </button>
                        </form>
                    </div>

                    <%-- Lista de Control y Eliminación --%>
                    <div class="management-list">
                        <h4>Administradores con Acceso</h4>
                        <table class="styled-table">
                            <thead>
                                <tr>
                                    <th>Identificador</th>
                                    <th>Rol</th>
                                    <th>Acciones</th>
                                </tr>
                            </thead>
                            <tbody>
                                <%
                                    List<Perfil> listaAdmins = userRepo.getAllAdmins();
                                    for (Perfil p : listaAdmins) {
                                %>
                                <tr>
                                    <td><strong><%= p.getUsuario()%></strong></td>
                                    <td>
                                        <span class="badge <%= p.getUsuario().equals("admin") ? "badge-gold" : "badge-blue"%>">
                                            <%= p.getUsuario().equals("admin") ? "SuperAdmin" : "Admin"%>
                                        </span>
                                    </td>
                                    <td>
                                        <% if (!p.getUsuario().equals("admin")) {%>
                                        <form action="EliminarAdminServlet" method="POST" style="margin: 0;">W
                                            <input type="hidden" name="usuario" value="<%= p.getUsuario()%>">
                                            <button type="submit" class="btn-delete" 
                                                    onclick="return confirm('¿Está seguro de eliminar a <%= p.getUsuario()%>?')">
                                                Revocar Acceso
                                            </button>
                                        </form>
                                        <% } else { %>
                                        <em style="color: #6B7280; font-size: 0.85em;">Protegido</em>
                                        <% } %>
                                    </td>
                                </tr>
                                <% } %>
                            </tbody>
                        </table>
                    </div>
                </div>
            </section>
            <% } %>

            <div class="grid-admin">
                <%-- Inventario Separado por Tablas --%>
                <div class="card inventario">
                    <h3>Peliculas en Inventario</h3>
                    <table class="styled-table">
                        <thead>
                            <tr><th>Nombre</th><th>Formato</th><th>Duracion</th><th>Costo/Dia</th><th>Stock</th></tr>
                        </thead>
                        <tbody>
                            <% for (Producto p : inventario) {
                                    if (p instanceof Pelicula) {%>
                            <tr>
                                <td><%= p.getNombre()%></td>
                                <td><%= p.getFormato()%></td>
                                <td><%= ((Pelicula) p).getDuracion()%></td>
                                <td>$<%= p.getCostoDia()%></td>
                                <td><%= p.getStock()%></td>
                            </tr>
                            <% }
                                } %>
                        </tbody>
                    </table>

                    <h3 style="margin-top: 30px;">Videojuegos en Inventario</h3>
                    <table class="styled-table">
                        <thead>
                            <tr><th>Nombre</th><th>Plataforma</th><th>Formato</th><th>Costo/Dia</th><th>Stock</th></tr>
                        </thead>
                        <tbody>
                            <% for (Producto p : inventario) {
                                    if (p instanceof Videojuego) {%>
                            <tr>
                                <td><%= p.getNombre()%></td>
                                <td><%= ((Videojuego) p).getPlataforma()%></td>
                                <td><%= p.getFormato()%></td>
                                <td>$<%= p.getCostoDia()%></td>
                                <td><%= p.getStock()%></td>
                            </tr>
                            <% }
                                } %>
                        </tbody>
                    </table>
                </div>

                <%-- Registro con JS corregido --%>
                <div class="card registro">
                    <h3>Añadir Producto</h3>
                    <form action="RegistrarProductoServlet" method="POST">
                        <label>Tipo</label>
                        <select id="tipo" name="tipo" onchange="cambiarCampos()" required>
                            <option value="PELICULA">Pelicula</option>
                            <option value="JUEGO">Videojuego</option>
                        </select>

                        <input type="text" name="nombre" placeholder="Nombre" required>
                        <input type="number" step="0.01" name="costoDia" placeholder="Costo por dia" required>
                        <input type="number" name="stock" placeholder="Stock Inicial" required>

                        <%-- Campos DinÃ¡micos --%>
                        <div id="contenedorDinamico">
                            <label id="label1">Formato</label>
                            <input type="text" id="extra1" name="formato" placeholder="DVD/BluRay" required>
                            <label id="label2">Duracion</label>
                            <input type="text" id="extra2" name="duracion" placeholder="120 min" required>
                        </div>

                        <button type="submit" class="btn-primary">Guardar Producto</button>
                    </form>
                </div>
            </div>

            <%-- Historial de Alquileres --%>
            <section class="card section" style="margin-top: 40px;">
                <h3>Historial General de Alquileres</h3>
                <table class="styled-table">
                    <thead>
                        <tr><th>Cliente</th><th>Producto</th><th>Fecha Inicio</th><th>Costo</th><th>Estado</th></tr>
                    </thead>
                    <tbody>
                        <% for (Alquiler a : alqRepo.getAll()) {%>
                        <tr>
                            <td><%= a.getIdCliente()%></td>
                            <td><%= a.getIdProducto()%></td>
                            <td><%= a.getFechaAlquiler()%></td>
                            <td>$<%= a.getCostoTotal()%></td>
                            <td class="status-<%= a.getEstado().toLowerCase()%>"><%= a.getEstado()%></td>
                        </tr>
                        <% }%>
                    </tbody>
                </table>
            </section>
        </div>
    </body>
</html>