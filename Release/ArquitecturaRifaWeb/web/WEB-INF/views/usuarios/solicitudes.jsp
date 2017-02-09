<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags/" %>

<t:masterpage titulo="Solicitudes">
    <jsp:body>
        <div class="container">
            <table class="bordered responsive-table centered white">
                <thead>
                    <tr>
                        <th>Cédula</th>
                        <th>Nombre</th>
                        <th>Apellido</th>
                        <th>Verificada</th>
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${modelo.solicitudes}" var="solicitud">
                        <tr>
                            <td>${solicitud.usuario.ci}</td>
                            <td>${solicitud.usuario.nombre}</td>
                            <td>${solicitud.usuario.apellido}</td>
                            <td>
                                <div class="switch">
                                    <label>No<input type="checkbox" ${solicitud.verificada ? 'checked': ''} onclick="return false;" /> <span class="lever"></span>Si</label>
                                </div>
                            </td>
                            <td class="icon-btn"> 
                                <c:if test="${solicitud.verificada}">
                                    <a href="solicitud?accion=confirmar&ci=${solicitud.usuario.ci}"><i class="material-icons">done</i></a>     
                                </c:if>
                                <a href="solicitud?accion=rechazar&ci=${solicitud.usuario.ci}"><i class="material-icons">clear</i></a> 
                            </td>
                        </tr>
                    </c:forEach>

                </tbody>
            </table>
        </div>
    </jsp:body>
</t:masterpage>
