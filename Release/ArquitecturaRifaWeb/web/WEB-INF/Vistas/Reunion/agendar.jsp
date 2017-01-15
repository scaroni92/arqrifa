<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <title>JSP Page</title>
    </head>
    <body>
        <form action="Reuniones" method="post">
            <button type="submit" name="accion" value="agendar">AGENDAR</button>
            <input type="submit" value="DESCARTAR" />
            <a href="Reuniones">Ver calendario</a>
            <hr>
            <table>
                <tr>
                    <td>Título</td>
                    <td><input type="text" name="titulo" value="${modelo.titulo}"/></td>
                </tr>
                <tr>
                    <td>Descripción</td>
                    <td><input type="text" name="descripcion" value="${modelo.descripcion}"/></td>
                </tr>
            </table>
            <table>
                <tr>
                    <td>Fecha<br><input type="date" name="fecha" value="${modelo.fecha}"/><input type="time" name="hora" value="${modelo.hora}"/></td>
                    <td>
                        Duración<br>
                        <select name="duracion" >
                            <option value="15" ${modelo.duracion eq '15' ? "selected" : ""}>15 minutos</option>
                            <option value="30" ${modelo.duracion eq '30' ? "selected" : ""}>30 minutos</option>
                            <option value="60" ${modelo.duracion eq '60' ? "selected" : ""}>60 minutos</option>
                            <option value="90" ${modelo.duracion eq '90' ? "selected" : ""}>90 minutos</option>
                            <option value="120" ${modelo.duracion eq '120' ? "selected" : ""}>120 minutos</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>Lugar<br><input type="text" name="lugar" value="${modelo.lugar}"/></td>
                    <td></td>
                </tr>
            </table>
            <input type="checkbox" id="obligatoria"  name="obligatoria" ${modelo.obligatoria ? "checked='checked'" : ""}/><label for="obligatoria">Marcar como obligatoria</label>
            <br>
            <fieldset>
                <legend>Temas</legend>
                <div id="temas_wrapper">
                    
                    <c:forEach var="tema" items="${modelo.temas}" varStatus="contador">
                        <p id="${contador.index}">
                            <input type="text" name="temas" placeholder="Ingrese un tema aquí" value="${tema}" required>
                            <input type="button" value="X" onclick="eliminarTema(${contador.index})" />
                        <p>
                    </c:forEach>

                </div>
                <input type="button" value="Nuevo tema" onclick="agregarTema()"/>
            </fieldset>
        </form>

    ${modelo.mensaje}
    <script>
        function agregarTema() {
            var temasWrapper = document.getElementById('temas_wrapper');
            var temaContainer = document.createElement('p');

            temaContainer.id = temasWrapper.childElementCount;
            temaContainer.innerHTML = "<input type='text' name='temas' placeholder='Ingrese un tema aquí' autofocus required />"
                    + " <input type='button' value='X' onclick='eliminarTema(" + temasWrapper.childElementCount + ")'/>";

            temasWrapper.appendChild(temaContainer);
        }

        function eliminarTema(indice) {
            var temasWrapper = document.getElementById('temas_wrapper');
            temasWrapper.removeChild(document.getElementById(indice));
        }
    </script>
</body>
</html>
