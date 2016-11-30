<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Arquitectura Rifa | Registro</title>
    </head>
    <body>
        <h1>Registro</h1>
        <form action="Usuarios" method="post">
            Cédula: <input type="number" name="ci" value="${modelo.ci}" autofocus required><br>
            Nombre: <input type="text" name="nombre" value="${modelo.nombre}" required><br>
            Apellido: <input type="text" name="apellido" value="${modelo.apellido}" required><br>
            Contraseña: <input type="password" name="contrasena" value="${modelo.contrasena}" required><br>
            Email: <input type="email" name="email" value="${modelo.email}" pattern="\w+([-+.']\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*" title="ejemplo@ejemplo.com" required><br>
            Generación: 
            <select name="generacion">
                <c:forEach items="${modelo.generaciones}" var="generacion">
                    <option>${generacion.id}</option>
                </c:forEach>
            </select><br>
            <input type="submit" name="accion" value="Registrar">
        </form>
        <p>${modelo.mensaje}</p>
    </body>
</html>
