<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                    Observaciones<br> 
                    <textarea type="text" name="observaciones" id="observaciones" required>${modelo.observaciones}</textarea><br>
                    Resoluciones<br>
                    <textarea type="text" name="resoluciones" id="resoluciones" readonly>${modelo.resoluciones}</textarea><br> 
                    <input type="text" id="resolucion" placeholder="Ingrese aquí las resoluciones" ><a href="#" onclick="agregarResolucion()">Agregar</a>
                    <p><input type="submit" name="accion" value="Finalizar"></p>
                </fieldset>
            </c:if>
            <t:info_reunion/>
        </form>
        <script>
            function agregarResolucion() {
                var txtResolucion = document.getElementById("resolucion");

                if (txtResolucion.value !== "") {
                    document.getElementById("resoluciones").innerHTML += txtResolucion.value + "\n";
                    txtResolucion.value = "";
                }
            }
        </script>
    </body>
</html>

