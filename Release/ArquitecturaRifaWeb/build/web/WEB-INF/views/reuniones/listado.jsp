<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags/" %>


<t:masterpage titulo="Reuniones">
    <jsp:body>
        <div class="container">

            <table class="bordered responsive-table centered white">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Título</th>
                        <th>Fecha/Hora</th>
                        <th>Estado</th>
                        <th>Lugar</th>
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="reunion" items="${modelo.reuniones}">
                        <tr>
                            <td>${reunion.id}</td>
                            <td>${reunion.titulo}</td>
                            <td>${reunion.fecha}</td>
                            <td>${reunion.estado}</td>
                            <td>${reunion.lugar}</td>
                            <td class="icon-btn">
                                <a href="reunion?accion=detalles&id=${reunion.id}"><i class="material-icons ">info_outline</i></a>
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