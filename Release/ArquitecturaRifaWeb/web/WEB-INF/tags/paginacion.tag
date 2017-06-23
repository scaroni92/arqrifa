<%@tag pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@attribute name="controlador"%>

<c:if test="${modelo.paginacion.cantidadPaginas > 1}">
    <ul class="pagination right">
        <li class="${modelo.paginacion.primera? 'disabled' : 'waves-effect'}">
            <a href="${controlador}?pg=${modelo.paginacion.anterior}"><i class="material-icons">chevron_left</i></a>
        </li>
        <c:forEach var = "i" begin = "1" end = "${modelo.paginacion.cantidadPaginas}">
            <li class="waves-effect pg-index ${i eq modelo.paginacion.paginaSolicitada? 'active' : ''} ">
                <a href="${controlador}?pg=${i}">${i}</a>
            </li>
        </c:forEach>
        <li class="${modelo.paginacion.ultima? 'disabled' : 'waves-effect'}">
            <a href="${controlador}?pg=${modelo.paginacion.siguiente}"><i class="material-icons">chevron_right</i></a>
        </li>
    </ul>
</c:if>

<style type="text/css">
    .pagination li.disabled a{
        pointer-events: none;
    }
</style>