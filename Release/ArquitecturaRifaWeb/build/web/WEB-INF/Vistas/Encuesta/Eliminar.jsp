<!-- Esta jsp debería mostrarse como cuadro de dialogo en detalle de reunión-->
<!DOCTYPE html>
<html>
    <head>
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Eliminar encuesta</h1>
        <form action="Encuesta" method="post">
            <p>¿Está segur@ que desea eliminar la encuesta  dela reunión ID: ${modelo.reunionId} ?</p>
            <input type="text" name="reunion_id" value="${modelo.reunionId}" hidden>
            <input type="submit" name="accion" value="Eliminar" />
        </form>
            ${modelo.mensaje}
    </body>
</html>
