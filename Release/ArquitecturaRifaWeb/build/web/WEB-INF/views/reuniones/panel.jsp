<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:masterpage titulo="Panel">
    <div class="container">
        <div class="cyan panel-wrapper card" style="padding:20px">
            <div class="section-info row white-text">
                <div class="col s12 m6">
                    <div class="section-estado">
                        <h4>${reunionActiva.estado}</h4> </div>
                    <div class="section-general"> <span class="strong"><b>General</b></span>
                        <br>Hora de inicio: <fmt:formatDate pattern="HH:mm'h'" value="${reunionActiva.fecha}" />
                        <br> Duración estimada: ${reunionActiva.duracion}m
                        <br><a href="reuniones?accion=detalles&id=${reunionActiva.id}" class="orange-text text-accent-2">DETALLES</a> </div>
                </div>
                <div class="col s12 m6">
                    <div class="section-temas"> 
                        <span class="strong"><b>Temas</b></span>
                        <c:forEach var="tema" items="${reunionActiva.temas}">
                            <br>- ${tema}
                        </c:forEach>
                    </div>
                    <div class="section-descripcion">
                        <br><span class="strong"><b>Descripción</b></span>
                        <br>${reunionActiva.descripcion}
                    </div>
                </div>
            </div>
            <div class="section-links row">
                <div class="col s12">
                    <ul class="collection white-text">
                        <c:if test="${reunionActiva.encuesta != null}">
                            <li class="collection-item cyan">
                                <div>La reunión contiene una encuesta<a href="reuniones?accion=encuesta&id=${reunionActiva.id}" class="secondary-content"><i class="material-icons white-text">poll</i></a></div>
                            </li>
                        </c:if>
                        <c:if test="${reunionActiva.encuesta == null}">
                            <li class="collection-item cyan">
                                <div>Agregar una encuesta a la reunión<a href="encuesta?accion=agregar&id=${reunionActiva.id}" class="secondary-content"><i class="material-icons white-text">add_circle</i></a></div>
                            </li>
                        </c:if>
                        <li class="collection-item cyan">
                            <div>Listado de estudiantes presentes<a href="reunion?accion=ver_participantes&id=${reunionActiva.id}" class="secondary-content"><i class="material-icons white-text">assignment_turned_in</i></a></div>
                        </li>
                    </ul>
                </div>
            </div>
            <div class="row">
                <div class="col s12">
                    <form action="panel" method="post">
                        <h5 class="white-text light"><i class="material-icons prefix">mode_edit</i> Recapitulación</h5>
                        <div class="recapitulacion-inputs white" style="padding:10px 30px; border-radius: 3px;">
                            <div class="row">
                                <div class="input-field col s12">
                                    <div class="chips chips-placeholder"></div>
                                </div>
                                <div class="input-field col s12">
                                    <textarea id="textarea1" class="materialize-textarea"></textarea>
                                    <label for="textarea1">Observaciones</label>
                                </div>
                            </div>
                        </div>
                        <div id="actions" class="section-actions center" style="margin-top:20px"> 
                            <button id="iniciar-reunion" name="accion" value="iniciar_reunion" class="btn waves-effect"><i class="material-icons right">record_voice_over</i>Iniciar reunión</button>
                            <button id="habilitar-votacion" name="accion" value="habilitar_votacion" class="${reunionActiva.encuesta == null? 'disabled' : ''} btn waves-effect"><i class="material-icons right">speaker_notes</i>habilitar votacion</button>
                            <button id="deshabilitar-votacion" name="accion" value="deshabilitar_votacion" class="btn waves-effect"><i class="material-icons right">speaker_notes_off</i>deshabilitar votacion</button>
                            <button id="habilitar-lista" name="accion" value="habilitar_lista" class="btn waves-effect"><i class="material-icons right">phonelink</i>habilitar lista</button>
                            <a id="finalizar-reunion" href="#finalizar" class="btn waves-effect"> <i class="material-icons right">done</i>Finalizar reunión</a>
                            <button id="deshabilitar-lista" name="accion" value="deshabilitar_lista" class="btn waves-effect"> <i class="material-icons right">phonelink_off</i>deshabilitar lista</button>
                            <a id="cuestionario" href="cuestionario" class="btn waves-effect"><i class="material-icons right">question_answer</i>cuestionario</a>
                            <a id="asistencias" href="asistencias" class="btn waves-effect"><i class="material-icons right">person_add</i>agregar asistencias</a></div>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <div id="finalizar" class="modal">
        <div class="modal-content">
            <h4>¿Está seguro/a que desea dar por finalizada la reunión?</h4>
            <p>Recuerde que una vez finalizada no podrá ser iniciada nuevamente</p>
        </div>
        <div class="modal-footer">
            <a class=" modal-action modal-close waves-teal btn-flat">Cancelar</a> 
            <form action="panel" method="post">
                <button type="submit" name="accion" value="finalizar_reunion" class="modal-action modal-close waves-effect waves-teal btn-flat">Confirmar</button>
            </form>
        </div>
    </div>
    <script type="text/javascript" src="js/layouts/panel.js"></script>
    <script>
        mostrarBotones('${reunionActiva.estado}');

        $('.chips').material_chip({
            data: [<c:forEach var="resolucion" items="${reunionActiva.resoluciones}">{tag: '${resolucion}'},</c:forEach>],
            placeholder: 'Agregar resolucion',
            secondaryPlaceholder: '+Resolución'
        });
        </script>
</t:masterpage>

