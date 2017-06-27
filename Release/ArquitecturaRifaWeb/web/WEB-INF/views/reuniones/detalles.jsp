<%@taglib prefix="t" tagdir="/WEB-INF/tags/" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<t:masterpage titulo="Reunión">
    <jsp:body>
        <div class="container">
            <div class="card-panel grey-text text-darken-2">
                <h4 class="light truncate">${modelo.reunion.titulo}<span class="new badge " data-badge-caption="${modelo.reunion.estado}"></span></h4>
                <p>${modelo.reunion.descripcion}</p>
                <ul class="collection with-header">
                    <li class="collection-header"><h6 class="text-darken-1">Temas</h6></li>
                    <c:forEach var="tema" items="${modelo.reunion.temas}"><li class="collection-item">${tema}</li></c:forEach>
                    </ul>

                <c:if test="${modelo.reunion.finalizada}">
                    <ul class="collection with-header">
                        <li class="collection-header"><h6 class="text-darken-1">Resoluciones</h6></li>
                            <c:forEach var="resolucion" items="${modelo.reunion.resoluciones}">
                            <li class="collection-item">${resolucion}</li>
                            </c:forEach>
                    </ul>

                    <ul class="collection with-header">
                        <li class="collection-header"><h6 class="text-darken-1">Observaciones</h6></li>
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
                <div class="divider"></div>
                <br>
                <div class="row">
                    <div class="col s12">
                        Caractér: ${modelo.reunion.obligatoria? 'Obligatoria' : 'Opcional'}<br>
                        Lugar: ${modelo.reunion.lugar}<br>
                        Fecha y hora: <fmt:formatDate pattern="dd 'de' MMMM HH:mm" value="${modelo.reunion.fecha}" /><br>
                        Duración: ${modelo.reunion.duracion}<br>
                    </div>
                </div>
            </div>
        </div>
        <c:if test="${usuario.rol eq 'Encargado'}">
            <div class="fixed-action-btn horizontal">
                <a href="reunion?accion=modificar&id=${modelo.reunion.id}" class="btn-floating btn-large red">
                    <i class="large material-icons">mode_edit</i>
                </a>
                <ul>
                    <li><a href="#modal" class="btn-floating blue"><i class="material-icons">delete</i></a></li>
                </ul>
            </div>

            <div id="modal" class="modal">
                <div class="modal-content">
                    <h4>¿Está seguro/a que desea eliminar la reunión actual?</h4>
                </div>
                <div class="modal-footer">
                    <a class=" modal-action modal-close  btn-flat">Cancelar</a> 
                    <form action="reunion" method="post">
                        <button type="submit" name="accion" value="eliminar" class="modal-action modal-close waves-effect  btn-flat">Eliminar</button>
                        <input type="hidden" name="id" value="${modelo.reunion.id}">
                    </form>
                </div>
            </div>
        </c:if>
    </jsp:body>
</t:masterpage>