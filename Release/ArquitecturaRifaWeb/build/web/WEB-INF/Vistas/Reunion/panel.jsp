<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Arquitectura Rifa | Panel</title>
        <style>
            #observaciones, #resolucion, #resoluciones {
                width: 400px;
            }
        </style>
    </head>

    <body>
        <h1>Panel</h1>
        <form action="Reuniones"  method="post">  
            <fieldset>
                <label style="text-align:center; display:block;">${modelo.titulo}</label><br>
                <p>Fecha y hora de inicio: ${modelo.fecha} ${modelo.hora}</p>
                <label style="float:right;">Estado: <span style="font-weight: bolder;">${modelo.estado}</span></label>

                <h3>Temas</h3>              
                <c:forEach var="tema" items="${modelo.temas}" >
                    ${tema}<br>
                </c:forEach>               
                <p><a href="Reuniones?accion=ver&id=${modelo.id}">Ver detalles</a></p>
            </fieldset>
            <p>${modelo.mensaje}</p>
            <c:if test="${modelo.estado == 'Pendiente'}">
                <p><input type="submit" name="accion" value="Iniciar"></p>
                </c:if>
                <c:if test="${modelo.estado == 'Iniciada'}">
                <fieldset>
                    <legend>Recapitulación</legend>
                    Observaciones<br><textarea type="text" name="observaciones" id="observaciones" required>${modelo.observaciones}</textarea><br>
                    Resoluciones<br>


                    <div id="resoluciones-wrapper">
                        <c:if test="${empty modelo.resoluciones}">
                            <p id="0">
                                <input type="text" name="resoluciones" placeholder="Ingrese una resolución aquí" required>
                                <input type="button" value="X" onclick="eliminarResolucion(0)" />
                            <p>
                        </c:if>
                        <c:forEach var="resolucion" items="${modelo.resoluciones}" varStatus="contador">
                            <p id="${contador.index}">
                                <input type="text" name="resoluciones" placeholder="Ingrese una resolución aquí" value="${resolucion}" required>
                                <input type="button" value="X" onclick="eliminarResolucion(${contador.index})" />
                            <p>
                        </c:forEach>
                    </div>
                    
                    <input type="button" value="Nueva resolucion" onclick="agregarResolucion()"/>
                    <p><input type="submit" name="accion" value="Finalizar"></p>
                </fieldset>
            </c:if>
            <t:info_reunion/>
        </form>
        <script>
            function agregarResolucion() {
                var resWrapper = document.getElementById('resoluciones-wrapper');
                var resContainer = document.createElement('p');

                resContainer.id = resWrapper.childElementCount;
                resContainer.innerHTML = "<input type='text' name='resoluciones' placeholder='Ingrese una resolución aquí' autofocus required />"
                        + " <input type='button' value='X' onclick='eliminarResolucion(" + resWrapper.childElementCount + ")'/>";

                resWrapper.appendChild(resContainer);
            }

            function eliminarResolucion(indice) {
                if (indice > 0) {
                    var resWrapper = document.getElementById('resoluciones-wrapper');
                    resWrapper.removeChild(document.getElementById(indice));
                }
            }
        </script>
    </body>
</html>

