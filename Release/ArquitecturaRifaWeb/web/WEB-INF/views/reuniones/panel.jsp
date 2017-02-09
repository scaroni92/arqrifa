<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:masterpage titulo="Panel">
    <div class="container">
        <form action="panel" id="panel-form" method="post">
            <div class="card-panel">
                <div class="panel-header"> 
                    <c:if test="${modelo.estado eq 'Pendiente'}">                    
                        <span class="chip blue white-text right">Pendiente</span>
                        <button class="btn btn-flat waves-effect waves-light right" type="submit" name="accion" value="iniciar">iniciar <i class="material-icons right">play_circle_outline</i> </button>
                    </c:if>
                    <c:if test="${modelo.estado eq 'Iniciada'}">
                        <span class="chip green white-text right">Iniciada</span>
                        <button class="btn btn-flat waves-effect waves-light right" type="submit" name="accion" value="finalizar">finalizar <i class="material-icons right">stop</i> </button>
                    </c:if>
                </div>
                <h5>${modelo.titulo} </h5>
                <p>Fecha y hora planificada: ${modelo.fecha} ${modelo.hora}</p>
            </div>
            <ul class="collapsible" data-collapsible="expandable">
                <li>
                    <div class="collapsible-header green lighten-1 white-text active"><i class="material-icons">assignment</i>Temas a tratar</div>
                    <div class="collapsible-body white">
                        <c:forEach var="tema" items="${modelo.temas}">
                            <input type="hidden" name="temas" value="${tema}" />
                            <p>${tema}</p>
                        </c:forEach>
                    </div>
                </li>
            </ul>
            <c:if test="${modelo.estado eq 'Iniciada'}">
                <div class="card-panel">
                    <h6 class="center">Recapitulación</h6>
                    <div class="row">
                        <div class="input-field col s12">
                            <textarea id="observaciones" name="observaciones" class="materialize-textarea"></textarea>
                            <label for="observaciones">Observaciones</label>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col s12">
                            <div id="resoluciones-chips" class="chips chips-placeholder"></div>
                        </div>
                    </div>
                </div>
            </c:if>

            <!-- TOOLBAR -->
            <div class="fixed-action-btn toolbar">
                <a class="btn-floating btn-large red"> <i class="large material-icons">menu</i> </a>
                <ul>
                    <li class="waves-effect waves-light"><a href="reunion?accion=detalles&id=${modelo.id}"><i class="material-icons">info_outline</i></a></li>
                    <li class="waves-effect waves-light"><a href="reunion?accion=ver-lista&id=${modelo.id}"><i class="material-icons">people</i></a></li>
                    <li class="waves-effect waves-light"><a href="encuesta?accion=agregar&reunionId=${modelo.id}"><i class="material-icons">playlist_add</i></a></li>
                    <li class="waves-effect waves-light"><a href="encuesta?accion=detalles&reunionId=${modelo.id}"><i class="material-icons">dvr</i></a></li>
                </ul>
            </div>


            <input type="hidden" name="id" value="${modelo.id}" >
            <input type="hidden" name="generacion" value="${modelo.generacion}" >
            <input type="hidden" name="titulo" value="${modelo.titulo}">
            <input type="hidden" name="descripcion" value="${modelo.descripcion}">
            <input type="hidden" name="fecha" value="${modelo.fecha}">
            <input type="hidden" name="hora" value="${modelo.hora}">
            <input type="hidden" name="duracion" value="${modelo.duracion}">
            <input type="hidden" name="obligatoria" value="${modelo.obligatoria}">
            <input type="hidden" name="lugar" value="${modelo.lugar}"> 
            <input type="hidden" name="observaciones" value="${modelo.observaciones}">
            <input type="hidden" name="estado" value="${modelo.estado}">
        </form>
    </div>
</t:masterpage>

