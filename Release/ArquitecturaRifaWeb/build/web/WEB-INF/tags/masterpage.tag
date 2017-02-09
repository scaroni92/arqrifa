<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@attribute name="titulo" %>

<!DOCTYPE html>
<html class="teal lighten-5">

    <head>
        <link href="http://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
        <link type="text/css" rel="stylesheet" href="css/materialize.min.css">
        <link type="text/css" rel="stylesheet" href="css/styles.css" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" /> 
    </head>

    <body onload="Materialize.toast('${modelo.mensaje}', 4000)">
        <header>
            <div class="navbar-fixed">
                <nav>
                    <div class="nav-wrapper"> <a id="titulo" href="#!" class="brand-logo white-text text-darken-1">${titulo}</a>
                        <ul class="right"> <a href="#" data-activates="slide-out" class="button-collapse"><i class="material-icons">menu</i></a> </ul>
                    </div>
                </nav>
            </div>
        </header>

        <main>
            <jsp:doBody />
        </main>


        <!-- Navegación Lateral -->
        <ul id="slide-out" class="side-nav fixed">
            <li>
                <div class="userView">
                    <a href="usuario?accion=perfil"><img class="circle" src="img/male-icon.png"></a> 
                    <a data-activates="profile-dropdown" class="profile-dropdown"><span class="white-text name"><i class="material-icons right">arrow_drop_down</i>${usuario.nombre} ${usuario.apellido}</span></a> 
                    <a ><span class="white-text email">${usuario.rol}</span> </a> 
                </div>
            </li>
            <li><a href="usuario"><i class="material-icons">home</i>INICIO</a></li>
            <li><a href="usuario?accion=ver-calendario"><i class="material-icons">event</i>Calendario</a></li>
            <li>
                <div class="divider"></div>
            </li>
            <li><a class="subheader">Tareas</a></li>
            <!-- OPCIONES DE ENCARGADO -->
            <c:if test="${usuario.rol eq 'Encargado'}">
                <li><a class="waves-effect" href="reunion?accion=agendar"><i class="material-icons">add</i>Agendar reunión</a></li>
                <li><a class="waves-effect" href="encargado?accion=listar-solicitudes"><i class="material-icons">group_add</i>Listar solicitudes</a></li>
                <li><a class="waves-effect" href="encargado?accion=listar-estudiantes"><i class="material-icons">group</i>Listar estudiantes</a></li>
                <li><a class="waves-effect" href="encargado?accion=listar-reuniones"><i class="material-icons">list</i>Listar reuniones</a></li>
                </c:if>

            <!-- OPCIONES DE ADMINISTRADOR -->
            <c:if test="${usuario.rol eq 'Admin'}">
                <li><a class="waves-effect" href="admin?accion=listar-usuarios"><i class="material-icons">people</i>Listar usuarios</a></li>
                <li><a class="waves-effect" href="admin?accion=agregar-usuario"><i class="material-icons">person_add</i>Agregar usuario</a></li>
                <li><a class="waves-effect" href="generaciones"><i class="material-icons">add</i>Agregar generación</a></li>
                <li><a class="waves-effect" href="admin?accion=listar-reuniones"><i class="material-icons">list</i>Listar reuniones</a></li>
                </c:if>
        </ul>

        <!-- Menú desplegable -->
        <ul id='profile-dropdown' class='dropdown-content'>
            <li><a href="usuario?accion=perfil">Perfil</a></li>
            <li><a href="#!">Información</a></li>
            <li class="divider"></li>
            <li><a href="usuario?accion=salir">Salir</a></li>
        </ul>


        <script type="text/javascript" src="js/jquery-3.1.1.min.js"></script>
        <script type="text/javascript" src="js/materialize.min.js"></script>
        <script type="text/javascript" src="js/main.js"></script>
        <script type="text/javascript" src="js/layouts/reunion.js"></script>
        <script type="text/javascript" src="js/layouts/panel.js"></script>
        <script type="text/javascript" src="js/layouts/encuesta.js"></script>
        <script>
        //mover a main.js
        function eliminar(id) {
            $('#' + id).remove();
        }

        //cambiar en materialze.js
        $('.datepicker').pickadate({
            selectMonths: true
            , min: true
            , format: 'yyyy-mm-dd'
        });

        $(document).ready(function () {
            $('select').material_select();
            $('.modal').modal();
            //$('.fixed-action-btn.toolbar').openToolbar();
        });
        </script>

    </body>

</html>