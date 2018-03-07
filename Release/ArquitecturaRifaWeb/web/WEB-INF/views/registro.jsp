<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html class="green darken-1-2">
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Arquitectura Rifa | Registro</title>
        <link rel="shortcut icon" href="favicon-16x16.ico" type="image/x-icon">
        <link rel="stylesheet" href="http://fonts.googleapis.com/icon?family=Material+Icons">
        <link rel="stylesheet" href="css/materialize.css">
        <link rel="stylesheet" href="css/materialize.min.css">
         </head>
    <style>
        #registro-form{
            margin: auto;
            margin-top: 20px;
            max-width: 400px;
        }
    </style>
    <body onload="Materialize.toast('${modelo.mensaje}', 4000)">
        <main>
            <div class="z-depth-4 card-panel grey-text text-darken-2" id="registro-form">
                <form action="registro" method="post">
                    <h5 class="text-darken-3 ">Registro</h5>
                    <div class="row">
                        <div class="input-field col s12">
                            <input id="ci" type="text" name="ci" maxlength="7" pattern="\d*" title="Num�rico" required>
                            <label for="ci">C�dula</label>
                        </div>
                    </div>
                    <div class="row">
                        <div class="input-field col s12">
                            <input id="nombre" name="nombre" value="${modelo.nombre}" type="text" required>
                            <label for="nombre">Nombre</label>
                        </div>
                    </div>
                    <div class="row">
                        <div class="input-field col s12">
                            <input id="apellido" name="apellido" value="${modelo.apellido}" type="text" required>
                            <label for="apellido">Apellido</label>
                        </div>
                    </div>
                    <div class="row">
                        <div class="input-field col s12">
                            <input id="email" name="email" value="${modelo.email}" type="email" required>
                            <label for="email">Email</label>
                        </div>
                    </div>
                    <div class="row">
                        <div class="input-field col s12">
                            <input id="pass" name="contrasena" value="${modelo.contrasena}" type="password" required>
                            <label for="pass">Contrase�a</label>
                        </div>
                    </div>
                    <div class="row">
                        <div class="input-field col  s12">
                            <select name="generacion">
                                <c:forEach var="generacion" items="${modelo.generaciones}">
                                    <option value="${generacion.id}" ${generacion.id eq modelo.generacion? 'selected' : ''}>${generacion.id}</option>
                                </c:forEach>

                            </select>
                            <label>Escoga una generaci�n</label>
                        </div>
                    </div>
                    <div class="row">
                        <div class="input-field col s12">
                            <button class="btn waves-effect waves-light col s12" type="submit">registrarse</button>
                        </div>
                    </div>
                    <div class="divider"></div>
                    <div class="row">
                        <div class="input-field  center">
                            <a href="login" class="btn white black-text ">Ingresar</a>
                        </div>
                    </div>
                </form>
            </div>
        </main>
        <script type="text/javascript" src="js/jquery-3.1.1.min.js"></script>
        <script type="text/javascript" src="js/materialize.min.js"></script>
        <script>
        $(document).ready(function () {
            $('select').material_select();
        });
        </script>
    </body>

</html>