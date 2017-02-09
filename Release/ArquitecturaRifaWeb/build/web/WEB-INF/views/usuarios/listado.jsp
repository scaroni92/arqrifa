<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags/" %>

<t:masterpage titulo="${usuario.rol eq 'Admin'? 'Usuarios' : 'Estudiantes' }">
    <jsp:body>
        <div class="container">
            <div class="row">
                <div class="col s12">
                    <div class=" center">
                        <form action="admin">
                            <div class="input-field inline">
                                <input id="ci" name="ci" type="number">
                                <label for="ci">Cédula</label>
                            </div>
                            <button class="btn-flat waves-effect waves-light" type="submit" name="accion" value="buscar-usuario">Buscar <i class="material-icons left">search</i> </button>
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
                    <c:forEach var="user" items="${modelo.usuarios}">
                        <tr>
                            <td>${user.ci}</td>
                            <td>${user.nombre}</td>
                            <td>${user.apellido}</td>
                            <td>${user.generacion}</td>
                            <td>${user.rol}</td>
                            <td class="icon-btn">
                                <a href="usuario?accion=detalles&ci=${user.ci}"><i class="material-icons ">info_outline</i></a> 
                                <c:if test="${usuario.rol eq 'Admin'}">
                                    <a href="admin?accion=modificar-usuario&ci=${user.ci}"><i class="material-icons ">edit</i></a>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <ul class="pagination right">
                <li class="disabled"><a href="#!"><i class="material-icons">chevron_left</i></a></li>
                <li class="active "><a href="#!">1</a></li>
                <li class="waves-effect"><a href="#!">2</a></li>
                <li class="waves-effect"><a href="#!">3</a></li>
                <li class="waves-effect"><a href="#!">4</a></li>
                <li class="waves-effect"><a href="#!">5</a></li>
                <li class="waves-effect"><a href="#!"><i class="material-icons">chevron_right</i></a></li>
            </ul>
        </div>
        <c:if test="${usuario.rol eq 'Admin'}"> 
            <div class="fixed-action-btn">
                <a href="admin?accion=agregar-usuario" class="btn-floating btn-large red"> <i class="large material-icons">add</i></a>
            </div>    
        </c:if>
    </jsp:body>
</t:masterpage>