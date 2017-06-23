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
                    <c:forEach var="reunion" items="${modelo.paginacion.elementos}">
                        <tr>
                            <td>${reunion.id}</td>
                            <td>${reunion.titulo}</td>
                            <td><fmt:formatDate pattern="dd/MM/yy hh:mm" value="${reunion.fecha}" /></td>
                            <td>${reunion.estado}</td>
                            <td>${reunion.lugar}</td>
                            <td class="icon-btn">
                                <a href="reuniones?accion=detalles&id=${reunion.id}"><i class="material-icons ">info_outline</i></a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <t:paginacion controlador="reuniones" />
        </div>
    </jsp:body>
</t:masterpage>