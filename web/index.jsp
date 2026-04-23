<%-- 
    Document   : index.jsp
    Author     : Juan David DÃ­az PÃ©rez
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="CSS/style.css">
        <title>Ingreso al Sistema - Alquileres</title>
    </head>
    <body>
        <div class="container">
            <header>
                <h2>Bienvenido al Sistema</h2>
                <p>Ingrese sus credenciales para continuar</p>
            </header>

            <form action="LoginServlet" method="POST" class="card">
                <div class="form-group">
                    <label for="usuario">Nombre de Usuario</label>
                    <input type="text" id="usuario" name="usuario" 
                           placeholder="Ej: fulano123" required />
                </div>

                <div class="form-group">
                    <label for="password">Contraseña</label>
                    <input type="password" id="contrasena" name="contrasena" 
                           required />
                </div>

                <button type="submit" class="btn-primary">Ingresar</button>
            </form>

            <%-- Manejo de Errores dinÃ¡mico --%>
            <% if (request.getParameter("error") != null) { %>
            <div class="error-message">
                Credenciales incorrectas. Por favor, verifique e intente de nuevo.
            </div>
            <% }%>

            <p class="redirect">
                ¿No tienes cuenta? <a href="registro.jsp">Registrate como Cliente aqui</a>
            </p>
        </div>
    </body>
</html>