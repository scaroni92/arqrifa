<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
    <head>
        <title>JSP Page</title>
        <style>
            body{
                padding:20px;
            }
            fieldset {
                margin:10px;
            }
        </style>
    </head>
    <body>
        <c:set var="encuesta" scope="page" value="${reunion.encuesta}"/>
        <h1>Agregar encuesta</h1>
        <form action='Encuesta' method='post' name="formulario">
            <p><input type="text" name="titulo" placeholder="Título de la encuesta" value="${encuesta.titulo}" required/></p>
            <p><input type="number" name='duracion' placeholder="Duración" value="${encuesta.duracion}" required/></p>
            <p><input type="submit" name="accion" value="agregar"></p>
            <p>${modelo.mensaje}</p>
            <br>
            <hr>
            <br>
            <div id="propuestas-wrapper">
                <c:forEach var="propuesta" items="${encuesta.propuestas}" varStatus="vsp">
                    <fieldset id="propuesta${vsp.index}-wrapper">
                        <p><input type="text" name='preguntas' placeholder="Pregunta" value="${propuesta.pregunta}" required></p>


                        <div id="propuesta${vsp.index}-preguntas-container"> 
                            <c:forEach var="respuesta" items="${propuesta.respuestas}" varStatus="vsr">
                                <p id="propuesta${vsp.index}-respuesta${vsr.index}">
                                    <input type="text" name="respuestas${vsp.index}" placeholder="Respuesta" value="${respuesta.respuesta}" required/>
                                    <button onclick="eliminarRespuesta(${vsp.index}, ${vsr.index})">X</button>
                                </p>
                            </c:forEach>
                        </div>

                        <a href="javascript:agregarRespuesta(${vsp.index})">Agregar respuesta</a>
                        <a href="javascript:eliminarPropuesta(${vsp.index})">Eliminar propuesta</a>

                    </fieldset>
                </c:forEach>
            </div>

        </form>
        <br>
        <br>
        <a href="javascript:agregarPropuesta()">Agregar propuesta</a>


        <script>

            function agregarPropuesta() {
                var propuestasWrapper = document.getElementById("propuestas-wrapper");
                var indicePropuesta = propuestasWrapper.childElementCount;
                var element = document.createElement("fieldset");
                element.id = 'propuesta' + indicePropuesta + '-wrapper';
                element.innerHTML = "<p><input type='text' name='preguntas' placeholder='Pregunta' required></p>"
                        + "<div id='propuesta" + indicePropuesta + "-preguntas-container'></div>"
                        + "<a href='javascript:agregarRespuesta(" + indicePropuesta + ")'>Agregar respuesta</a>"
                        + "<a href='javascript:eliminarPropuesta(" + indicePropuesta + ")'>Eliminar propuesta</a>";

                propuestasWrapper.appendChild(element);
            }

            function eliminarPropuesta(indicePropuesta) {

                if (indicePropuesta > 0) {
                    if (confirm('¿Está seguro que desea eliminar la propuesta?')) {
                        var propuestasWrapper = document.getElementById("propuestas-wrapper");
                        var propuesta = document.getElementById("propuesta" + indicePropuesta + "-wrapper");
                        propuestasWrapper.removeChild(propuesta);
                    }
                }
            }

            function agregarRespuesta(indicePropuesta) {
                var preguntasContainer = document.getElementById("propuesta" + indicePropuesta + "-preguntas-container");
                var indiceRespuesta = preguntasContainer.childElementCount;

                var element = document.createElement("p");
                element.id = 'propuesta' + indicePropuesta + "-respuesta" + indiceRespuesta;
                element.innerHTML = "<input type='text' name='respuestas" + indicePropuesta + "' placeholder='Respuesta' required /><button onclick=eliminarRespuesta(" + indicePropuesta + "," + indiceRespuesta + ")>X</button>";

                preguntasContainer.appendChild(element);
            }

            function eliminarRespuesta(indicePropuesta, indiceRespuesta) {
                var preguntasContainer = document.getElementById("propuesta" + indicePropuesta + "-preguntas-container");

                var respuestaElement = document.getElementById('propuesta' + indicePropuesta + '-respuesta' + indiceRespuesta);
                preguntasContainer.removeChild(respuestaElement);

            }
        </script>
    </body>
</html>
