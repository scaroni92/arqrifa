<!--ORDENAR RESPUESTAS POR VOTOS EN LÓGICA-->
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags/" %>
<t:masterpage titulo="Detalles">
    <jsp:body>
        <c:set scope="page" value="${modelo.reunion.encuesta}" var="encuesta" />

        <div class="container">
            <div class="card-panel">
                <h5>${encuesta.titulo}</h5>
                <p>Duración: ${encuesta.duracion}</p>
                <p>Estado: ${encuesta.habilitada? 'HABILITADA' : 'DESHABILITADA'}</p>
            </div>
            <ul class="collapsible" data-collapsible="expandable">
                <c:forEach var="propuesta" items="${encuesta.propuestas}">
                    <li>
                        <div class="collapsible-header green lighten-1 white-text active "><i class="material-icons">question_answer</i>${propuesta.pregunta}</div>
                        <div class="collapsible-body white">
                            <table class="highlight">
                                <tr>
                                    <th >Respuestas</th>
                                    <th >Votos</th>
                                </tr>
                                <c:forEach var="respuesta" items="${propuesta.respuestas}">
                                    <tr>
                                        <td>${respuesta.respuesta}</td>
                                        <td>${respuesta.cantidadVotos}</td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </div>
                    </li>
                </c:forEach>
            </ul>
        </div>

        <!-- Barra de herramientas -->                        
        <div class="fixed-action-btn toolbar">
            <a class="btn-floating btn-large red"> <i class="large material-icons">menu</i> </a>
            <ul>
                <li class="waves-effect waves-light"><a data-tooltip="Eliminar" href="#modal"><i class="material-icons">delete</i></a></li>
                <li class="waves-effect waves-light"><a data-tooltip="Modificar" href="encuesta?accion=modificar&id=${encuesta.id}"><i class="material-icons">edit</i></a></li>
                <li class="waves-effect waves-light"><a data-tooltip="Cuestionario" href="cuestionario?reunionId=${modelo.reunion.id}"><i class="material-icons">speaker_notes</i></a></li>
                <c:choose>
                    <c:when test="${encuesta.habilitada}">
                        <li class="waves-effect waves-light"><a data-tooltip="Finalizar" href="encuesta?accion=finalizar&reunionId=${modelo.reunion.id}"><i class="material-icons">stop</i></a></li>
                    </c:when>
                    <c:otherwise>
                        <li class="waves-effect waves-light"><a data-tooltip="Iniciar" href="encuesta?accion=iniciar&reunionId=${modelo.reunion.id}"><i class="material-icons">play_circle_filled</i></a></li>
                    </c:otherwise>
                </c:choose>
            </ul>
        </div>            

        <!-- Cuadro de dialogo -->                
        <div id="modal" class="modal">
            <div class="modal-content">
                <h4>¿Está segur@ que desea eliminar premanentemente la encuesta actual?</h4>
            </div>
            <div class="modal-footer">
                <a href="#" class=" modal-action modal-close waves-teal btn-flat">Cancelar</a>
                <form action="encuesta" method="post">
                    <button type="submit" name="accion" value="eliminar" class="modal-action modal-close waves-effect waves-teal btn-flat">Eliminar</button>
                    <input type="hidden" name="reunionId" value="${modelo.reunion.id}">
                </form>
            </div>
        </div>
    </jsp:body>
</t:masterpage>
