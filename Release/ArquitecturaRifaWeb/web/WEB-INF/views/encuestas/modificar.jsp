<%@taglib  prefix="t" tagdir="/WEB-INF/tags/" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="encuesta" scope="page" value="${reunion.encuesta}"/>
<t:masterpage titulo="Agregar">
    <jsp:body>
        <div class="container">
            <div class="row">
                <form action="encuesta" class="col s12 card-panel" style="padding:30px;" method="post">
                    <div class="row">
                        <div class="col s12">
                            <h5>Modificar encuesta</h5> </div>
                    </div>
                    <div class="row general-container">
                        <div class="input-field col m10 s12">
                            <input id="titulo" name="titulo" type="text" value="${encuesta.titulo}"  style="font-size: 2em;" required>
                            <label for="titulo">Título</label>
                        </div>
                        <div class="input-field col m2 s12">
                            <input id="duracion" name="duracion" type="number" value="${encuesta.duracion}" required>
                            <label for="duracion">Duración</label>
                        </div>
                    </div>
                    <div id="propuestas-wrapper">
                    </div>
                    <div class="row">
                        <div class="col">
                            <input id="agregar-propuesta" type="text" placeholder="Agregar propuesta"> </div>
                    </div>
                    <div class="row">
                        <div class="input-field col s12 center">
                            <button class="btn waves-effect waves-light" type="submit" name="accion" value="modificar">guardar</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>


        <script type="text/javascript" src="js/layouts/encuesta.js"></script>
        <script type="text/javascript">
            <c:forEach var="propuesta" items="${reunion.encuesta.propuestas}">
            var respuestas = [<c:forEach var="respuesta" items="${propuesta.respuestas}">{tag: '${respuesta.respuesta}'},</c:forEach>];
            agregarPropuesta('${propuesta.pregunta}', respuestas);
            </c:forEach>
        </script>
    </jsp:body>
</t:masterpage>