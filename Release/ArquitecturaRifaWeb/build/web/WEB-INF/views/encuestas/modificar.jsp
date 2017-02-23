<%@taglib  prefix="t" tagdir="/WEB-INF/tags/" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="encuesta" scope="page" value="${reunion.encuesta}"/>
<t:masterpage titulo="Modificar">
    <jsp:body>
        <div class="container">
            <div class="row">
                <form action="encuesta" id="encuesta-form" class="col s12 card-panel"  method="post" onclick="crearParametros()">
                    <input type="hidden" name="id" value="${encuesta.id}" />
                    <div class="row">
                        <div class="col s12">
                            <h5>Modificar encuesta</h5> </div>
                    </div>
                    <div class="row">
                        <div class="input-field col m10 s12">
                            <input id="titulo" name="titulo" type="text" value="${encuesta.titulo}" required>
                            <label for="titulo">Título</label>
                        </div>
                        <div class="input-field col m2 s12">
                            <input id="duracion" name="duracion" type="number" value="${encuesta.duracion}" required>
                            <label for="duracion">Duración</label>
                        </div>
                    </div>
                    <p>Propuestas</p>
                    <div id="propuestas-container">
                        <div id="propuesta-container1" class="row propuesta-container">
                            <div class="input-field col s10">
                                <input id="propuesta1" name="preguntas" type="text">
                                <label for="propuesta1">Pregunta</label>
                            </div>
                            <div class="col s2"><span class="icon-btn right"><i class="material-icons" onclick="eliminarPropuesta('propuesta-container1')">close</i></span> </div>
                            <div class="col s12">
                                <div class="chips chips-placeholder respuestas-chips"></div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col">
                            <input id="agregar-propuesta" type="text" placeholder="Agregar propuesta"> </div>
                    </div>
                    <div class="row">
                        <div class="input-field col s12 center">
                            <button class="btn waves-effect waves-light" type="submit" name="accion" value="modificar">modificar</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
        <script>
            var $propContainer = $('#propuestas-container');
        </script>
    </jsp:body>
</t:masterpage>