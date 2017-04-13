<%@taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags/" %>

<t:masterpage titulo="Cuestionario">
    <jsp:body>
        <div class="container">
            <div class="card-panel grey-text text-darken-2">
                <h4>${encuesta.titulo}</h4>
                <h5>${estudiante != null? 'Votación para el estudiante' : ''} ${estudiante.nombre} ${estudiante.apellido}</h5> 
            </div>
            <nav>
                <div class="nav-wrapper">
                    <form action="cuestionario">
                        <div class="input-field">
                            <input id="search" type="search" name="ci" placeholder="Buscar estudiante" required>
                            <label class="label-icon" for="search"><i class="material-icons">search</i></label> <i class="material-icons">close</i> </div>
                        <input type="hidden" name="accion" value="buscar-estudiante">
                    </form>
                </div>
            </nav>
            <br>
            <form action="cuestionario" method="post">
                <c:forEach var="propuesta" items="${encuesta.propuestas}">
                    <div class="card">
                        <div class="card-header green white-text">
                            <h6>${propuesta.pregunta}</h6> 
                        </div>
                        <div class="card-content">
                            <c:forEach var="respuesta" items="${propuesta.respuestas}">
                                <p>
                                    <input name="${propuesta.id}" type="radio" id="${respuesta.id}" value="${respuesta.id}"/>
                                    <label for="${respuesta.id}">${respuesta.respuesta}</label>
                                </p>
                            </c:forEach>
                        </div>
                    </div>
                </c:forEach>

                <div class="row">
                    <div class="input-field col s12 center">
                        <button class="btn green waves-effect waves-light" type="submit" id="confirmar-voto" name="accion" value="confirmar-voto" ${estudiante == null? 'disabled' : ''}>confirmar votación </button>
                    </div>
                </div>
            </form>
        </div>
    </jsp:body>
</t:masterpage>
