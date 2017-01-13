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
        <h1>Modificar encuesta</h1>
        <form action='Encuesta?accion=modificar' method='post' name="formulario">
            <p><input type="text" name="titulo" placeholder="Título de la encuesta" value="${encuesta.titulo}"/></p>
            <p><input type="number" name='duracion' placeholder="Duración" value="${encuesta.duracion}" /></p>
            <p><input type="button" value="confirmar" onclick="enviarFormulario()"></p>
            <p>${modelo.mensaje}</p>
            <br>
            <hr>
            <br>
            <div id="propuestas-wrapper">
                <c:forEach var="propuesta" items="${encuesta.propuestas}" varStatus="vsp">
                    <fieldset id="propuesta${vsp.index}-wrapper">
                        <p><input type="text" name='propuesta${vsp.index}_pregunta' placeholder="Pregunta" value="${propuesta.pregunta}"></p>


                        <div id="propuesta${vsp.index}-preguntas-container"> 
                            <c:forEach var="respuesta" items="${propuesta.respuestas}" varStatus="vsr">
                                <p id="propuesta${vsp.index}-respuesta${vsr.index}">
                                    <input type="text" name="propuesta${vsp.index}_respuesta${vsr.index}" placeholder="Respuesta" value="${respuesta.respuesta}"/>
                                    <button onclick="eliminarRespuesta(${vsp.index}, ${vsr.index})">X</button>
                                </p>
                            </c:forEach>
                        </div>

                        <a href="javascript:agregarRespuesta(${vsp.index})">Agregar respuesta</a>
                        <a href="javascript:eliminarPropuesta(${vsp.index})">Eliminar propuesta</a>
                        <input type="hidden" id="propuesta${vsp.index}-cantidad-respuestas" name="propuesta${vsp.index}_cantidad_respuestas" value="${fn:length(propuesta.respuestas)}"/>
                    </fieldset>
                </c:forEach>
            </div>


            <input id="cantidad-propuestas" type="hidden" name="cantidad_propuestas" value="${fn:length(encuesta.propuestas)}"/>
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
                element.innerHTML = "<p><input type='text' name='propuesta" + indicePropuesta + "_pregunta' placeholder='Pregunta'></p>"
                        + "<div id='propuesta" + indicePropuesta + "-preguntas-container'></div>"
                        + "<a href='javascript:agregarRespuesta(" + indicePropuesta + ")'>Agregar respuesta</a>"
                        + "<a href='javascript:eliminarPropuesta(" + indicePropuesta + ")'>Eliminar propuesta</a>"
                        + "<input type='hidden' name='propuesta" + indicePropuesta + "_cantidad_respuestas' id='propuesta" + indicePropuesta + "-cantidad-respuestas' value='0'/>";

                propuestasWrapper.appendChild(element);

                document.getElementById("cantidad-propuestas").value++;
            }

            function eliminarPropuesta(indicePropuesta) {
                alert('¿Está seguro que desea eliminar la propuesta?');
                var propuestasWrapper = document.getElementById("propuestas-wrapper");
                var propuesta = document.getElementById("propuesta" + indicePropuesta + "-wrapper");
                propuestasWrapper.removeChild(propuesta);

                document.getElementById("cantidad-propuestas").value--;
            }

            function agregarRespuesta(indicePropuesta) {
                var preguntasContainer = document.getElementById("propuesta" + indicePropuesta + "-preguntas-container");
                var indiceRespuesta = preguntasContainer.childElementCount;

                var element = document.createElement("p");
                element.id = 'propuesta' + indicePropuesta + "-respuesta" + indiceRespuesta;
                element.innerHTML = "<input type='text' name='propuesta" + indicePropuesta + "_respuesta" + indiceRespuesta + "' placeholder='Respuesta' /><button onclick=eliminarRespuesta(" + indicePropuesta + "," + indiceRespuesta + ")>X</button>";

                preguntasContainer.appendChild(element);

                document.getElementById("propuesta" + indicePropuesta + "-cantidad-respuestas").value++;
            }

            function eliminarRespuesta(indicePropuesta, indiceRespuesta) {
                var preguntasContainer = document.getElementById("propuesta" + indicePropuesta + "-preguntas-container");
                var respuestaElement = document.getElementById('propuesta' + indicePropuesta + '-respuesta' + indiceRespuesta);
                preguntasContainer.removeChild(respuestaElement);

                document.getElementById("propuesta" + indicePropuesta + "-cantidad-respuestas").value--;
            }

            function enviarFormulario() {
                document.formulario.submit();
            }
        </script>
    </body>
</html>
