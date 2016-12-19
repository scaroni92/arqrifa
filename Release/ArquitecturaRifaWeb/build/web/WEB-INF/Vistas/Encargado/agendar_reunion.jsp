<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Arquitectura Rifa | Agendar Reunión</title>
    </head>
    <body>
        
        <form action="Reuniones" method="post">
            <p>
                <input type="submit" name="accion" value="AGENDAR">
                <input type="submit" name="accion" value="DESCARTAR">
            </p>
            <p>
                Título <input type="text" name="titulo" value="${modelo.titulo}" required><br>
                Descripción <input type="text" name="descripcion" value="${modelo.descripcion}" required>
            </p>
            
            <p>
            <table>
                <tr>
                    <td>Fecha<br><input type="date" name="fecha" value="${modelo.fecha}" required>
                        <input type="time" name="hora" value="${modelo.hora}" required>
                    </td>
                    <td>
                        Duración<br><input type="number" name="duracion" value="${modelo.duracion}" min="30" max="240" required>
                    </td>
                </tr>
                <tr>
                    <td>Lugar<br><input type="text" name="lugar" value="${modelo.lugar}" required></td>
                    <td>
                        <input type="checkbox" id="obligatoria" name="obligatoria" <c:if test="${modelo.obligatoria}">checked</c:if>>
                        <label for="obligatoria" >Marcar como obligatoria</label>
                    </td>
                </tr>
            </table>

            <t:cargar_valores_textarea name="temas" valores="${modelo.temas}" />
        </form>
        ${modelo.mensaje}
    </body>
</html>
