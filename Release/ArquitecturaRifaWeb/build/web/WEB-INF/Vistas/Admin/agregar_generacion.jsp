<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Arquitectura Rifa | Agregar Generación</title>
    </head>
    <body>
        <h1>Agregar Generación</h1>
        <form action="Admin" method="post">
            Año: <input type="number" name="anio" min="2009" required>
            <button type="submit" name="accion" value="agregar_generacion">Agregar</button>
        </form>
        <table>
            <tr>
                <th>Generaciones</th>
            </tr>

            <c:forEach items="${modelo.generaciones}" var="generacion">
                <tr>
                    <td style="text-align: center;">${generacion.id}</td>
                </tr>
            </c:forEach>
        </table>
        <p>${modelo.mensaje}</p>
    </body>
</html>
