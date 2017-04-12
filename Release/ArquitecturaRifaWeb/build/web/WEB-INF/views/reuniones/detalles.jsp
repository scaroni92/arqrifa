<%@taglib prefix="t" tagdir="/WEB-INF/tags/" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<t:masterpage titulo="Detalles">
    <jsp:body>
        <div class="container">
            <div class="card-panel">
                <div class="panel-header"> 
                    <span class="chip right">${modelo.reunion.estado}</span>
                </div>
                <h4>${modelo.reunion.titulo}</h4>
                <p class="flow-text">${modelo.reunion.descripcion}</p>
                <div class="divider"></div>
                <p>Fecha y hora: <fmt:formatDate pattern="dd/MM/yy hh:mm" value="${modelo.reunion.fecha}" /></p>
                <p>Carácter: ${modelo.reunion.obligatoria? "obligatorio" : "no obligatorio"}</p>
                <p>Duración: ${modelo.reunion.duracion} minutos</p>
                <p>Lugar: ${modelo.reunion.lugar}</p>
                <c:if test="${modelo.reunion.estado eq 'Finalizada'}">
                    <p>Participantes: ${fn:length(modelo.reunion.participantes)}</p>    
                </c:if>
                <p>
                    Temas
                <ul>
                    <c:forEach var="tema" items="${modelo.reunion.temas}">
                        <li>${tema}</li>
                        </c:forEach>
                </ul>
                </p>

                <c:if test="${modelo.reunion.estado eq 'Finalizada'}">
                    <ul class="collapsible" data-collapsible="expandable">
                        <li>
                            <div class="collapsible-header"><i class="material-icons grey-text text-darken-1 ">rate_review</i>Resoluciones</div>
                            <div class="collapsible-body">
                                <c:forEach var="resolucion" items="${modelo.reunion.resoluciones}">
                                    <p>${resolucion}</p>
                                </c:forEach>
                            </div>
                        </li>
                        <li>
                            <div class="collapsible-header"><i class="material-icons grey-text text-darken-1">find_in_page</i>Observaciones</div>
                            <div class="collapsible-body"><span>${modelo.reunion.observaciones}</span></div>
                        </li>
                    </ul>
                </c:if>
            </div>

        </div>

        <!-- TOOLBAR -->
        <div class="fixed-action-btn toolbar">
            <a class="btn-floating btn-large red"> <i class="large material-icons">menu</i> </a>
            <ul>
                <c:if test="${usuario.rol eq 'Encargado'}">
                    <li class="waves-effect waves-light"><a href="#modal"  data-tooltip="Eliminar"><i class="material-icons">delete</i></a></li>
                    <li class="waves-effect waves-light"><a href="reunion?accion=modificar&id=${modelo.reunion.id}" data-tooltip="Modificar"><i class="material-icons">edit</i></a></li>
                    <li class="waves-effect waves-light"><a href="reunion?accion=ver-lista&id=${modelo.reunion.id}" data-tooltip="Asistencias"><i class="material-icons">people</i></a></li>
                        <c:if test="${modelo.reunion.encuesta == null}">
                        <li class="waves-effect waves-light"><a href="encuesta?accion=agregar&reunionId=${modelo.reunion.id}" data-tooltip="Agregar encuesta"><i class="material-icons">playlist_add</i></a></li>
                        </c:if>
                    </c:if>
                    <c:if test="${modelo.reunion.encuesta != null}">
                    <li class="waves-effect waves-light"><a href="encuesta?accion=detalles&reunionId=${modelo.reunion.id}" data-tooltip="Encuesta"><i class="material-icons">dvr</i></a></li>
                    </c:if>
            </ul>
        </div>

        <!-- MODAL-->
        <div id="modal" class="modal">
            <div class="modal-content">
                <h4>¿Está segur@ que desea eliminar la reunión actual?</h4>
            </div>
            <div class="modal-footer">
                <a href="#!" class=" modal-action modal-close waves-teal btn-flat">Rechazar</a> 
                <form action="reunion" method="post">
                    <button type="submit" name="accion" value="eliminar" class="modal-action modal-close waves-effect waves-teal btn-flat">Eliminar</button>
                    <input type="hidden" name="id" value="${modelo.reunion.id}">
                </form>
            </div>
        </div>
    </jsp:body>
</t:masterpage>