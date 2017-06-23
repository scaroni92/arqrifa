<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags/" %>



<t:masterpage titulo="Asistencias">
    <jsp:body>
        <div class="container">
            <div class=" center">
                <form action="asistencias">
                    <div class="input-field inline">
                        <input id="ci" name="ci" type="number">
                        <label for="ci">Cédula</label>
                    </div>
                    <button class="btn-flat waves-effect waves-light" type="submit" name="accion" value="buscar">Buscar <i class="material-icons left">search</i> </button>
                </form>
            </div>
            <table class="bordered responsive-table centered white highlight">
                <thead class="cyan white-text">
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
                                <a href="asistencias?accion=marcar_asistencia&ci=${asistencia.estudiante.ci}&id=${reunionActiva.id}"><i class="material-icons ">add</i></a> 
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </jsp:body>
</t:masterpage>