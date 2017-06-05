<%@tag pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@attribute name="controlador"%>

<ul class="pagination right">
    <li class="${modelo.paginacion.paginaSolicitada == 1? 'disabled' : 'waves-effect'}">
        <a href="${controlador}?pg=${modelo.paginacion.paginaSolicitada - 1}"><i class="material-icons">chevron_left</i></a>
    </li>
    <c:forEach var = "i" begin = "1" end = "${modelo.paginacion.cantidadPaginas}">
        <li class="waves-effect pg-index ${i eq modelo.paginacion.paginaSolicitada? 'active' : ''} ">
            <a href="${controlador}?pg=${i}">${i}</a>
        </li>
    </c:forEach>
    <li class="${modelo.paginacion.paginaSolicitada >= modelo.paginacion.cantidadPaginas? 'disabled' : 'waves-effect'}">
        <a href="${controlador}?pg=${modelo.paginacion.paginaSolicitada + 1}"><i class="material-icons">chevron_right</i></a>
    </li>
</ul>
    
<style type="text/css">
    /* TODO: Mover estilo a styles.css */
    .pagination li.disabled a{
        pointer-events: none;
    }
</style>