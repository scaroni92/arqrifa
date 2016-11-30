<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Arquitectura Rifa | Agendar Reunión</title>
    </head>
    <body>
        <h1>Agendar Reunión</h1>
        <form action="Reuniones" method="post">
            titulo <input type="text" name="titulo" value="${modelo.titulo}" required><br>
            descripcion <input type="text" name="descripcion" value="${modelo.descripcion}" required><br>
            fecha <input type="date" name="fecha" value="${modelo.fecha}" required><br>
            hora <input type="time" name="hora" value="${modelo.hora}" required><br>
            duracion <input type="number" name="duracion" value="${modelo.duracion}" min="30" max="240" required> min<br>
            obligatoria <input type="checkbox" name="obligatoria" <c:if test="${modelo.obligatoria}">checked</c:if>><br>
            lugar <input type="text" name="lugar" value="${modelo.lugar}" required><br>
            <t:cargar_valores_textarea name="temas" valores="${modelo.temas}" />
            <input type="submit" name="accion" value="Agendar">
        </form>
        ${modelo.mensaje}
    </body>
</html>
