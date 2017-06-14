<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags/" %>

<t:masterpage titulo="Usuarios">
    <jsp:body>
        <div class="container">
            <div class="row">
                <div class="col s12">
                    <div class=" center">
                        <form action="usuarios">
                            <div class="input-field inline">
                                <input id="ci" name="ci" type="number" required>
                                <label for="ci">Cédula</label>
                            </div>
                            <button class="btn-flat waves-effect waves-light" type="submit" name="accion" value="buscar">Buscar <i class="material-icons left">search</i> </button>
                        </form>
                    </div>
                </div>
            </div>
            <table class="bordered responsive-table centered white">
                <thead>
                    <tr>
                        <th>Cédula</th>
                        <th>Nombre</th>
                        <th>Apellido</th>
                        <th>Generación</th>
                        <th>Rol</th>
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="user" items="${modelo.paginacion.elementos}">
                        <tr>
                            <td>${user.ci}</td>
                            <td>${user.nombre}</td>
                            <td>${user.apellido}</td>
                            <td>${user.generacion}</td>
                            <td>${user.rol}</td>
                            <td class="icon-btn">
                                <a href="usuarios?accion=detalles&ci=${user.ci}"><i class="material-icons ">info_outline</i></a> 
                                <c:if test="${usuario.rol eq 'Admin'}">
                                    <a href="usuario?accion=modificar&ci=${user.ci}"><i class="material-icons ">edit</i></a>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <t:paginacion controlador="usuarios" />
        </div>
        <c:if test="${usuario.rol eq 'Admin'}"> 
            <div class="fixed-action-btn">
                <a href="usuario?accion=agregar" class="btn-floating btn-large red"> <i class="large material-icons">add</i></a>
            </div>    
        </c:if>
    </jsp:body>
</t:masterpage>