<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
        <form action="Reuniones" method="post">
            <!-- para todos los atributos-->

            Hora programada: <input type="text" value="${modelo.hora}" >
            Resoluciones: <input type="text" name="resoluciones" >
            Estado: <input type="text" value="${modelo.estado}">
            
            <c:if test="${modelo.estado == 'Pendiente'}">
                <input type="submit" name="accion" value="Iniciar">
            </c:if>
            <c:if test="${modelo.estado == 'Iniciada'}">
                <input type="submit" name="accion" value="Finalizar">
            </c:if>
        </form>
    </body>
</html>
