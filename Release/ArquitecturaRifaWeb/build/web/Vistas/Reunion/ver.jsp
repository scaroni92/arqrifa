<!-- Esta página fue creada provisoriamente para implementar el CU Iniciar Reunión-->

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>${modelo.titulo}</h1>
        <h2>${modelo.descripcion}</h2>
        <h3>${modelo.resoluciones}</h3>
        Fecha: ${modelo.fecha}<br>
        Hora: ${modelo.hora}<br>
        Obligatoria: ${modelo.obligatoria}<br>
        Estado: ${modelo.estado}<br>
        Lugar: ${modelo.lugar}<br>


        <form action="Reuniones" method="post">
            <!-- Estos inputs son para mantener el estado de la reunión-->
            <!-- Reemplzar en CSS hidden por display:none-->
            <input type="text" name="id" value="${modelo.id}" hidden><br>
            <input type="text" name="titulo" value="${modelo.titulo}"hidden><br>
            <textarea name="descripcion"hidden>${modelo.descripcion}</textarea><br>
            <textarea name="resoluciones" hidden>${modelo.resoluciones}</textarea><br>
            <input type="date" name="fecha" value="${modelo.fecha}"hidden><br>
            <input type="time" name="hora" value="${modelo.hora}"hidden><br>
            <input type="checkbox" name="obligatoria" value="${modelo.obligatoria}"hidden><br>
            <input type="text" name="generacion" value="${modelo.generacion}"hidden><br>
            <input type="text" name="estado" value="${modelo.estado}"hidden><br>
            <input type="text" name="lugar" value="${modelo.lugar}"hidden><br> 
            <c:if test="${usuario.rol == 'Encargado' and modelo.habilitarInicio}">
                <button type="submit" name="accion" value="iniciar_link">Iniciar</button>
                <input type="text" name="id" value="${modelo.id}" hidden>
            </c:if>
        </form>

        <p>${modelo.mensaje}</p>
    </body>
</html>
