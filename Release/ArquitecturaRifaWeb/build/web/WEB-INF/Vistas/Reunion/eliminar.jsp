<!DOCTYPE html>
<html>
    <head>
        <title>JSP Page</title>
    </head>
    <body>
        <p>Esta página podría ser un cuadro de diálogo en el calendario</p>
        <h3>Eliminar reunión</h3>
        <p>¿Está segur@ que desea eliminar la reunión con ID: ${modelo.id}?</p>
        <form action="Reuniones" method="post">
            <input type="text" name="id" value="${modelo.id}" hidden />
            <input type="submit" name="accion" value="Eliminar" />
        </form>
        ${modelo.mensaje}
    </body>
</html>
