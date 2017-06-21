<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@attribute name="titulo" %>
<%@attribute name="footer" %>
<%@attribute name="href" %>

<!DOCTYPE html>
<html class="grey lighten-3">
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1.0" /> 
        <title>Arquitectura Rifa | ${titulo}</title>
        <link type="text/css" rel="stylesheet" href="http://fonts.googleapis.com/icon?family=Material+Icons" >
        <link type="text/css" rel="stylesheet" href="css/materialize.css">
        <link type="text/css" rel="stylesheet" href="css/styles.css" />
        <script type="text/javascript" src="js/jquery-3.1.1.min.js"></script>
        <script type="text/javascript" src="js/materialize.min.js"></script>
    </head>
    <body>
        <header>
            <div class="navbar-fixed">
                <nav>
                    <div class="nav-wrapper"> <a id="titulo" href="${href}" class="brand-logo white-text text-darken-1">${titulo}</a>
                        <ul class="right"> <a href="#" data-activates="slide-out" class="button-collapse"><i class="material-icons">menu</i></a> </ul>
                    </div>
                </nav>
            </div>
        </header>

        <main>
            <jsp:doBody />
        </main>

        <!-- Navegación lateral -->
        <ul id="slide-out" class="side-nav fixed">
            <li>
                <div class="userView">
                    <a href="index?accion=perfil"><img class="circle" src="img/male-icon.png"></a> 
                    <a data-activates="profile-dropdown" class="profile-dropdown"><span class="white-text name"><i class="material-icons right">arrow_drop_down</i>${usuario.nombre} ${usuario.apellido}</span></a> 
                    <a ><span class="white-text email">${usuario.rol}</span> </a> 
                </div>
            </li>
            <li><a href="index"><i class="material-icons">home</i>INICIO</a></li>
            <li><a href="calendario"><i class="material-icons">event</i>Calendario</a></li>

            <c:if test="${!(usuario.rol eq 'Estudiante')}">
                <li><div class="divider"></div></li>
                <li><a class="subheader">Tareas</a></li>
                </c:if>

            <!-- Acciones de encargado -->
            <c:if test="${usuario.rol eq 'Encargado'}">
                <li><a class="waves-effect" href="usuarios"><i class="material-icons">school</i>Estudiantes</a></li>
                <li><a class="waves-effect" href="reuniones"><i class="material-icons">event_note</i>Reuniones</a></li>
                <li><a class="waves-effect" href="solicitudes"><i class="material-icons">group_add</i>Solicitudes</a></li>
                <c:if test="${reunionActiva != null}"><li><a class="btn waves-effect cyan white-text" href="panel">Panel de renión</a></li></c:if>
            </c:if>

            <!-- Acciones de administrador -->
            <c:if test="${usuario.rol eq 'Admin'}">
                <li><a class="waves-effect" href="generaciones"><i class="material-icons">wc</i>Generaciones</a></li>
                <li><a class="waves-effect" href="reuniones"><i class="material-icons">event_note</i>Reuniones</a></li>
                <li><a class="waves-effect" href="usuarios"><i class="material-icons">people</i>Integrantes</a></li>
            </c:if>
            
        </ul>

        <!-- Menú desplegable -->
        <ul id='profile-dropdown' class='dropdown-content'>
            <li><a href="index?accion=perfil">Perfil</a></li>
            <li><a href="#!">Información</a></li>
            <li class="divider"></li>
            <li><a href="login?accion=logout">Salir</a></li>
        </ul>

        <c:if test="${footer}">
            <footer class="page-footer">
                <div class="container">
                    <div class="row">
                        <div class="col l6 s12">
                            <h5 class="white-text">Contacto</h5>
                            <p id="asd" class="grey-text text-lighten-4">Puedes contactar con el administrador al correo xxxx@xxx.com </p>
                        </div>
                        <div class="col l4 offset-l2 s12">
                            <h5 class="white-text">Enlaces</h5>
                            <ul>
                                <li>
                                    <a class="grey-text text-lighten-3" href="#!"> <i class="material-icons tiny">phone_android</i> Aplicación móvil</a>
                                </li>
                                <li>
                                    <a class="grey-text text-lighten-3" href="#!"> <i class="material-icons tiny">laptop</i> Apliación de escritorio</a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="footer-copyright">
                    <div class="container">Gestión de Reuniones <a class="orange-text text-lighten-3" href="#">Arquitectura Rifa</a></div>
                </div>
            </footer>
        </c:if>


        <script type="text/javascript" src="js/main.js"></script>
        <script type="text/javascript">
            Materialize.toast('${modelo.mensaje}', 4000);
        </script>
    </body>

</html>