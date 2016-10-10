<%-- 
    Document   : verificar
    Created on : 09-oct-2016, 18:00:37
    Author     : Ale
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <c:choose>
            <c:when test="${verificada}">
                <h1>Éxito!</h1>
                <h2>Has verificado correctamente tu dirección de correo electrónico.</h2>
                <a href="index.jsp">INICIA SESIÓN</a>
            </c:when>
            <c:otherwise>
                <h1>No se pudo verificar la solicitud</h1>
                <h2>${modelo.mensaje}</h2>
            </c:otherwise>
        </c:choose>
    </body>
</html>
