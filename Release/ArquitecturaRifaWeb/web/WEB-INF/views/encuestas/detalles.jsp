<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags/" %>
<t:masterpage titulo="Encuesta">
    <jsp:body>
        <c:set scope="page" value="${modelo.reunion.encuesta}" var="encuesta" />
        <div class="container">
            <div class="card-panel grey-text text-darken-2">
                <h4 class="light truncate">${encuesta.titulo}<span class="new badge" data-badge-caption="Duración: ${encuesta.duracion}"></span></h4>
                <c:forEach var="propuesta" items="${encuesta.propuestas}">
                    <ul class="collection with-header">
                        <li class="collection-header"><h6 class="text-darken-1">${propuesta.pregunta}</h6></li>
                        <c:forEach var="respuesta" items="${propuesta.respuestas}">
                            <li class="collection-item">${respuesta.respuesta}<span class="badge" data-badge-caption="Votos">${respuesta.cantidadVotos}</span></li>
                        </c:forEach>
                    </ul>
                </c:forEach>
            </div>
        </div>
        <c:if test="${usuario.rol eq 'Encargado'}">
            <div class="fixed-action-btn horizontal">
                <a href="encuesta?accion=modificar&id=${modelo.reunion.id}" class="btn-floating btn-large red">
                    <i class="large material-icons">mode_edit</i>
                </a>
                <ul>
                    <li><a href="#eliminar" class="btn-floating blue"><i class="material-icons">delete</i></a></li>
                </ul>
            </div>
        </c:if>         
        <div id="eliminar" class="modal">
            <div class="modal-content">
                <h4>¿Está seguro/a que desea eliminar la encuesta actual?</h4>
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
