<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@attribute name="titulo" %>
<%@attribute name="footer" %>

<!DOCTYPE html>
<html class="grey lighten-3">
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1.0" /> 
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
                    <div class="nav-wrapper"> <a id="titulo" href="#!" class="brand-logo white-text text-darken-1">${titulo}</a>
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
                    <a href="usuario?accion=perfil"><img class="circle" src="img/male-icon.png"></a> 
                    <a data-activates="profile-dropdown" class="profile-dropdown"><span class="white-text name"><i class="material-icons right">arrow_drop_down</i>${usuario.nombre} ${usuario.apellido}</span></a> 
                    <a ><span class="white-text email">${usuario.rol}</span> </a> 
                </div>
            </li>
            <li><a href="index"><i class="material-icons">home</i>INICIO</a></li>
            <li><a href="usuario?accion=ver-calendario"><i class="material-icons">event</i>Calendario</a></li>

            <c:if test="${!(usuario.rol eq 'Estudiante')}">
                <li><div class="divider"></div></li>
                <li><a class="subheader">Tareas</a></li>
            </c:if>
                
            <!-- Acciones de encargado -->
            <c:if test="${usuario.rol eq 'Encargado'}">
                <li><a class="waves-effect" href="reunion?accion=agendar"><i class="material-icons">event_note</i>Agendar</a></li>
                <li><a class="waves-effect" href="encargado?accion=listar-reuniones"><i class="material-icons">date_range</i>Reuniones</a></li>
                <li><a class="waves-effect" href="encargado?accion=listar-solicitudes"><i class="material-icons">group_add</i>Solicitudes</a></li>
                <li><a class="waves-effect" href="encargado?accion=listar-estudiantes"><i class="material-icons">group</i>Estudiantes</a></li>
            </c:if>
                
            <!-- Acciones de administrador -->
            <c:if test="${usuario.rol eq 'Admin'}">
                <li><a class="waves-effect" href="admin?accion=listar-reuniones"><i class="material-icons">date_range</i>Reuniones</a></li>
                <li><a class="waves-effect" href="generaciones"><i class="material-icons">wc</i>Generaciones</a></li>
                <li><a class="waves-effect" href="admin?accion=listar-usuarios"><i class="material-icons">people</i>Usuarios</a></li>
            </c:if>
        </ul>

        <!-- Menú desplegable -->
        <ul id='profile-dropdown' class='dropdown-content'>
            <li><a href="usuario?accion=perfil">Perfil</a></li>
            <li><a href="#!">Información</a></li>
            <li class="divider"></li>
            <li><a href="usuario?accion=salir">Salir</a></li>
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
                    <div class="container"> © 2017 Arquitectura Rifa </div>
                </div>
            </footer>
        </c:if>


        <script type="text/javascript" src="js/main.js"></script>
        <script>
            Materialize.toast('${modelo.mensaje}', 4000);
            $('.toolbar ul li a').tooltip({delay: 50, position: 'top'});
        </script>
    </body>

</html>