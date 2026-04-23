<%-- 
    Document   : registro.jsp
    Author     : Juan David DÃ­az PÃ©rez
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="CSS/style.css">
        <title>Registro de Cliente - Alquileres</title>
    </head>
    <body>
        <div class="container">
            <header>
                <h2>Unete a nosotros</h2>
                <p>Crea tu cuenta de cliente y empieza a alquilar</p>
            </header>

            <form action="RegistroServlet" method="POST" class="card">
                <div class="form-group">
                    <label for="usuario">Elige un nombre de usuario</label>
                    <input type="text" id="usuario" name="usuario" 
                           minlength="4" required />
                </div>

                <div class="form-group">
                    <label for="password">Contraseña segura</label>
                    <input type="password" id="contrasena" name="contrasena" 
                           minlength="6" required />
                </div>

                <%-- Campo oculto para asegurar que el registro sea siempre CLIENTE --%>
                <input type="hidden" name="rol" value="cliente">

                <button type="submit" class="btn-primary">Crear Cuenta</button>
            </form>

            <%-- Mensajes de Respuesta --%>
            <% if (request.getParameter("success") != null) { %>
            <div class="success-message">
                ¡Registro exitoso! Ya puedes <a href="index.jsp">iniciar sesion</a>.
            </div>
            <% } else if (request.getParameter("error") != null) { %>
            <div class="error-message">
                El nombre de usuario ya existe o los datos son invalidos.
            </div>
            <% }%>

            <p class="redirect">
                ¿Ya tienes una cuenta? <a href="index.jsp">Inicia sesion aqui</a>
            </p>
        </div>
    </body>
</html>