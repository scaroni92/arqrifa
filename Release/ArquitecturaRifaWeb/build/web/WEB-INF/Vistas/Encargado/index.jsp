<!DOCTYPE html>
<html>
    <head>
        <title>Arquitectura Rifa | INICIO</title>
    </head>
    <body>
        <h1>Inicio Encargado</h1>
        <h2>Nombre : ${usuario.nombre} ${usuario.apellido}</h2>
        <h3>Generación: ${usuario.generacion}</h3>
        <a href="Encargados?accion=ver_solicitudes">Listar solicitudes</a>
        <a href="Reuniones?accion=agendar">Agendar reunion</a>
        <p>${modelo.mensaje}</p>
        <br>
        <br>
        <a href="Reuniones?accion=proxima">Próxima reunión</a>
        <a href="Reuniones?accion=resumen">Resumen de última reunión</a>
        <a href="Reuniones?accion=calendario">Ver calendario</a>
    </body>
</html>
