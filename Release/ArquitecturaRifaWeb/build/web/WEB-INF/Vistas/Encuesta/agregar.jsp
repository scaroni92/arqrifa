<%@page import="org.arqrifa.datatypes.DTReunion"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags/" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Arquitectura Rifa | Crear Encuesta</title>
        <style>
            #pregunta, #respuestas, #respuesta {
                width:400px;
            }
        </style>
    </head>

    <body>
        <h1>Agregar Encuesta a la Reunión</h1>
        <form action="Encuesta" method="post" onKeypress="if(event.keyCode == 13) event.returnValue = false;">
            <fieldset>
                <legend>General</legend>
                <p>
                    Título: <input type="text" name="titulo" value="${modelo.titulo}" placeholder="Ej: Encuesta del 30/12/2016" />
                    Duración: <input type="number" name="duracion" value="${modelo.duracion}">
                </p>
                <hr>
                <h3>Agregue Propuestas</h3>
                <table>
                    <tr>
                        <td>Pregunta </td>
                        <td><input type="text" id="pregunta" name="pregunta" placeholder="Ej: ¿Cuál es tu color favorito?"/></td>
                    </tr>
                    <tr>
                        <td>Respuestas</td>
                        <td>
                            <textarea id="respuestas" name="respuestas" style="resize:none" readonly></textarea><br>
                            <input id="respuesta" type="text" placeholder="Ingrese una pregunta y presione Enter." onkeypress="agregarRespuesta(event)">
                        </td>
                    </tr>
                </table>
                <button type="submit" name="accion" value="agregar_propuesta">Agregar propuesta</button>
            </fieldset>
            <p>${modelo.mensaje}</p>
            <fieldset>
                <legend>Vista previa</legend>
                <h3>${modelo.titulo}</h3>
                <c:forEach var="p" items="${reunion.encuesta.propuestas}" >
                    <span>${p.pregunta}</span>
                    <c:forEach var="r" items="${p.respuestas}">
                        ${r}<br>
                    </c:forEach>
                </c:forEach>
            </fieldset>
            <p><button type="submit" name="accion" value="crear_encuesta">CONFIRMAR</button></p>
        </form>
        <script>
            function agregarRespuesta(e) {
                var tecla = (document.all) ? e.keyCode : e.which;
                if (tecla == 13) {
                    var txtRespuesta = document.getElementById("respuesta");

                    if (txtRespuesta.value !== "") {
                        document.getElementById("respuestas").innerHTML += txtRespuesta.value + "\n";
                        txtRespuesta.value = "";
                    }
                }

            }
        </script>
    </body>

</html>
