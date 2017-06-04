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
          <div class="fixed-action-btn horizontal">
            <a class="btn-floating btn-large red">
                <i class="large material-icons">mode_edit</i>
            </a>
            <ul>
                <li><a href="encuesta?accion=modificar&id=${modelo.reunion.id}" class="btn-floating green"><i class="material-icons">mode_edit</i></a></li>
                <li><a href="#eliminar" class="btn-floating blue"><i class="material-icons">delete</i></a></li>
            </ul>
        </div>

        <!-- Cuadro de dialogo -->                
        <div id="eliminar" class="modal">
            <div class="modal-content">
                <h4>¿Está segur@ que desea eliminar la encuesta actual?</h4>
            </div>
            <div class="modal-footer">
                <a class=" modal-action modal-close waves-teal btn-flat">Cancelar</a>
                <form action="encuesta" method="post">
                    <button type="submit" name="accion" value="eliminar" class="modal-action modal-close waves-effect waves-teal btn-flat">Eliminar</button>
                    <input type="hidden" name="id" value="${modelo.reunion.id}">
                </form>
            </div>
        </div>
    </jsp:body>
</t:masterpage>
