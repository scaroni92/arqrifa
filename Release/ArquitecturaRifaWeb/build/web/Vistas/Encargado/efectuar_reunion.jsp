<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Efectuar reunión</h1>
        <form action="Reuniones"  method="post">
            Id:<br> <input type="text" name="id" value="${modelo.id}" readonly><br>
            Título:<br> <input type="text" name="titulo" value="${modelo.titulo}"readonly><br>
            Descripción:<br> <textarea name="descripcion"readonly>${modelo.descripcion}</textarea><br>
            Fecha:<br> <input type="date" name="fecha" value="${modelo.fecha}"readonly><br>
            Hora:<br> <input type="time" name="hora" value="${modelo.hora}"readonly><br>
            Obligatoria:<br> <input type="checkbox" name="obligatoria" value="${modelo.obligatoria}"disabled=""><br>
            Generación: <br><input type="text" name="generacion" value="${modelo.generacion}"readonly><br>
            Estado:<br> <input type="text" name="estado" value="${modelo.estado}"readonly><br>
            Lugar:<br> <input type="text" name="lugar" value="${modelo.lugar}"readonly><br>

            <c:if test="${modelo.estado == 'Pendiente'}">
                <input type="submit" name="accion" value="Iniciar">
            </c:if>
            <c:if test="${modelo.estado == 'Iniciada'}">
                Observaciones:<br> <textarea type="text" name="observaciones">${modelo.observaciones}</textarea><br>
                <t:agregar_valores_textarea name="resoluciones" valores="${modelo.resoluciones}" /><br>
                <input type="submit" name="accion" value="Finalizar">
            </c:if>
        </form>
        <p>${modelo.mensaje}</p>
    </body>
</html>

