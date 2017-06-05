<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:masterpage titulo="Panel">
    <c:if test="${reunionActiva != null}">
    <div class="container">
        <form action="panel" id="panel-form" method="post">
            <div class="card-panel">
                <div class="panel-header"> 
                    <span class="chip right  ${reunionActiva.estado eq 'Pendiente'? '':'green white-text'}" style="margin-left:10px">${reunionActiva.estado}</span>
                    <c:if test="${reunionActiva.estado eq 'Pendiente'}">                    
                        <button class="btn btn-flat waves-effect waves-light right" type="submit" name="accion" value="iniciar">iniciar <i class="material-icons right">play_circle_outline</i> </button>
                    </c:if>
                    <c:if test="${reunionActiva.estado != 'Pendiente'}">
                        <button class="btn btn-flat waves-effect waves-light right" type="submit" name="accion" value="finalizar">finalizar <i class="material-icons right">stop</i> </button>
                    </c:if>
                </div>
                <h5>Reunión actual: ${reunionActiva.titulo}</h5>
            </div>
            <ul class="collection with-header">
                <li class="collection-header"><h4>Temas</h4></li>
                <c:forEach var="tema" items="${reunionActiva.temas}">
                    <li class="collection-item">${tema}</li>
                </c:forEach>
            </ul>
            <div class="card-panel">
                <div class="row">
                    <div class="input-field col s12">
                        <textarea id="observaciones" name="observaciones" class="materialize-textarea">${reunionActiva.observaciones}</textarea>
                        <label for="observaciones">Observaciones</label>
                    </div>
                </div>
                <div class="row">
                    <div class="col s12">
                        <div class="chips"></div>
                    </div>
                </div>
            </div>
        </form>
    </div>

    <!-- TOOLBAR -->
    <div class="fixed-action-btn toolbar">
        <a class="btn-floating btn-large red"> <i class="large material-icons">menu</i> </a>
        <ul>
            <li class="waves-effect waves-light"><a data-tooltip="Detalles de reunión" href="reuniones?accion=detalles&id=${reunionActiva.id}"><i class="material-icons">info_outline</i></a></li>

            <c:choose>
                <c:when test="${reunionActiva.estado eq 'Listado'}">
                    <li class="waves-effect waves-light"><a data-tooltip="Deshabilitar lista" href="panel?accion=deshabilitar_lista&id=${reunionActiva.id}"><i class="material-icons">assignment_late</i></a></li>
                    </c:when>
                    <c:otherwise>
                    <li class="waves-effect waves-light"><a data-tooltip="Habilitar lista" href="panel?accion=habilitar_lista&id=${reunionActiva.id}"><i class="material-icons">assignment_turned_in</i></a></li>
                    </c:otherwise>
                </c:choose>
            <li class="waves-effect waves-light"><a data-tooltip="Lista de asistencias" href="panel?accion=lista&id=${reunionActiva.id}"><i class="material-icons">assignment</i></a></li>
            <li class="waves-effect waves-light"><a data-tooltip="Iniciar votación" href="panel?accion=iniciar_votacion"><i class="material-icons">play_circle_outline</i></a></li>
            <li class="waves-effect waves-light"><a data-tooltip="Finalizar votación" href="panel?accion=finalizar_votacion"><i class="material-icons">stop</i></a></li>
            <li class="waves-effect waves-light"><a data-tooltip="Cuestionario" href="cuestionario"><i class="material-icons">speaker_notes</i></a></li>

        </ul>
    </div>
    <script>
        $('form').on('submit', function () {
            $.each($('.chips').material_chip('data'), function (i, val) {
                $('form').append('<input type="hidden" name="resoluciones" value="' + val.tag + '" />');
            });
        });

        $('.chips').material_chip({
            data: [<c:forEach var="resolucion" items="${reunionActiva.resoluciones}">{tag: '${resolucion}'},</c:forEach>],
            placeholder: 'Agregar resolucion',
            secondaryPlaceholder: '+Resolución'
        });
        </script>
    </c:if>
</t:masterpage>

