<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <title>JSP Page</title>
        <style>
            table { 
                border-spacing: 0;
                border-collapse: collapse;
            }

            table td {
                border: 1px solid;
                padding: 10px;
                margin:0;
            }
        </style>
    </head>

    <body>
        <h1>Calendario</h1>

        <table>
            <c:forEach var="reunion" items="${modelo.reuniones}">
                <tr>
                    <td>
                        <h3><fmt:formatDate pattern="dd/MM/yy" value="${reunion.fecha}" /></h3>
                        <p><fmt:formatDate pattern="hh:mm" value="${reunion.fecha}" /></p>
                    </td>
                    <td>${reunion.descripcion}</td>
                    <td>
                        <a href="Reuniones?accion=ver&id=${reunion.id}">Detalles</a>
                        <c:if test="${reunion.estado != 'Iniciada'}">
                            <a href="Reuniones?accion=eliminar&id=${reunion.id}">Eliminar</a>    
                        </c:if>
                        <c:if test="${reunion.estado eq 'Pendiente'}">
                            <a href="Reuniones?accion=modificar&id=${reunion.id}">Modificar</a>
                        </c:if>
                    </td>
                </tr>    
            </c:forEach>

        </table>
        <p>${modelo.mensaje}</p>
    </body>
</html>
