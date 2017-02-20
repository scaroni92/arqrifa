<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags/" %>

<t:masterpage titulo="Solicitudes">
    <jsp:body>
        <div class="container">
            <table class="bordered responsive-table centered white">
                <thead>
                    <tr>
                        <th>Nombre</th>
                        <th>Apellido</th>
                        <th>Fecha</th>
                        <th>Verificada</th>
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${modelo.solicitudes}" var="solicitud">
                        <tr>
                            <td>${solicitud.usuario.nombre}</td>
                            <td>${solicitud.usuario.apellido}</td>
                            <td><fmt:formatDate pattern="dd/MM/yy" value="${solicitud.fecha}" /></td>
                            <td>
                                <c:if test="${solicitud.verificada}">
                                    <i class="material-icons blue-text" style="font-size: 2em">verified_user</i>
                                </c:if>
                                
                            </td>
                            <td class="icon-btn"> 
                                <a href="solicitud?accion=detalles&ci=${solicitud.usuario.ci}"><i class="material-icons ">info_outline</i></a> 
                                <a href="solicitud?accion=confirmar&ci=${solicitud.usuario.ci}"><i class="material-icons">done</i></a>     
                                <a href="solicitud?accion=rechazar&ci=${solicitud.usuario.ci}"><i class="material-icons">clear</i></a> 
                            </td>
                        </tr>
                    </c:forEach>

                </tbody>
            </table>
        </div>
    </jsp:body>
</t:masterpage>
