<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Agendar reunión</h1>


        <form action="Reuniones" method="post">
            titulo <input type="text" name="titulo" value="${modelo.titulo}"><br>
            descripcion <input type="text" name="descripcion" value="${modelo.descripcion}"><br>
            fecha <input type="date" name="fecha" value="${modelo.fecha}"><br>
            hora <input type="time" name="hora" value="${modelo.hora}"><br>
            duracion <input type="text" name="duracion" value="${modelo.duracion}"><br>
            obligatoria <input type="checkbox" name="obligatoria" <c:if test="${modelo.obligatoria}">checked</c:if>><br>
            lugar <input type="text" name="lugar" value="${modelo.lugar}"><br>
            
            <!-- sustituir por tag -->
            <input type="text" id="tema"/><input type="button" value="Agregar" onclick="agregarTema()">
            <textarea name="temas" id="temas" readonly>${modelo.temas}</textarea>
            
            
            <input type="submit" name="accion" value="Agendar">
        </form>
        ${modelo.mensaje}
        
        <!-- sustituir por tag -->
        <script>
            function agregarTema() {
                var input = document.getElementById("tema");
                var tema = input.value;

                if (tema !== "") {
                    var textarea = document.getElementById("temas");
                    textarea.innerHTML += tema + "\n";
                    input.value = "";
                }
            }

        </script>
    </body>
</html>
