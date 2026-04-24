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
                            <tr>
                                <th>Nombre</th>
                                <th>Formato</th>
                                <th>Duracion</th>
                                <th>Costo/Dia</th>
                                <th>Stock</th>
                                <th>Acción</th>
                            </tr>
                        </thead>
                        <tbody>
                            <% for (Producto p : inventario) {
                                    if (p instanceof Pelicula) {
                                        String fId = "fPeli" + p.getId(); // ID único para el formulario de la fila
                            %>
                            <tr>
                                <td><%= p.getNombre()%></td>
                                <td><%= p.getFormato()%></td>
                                <td><%= ((Pelicula) p).getDuracion()%></td>
                                <td>
                                    <%-- Vinculamos el input al formulario mediante el ID 'fId' --%>
                                    <input type="number" step="0.01" name="costoDia" form="<%= fId%>"
                                           value="<%= p.getCostoDia()%>" class="input-table-edit" required>
                                </td>
                                <td>
                                    <input type="number" name="stock" form="<%= fId%>"
                                           value="<%= p.getStock()%>" class="input-table-edit" required>
                                </td>
                                <td>
                                    <%-- El formulario se define oculto, no estorba el diseño de la tabla --%>
                                    <form id="<%= fId%>" action="ActualizarProductoServlet" method="POST" style="display:none;">
                                        <input type="hidden" name="id" value="<%= p.getId()%>">
                                    </form>
                                    <button type="submit" form="<%= fId%>" class="btn-table-save">Guardar</button>
                                </td>
                            </tr>
                            <% }
                                } %>
                        </tbody>
                    </table>

                    <h3 style="margin-top: 30px;">Videojuegos en Inventario</h3>
                    <table class="styled-table">
                        <thead>
                            <tr>
                                <th>Nombre</th>
                                <th>Plataforma</th>
                                <th>Formato</th>
                                <th>Costo/Dia</th>
                                <th>Stock</th>
                                <th>Acción</th>
                            </tr>
                        </thead>
                        <tbody>
                            <% for (Producto p : inventario) {
                                    if (p instanceof Videojuego) {
                                        String fId = "fJuego" + p.getId();
                            %>
                            <tr>
                                <td><%= p.getNombre()%></td>
                                <td><%= ((Videojuego) p).getPlataforma()%></td>
                                <td><%= p.getFormato()%></td>
                                <td>
                                    <input type="number" step="0.01" name="costoDia" form="<%= fId%>"
                                           value="<%= p.getCostoDia()%>" class="input-table-edit" required>
                                </td>
                                <td>
                                    <input type="number" name="stock" form="<%= fId%>"
                                           value="<%= p.getStock()%>" class="input-table-edit" required>
                                </td>
                                <td>
                                    <form id="<%= fId%>" action="ActualizarProductoServlet" method="POST" style="display:none;">
                                        <input type="hidden" name="id" value="<%= p.getId()%>">
                                    </form>
                                    <button type="submit" form="<%= fId%>" class="btn-table-save">Guardar</button>
                                </td>
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

            <%-- 
                Módulo Centralizado de Gestión de Membresías
            --%>
            <section class="card section" id="modulo-membresias" style="margin-top: 40px;">
                <header class="section-header">
                    <h3>Módulo de Gestión de Membresías</h3>
                    <p>Administre los niveles de beneficios, descuentos y costos de afiliación para sus clientes.</p>
                </header>

                <div class="admin-management-grid" style="display: grid; grid-template-columns: 1fr 2fr; gap: 30px; margin-top: 20px;">

                    <%-- Formulario de Registro de Nueva Membresía --%>
                    <div class="management-form" style="padding-right: 20px; border-right: 1px solid #D1D5DB;">
                        <h4>Añadir Nueva Membresía</h4>
                        <form action="GestionMembresiaServlet" method="POST">
                            <input type="hidden" name="accion" value="crear">

                            <div class="form-group">
                                <label for="mem_nombre">Nombre del Nivel</label>
                                <input type="text" id="mem_nombre" name="nombre" 
                                       placeholder="Ej: Diamante" required>
                            </div>

                            <div class="form-group">
                                <label for="mem_desc">Descuento (Decimal)</label>
                                <input type="number" step="0.01" min="0" max="1" id="mem_desc" name="descuento" 
                                       placeholder="Ej: 0.25 para 25%" required>
                            </div>

                            <div class="form-group">
                                <label for="mem_costo">Costo de Cambio ($)</label>
                                <input type="number" step="0.01" min="0" id="mem_costo" name="costo" 
                                       placeholder="Ej: 50000" required>
                            </div>

                            <button type="submit" class="btn-primary" style="margin-top: 15px;">
                                Guardar Membresía
                            </button>
                        </form>
                    </div>

                    <%-- Tabla de Gestión (Edición y Eliminación) --%>
                    <div class="management-list">
                        <h4>Membresías Activas</h4>
                        <table class="styled-table">
                            <thead>
                                <tr>
                                    <th>Nivel</th>
                                    <th>Descuento</th>
                                    <th>Costo de Cambio</th>
                                    <th colspan="2">Acciones</th>
                                </tr>
                            </thead>
                            <tbody>
                                <%
                                    List<Membresia> listaMembresias = memRepo.getAll();
                                    for (Membresia m : listaMembresias) {
                                        String fMemId = "fMem" + m.getId();
                                %>
                                <tr>
                                    <td><strong><%= m.getNombre()%></strong></td>

                                    <%-- Regla de Negocio: La membresía 'Normal' es intocable --%>
                                    <% if (m.getNombre().equalsIgnoreCase("Normal") || m.getId() == 1) {%>
                                    <td><%= m.getPorcentajeDescuento() * 100%>%</td>
                                    <td>$<%= m.getCostoCambio()%></td>
                                    <td colspan="2"><em style="color: #6B7280; font-size: 0.85em;">Por Defecto (Protegida)</em></td>
                                    <% } else {%>
                                    <td>
                                        <input type="number" step="0.01" min="0" max="1" name="descuento" form="<%= fMemId%>"
                                               value="<%= m.getPorcentajeDescuento()%>" class="input-table-edit" required>
                                    </td>
                                    <td>
                                        <input type="number" step="0.01" min="0" name="costo" form="<%= fMemId%>"
                                               value="<%= m.getCostoCambio()%>" class="input-table-edit" required>
                                    </td>

                                    <%-- Acción 1: Actualizar --%>
                                    <td style="padding: 5px;">
                                        <form id="<%= fMemId%>" action="GestionMembresiaServlet" method="POST" style="display:none;">
                                            <input type="hidden" name="accion" value="actualizar">
                                            <input type="hidden" name="id" value="<%= m.getId()%>">
                                        </form>
                                        <button type="submit" form="<%= fMemId%>" class="btn-table-save">Actualizar</button>
                                    </td>

                                    <%-- Acción 2: Eliminar --%>
                                    <td style="padding: 5px;">
                                        <form action="GestionMembresiaServlet" method="POST" style="margin: 0;">
                                            <input type="hidden" name="accion" value="eliminar">
                                            <input type="hidden" name="id" value="<%= m.getId()%>">
                                            <button type="submit" class="btn-delete" style="padding: 4px 8px; font-size: 11px;"
                                                    onclick="return confirm('¿Eliminar membresía <%= m.getNombre()%>?')">
                                                X
                                            </button>
                                        </form>
                                    </td>
                                    <% } %>
                                </tr>
                                <% } %>
                            </tbody>
                        </table>
                    </div>
                </div>
            </section>

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
                            <td>$<%= a.getCosto()%></td>
                            <td class="status-<%= a.getEstado().toLowerCase()%>"><%= a.getEstado()%></td>
                        </tr>
                        <% }%>
                    </tbody>
                </table>
            </section>
        </div>
    </body>
</html>