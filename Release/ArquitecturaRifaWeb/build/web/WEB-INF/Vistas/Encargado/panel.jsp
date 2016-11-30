<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Arquitectura Rifa | Panel</title>
    </head>
    <body>
        <h1>Panel</h1>
        <form action="Reuniones"  method="post">  
            Id: ${modelo.id}<br>
            Título: ${modelo.titulo}<br>
            Descripción: ${modelo.descripcion}<br>
            Fecha: ${modelo.fecha}<br>
            Hora: ${modelo.hora}<br>           
            Estado: ${modelo.estado}<br><br>

            <c:if test="${modelo.estado == 'Pendiente'}">
                <input type="submit" name="accion" value="Iniciar">
            </c:if>
            <c:if test="${modelo.estado == 'Iniciada'}">
                Observaciones:<br> <textarea type="text" name="observaciones">${modelo.observaciones}</textarea><br>
                <t:cargar_valores_textarea name="resoluciones" valores="${modelo.resoluciones}" /><br>
                <input type="submit" name="accion" value="Finalizar">
            </c:if>
                
            <!-- Para evitar el uso de este tag mostrar la información en inputs-->
            <t:info_reunion/>
        </form>
        <p>${modelo.mensaje}</p>
        <a href="Reuniones?accion=ver&id=${modelo.id}">Ver detalles</a>
    </body>
</html>

