<%@taglib prefix="t" tagdir="/WEB-INF/tags/" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<t:masterpage titulo="Detalles">
    <jsp:body>
        <div class="container">
            <div class="card-panel">
                <span class="chip right">${modelo.reunion.estado}</span>
                <h4>${modelo.reunion.titulo}</h4>
                <p class="flow-text">${modelo.reunion.descripcion}</p>

                <div class="row center grey-text text-darken-2" >
                    <div class="col s3"><i class="material-icons tiny">event_seat</i> ${modelo.reunion.obligatoria? "Obligatoria" : "Opcional"}</div>
                    <div class="col s3"><i class="material-icons tiny">today</i> <fmt:formatDate pattern="dd/MM/yy hh:mm" value="${modelo.reunion.fecha}" /></div>
                    <div class="col s3"><i class="material-icons tiny">location_on</i> ${modelo.reunion.lugar}</div>
                    <div class="col s3"><i class="material-icons tiny">timer</i> ${modelo.reunion.duracion} minutos</div>
                </div>

                <ul class="collection with-header">
                    <li class="collection-header"><h5>Temas</h5></li>
                    <c:forEach var="tema" items="${modelo.reunion.temas}"><li class="collection-item">${tema}</li></c:forEach>
                </ul>

                <c:if test="${modelo.reunion.finalizada}">
                    <ul class="collection with-header">
                        <li class="collection-header"><h5>Resoluciones</h5></li>
                            <c:forEach var="resolucion" items="${modelo.reunion.resoluciones}">
                            <li class="collection-item">${resolucion}</li>
                            </c:forEach>
                    </ul>

                    <ul class="collection with-header">
                        <li class="collection-header"><h5>Observaciones</h5></li>
                        <li class="collection-item">${modelo.reunion.observaciones}</li>
                    </ul>
                </c:if>

                <ul class="collection">
                    <c:if test="${modelo.reunion.encuesta == null and usuario.rol eq 'Encargado' }">
                        <li class="collection-item"><div>Encuesta<a href="encuesta?accion=agregar&id=${modelo.reunion.id}" class="secondary-content"><i class="material-icons">add_circle</i></a></div></li>
                        </c:if>
                        <c:if test="${modelo.reunion.encuesta != null}">
                        <li class="collection-item"><div>Encuesta<a href="reuniones?accion=encuesta&id=${modelo.reunion.id}" class="secondary-content"><i class="material-icons">send</i></a></div></li>
                        </c:if>
                        <c:if test="${!(modelo.reunion.pendiente) and usuario.rol eq 'Encargado'}">
                        <li class="collection-item"><div>Participantes<a href="reunion?accion=ver_participantes&id=${modelo.reunion.id}" class="secondary-content"><i class="material-icons">send</i></a></div></li>
                        </c:if>
                </ul>
            </div>

        </div>

        <c:if test="${usuario.rol eq 'Encargado'}">
            <div class="fixed-action-btn horizontal">
                <a class="btn-floating btn-large red">
                    <i class="large material-icons">mode_edit</i>
                </a>
                <ul>
                    <li><a href="reunion?accion=modificar&id=${modelo.reunion.id}" class="btn-floating green"><i class="material-icons">mode_edit</i></a></li>
                    <li><a href="#modal" class="btn-floating blue"><i class="material-icons">delete</i></a></li>
                </ul>
            </div>

            <div id="modal" class="modal">
                <div class="modal-content">
                    <h4>¿Está segur@ que desea eliminar la reunión actual?</h4>
                </div>
                <div class="modal-footer">
                    <a href="#!" class=" modal-action modal-close waves-teal btn-flat">Cancelar</a> 
                    <form action="reunion" method="post">
                        <button type="submit" name="accion" value="eliminar" class="modal-action modal-close waves-effect waves-teal btn-flat">Eliminar</button>
                        <input type="hidden" name="id" value="${modelo.reunion.id}">
                    </form>
                </div>
            </div>
        </c:if>
    </jsp:body>
</t:masterpage>