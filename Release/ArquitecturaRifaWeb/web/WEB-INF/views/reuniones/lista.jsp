<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags/" %>



<t:masterpage titulo="Lista">
    <jsp:body>
        <div class="container">
            <table class="bordered responsive-table centered white">
                <thead>
                    <tr>
                        <th>Cédula</th>
                        <th>Nombre</th>
                        <th>Apellido</th>
                        <th>Estado</th>
                        <th>Acciones</th>
                        
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="asistencia" items="${modelo.asistencias}">
                        <tr>
                            <td>${asistencia.estudiante.ci}</td>
                            <td>${asistencia.estudiante.nombre}</td>
                            <td>${asistencia.estudiante.apellido}</td>
                            <td>${asistencia.estado}</td>
                            <td class="icon-btn">
                                <a href="usuario?accion=detalles&ci=${asistencia.estudiante.ci}"><i class="material-icons ">content_paste</i></a> 
                                <a href="panel?accion=marcar-asistencia&ci=${asistencia.estudiante.ci}&id=${modelo.reunion.id}"><i class="material-icons ">add</i></a> 
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </jsp:body>
</t:masterpage>