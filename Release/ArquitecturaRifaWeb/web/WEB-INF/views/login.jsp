<!DOCTYPE html>
<html class="teal lighten-2">

<head>
    <link rel="stylesheet" href="http://fonts.googleapis.com/icon?family=Material+Icons">
    <link rel="stylesheet" href="css/materialize.min.css">
    <link rel="stylesheet" href="css/layouts/page-center.css">
    <meta name="viewport" content="width=device-width, initial-scale=1.0" /> </head>

<body onload="Materialize.toast('${modelo.mensaje}', 4000)">
    <main class="row">
        <div class="col s12 z-depth-4 card-panel grey-text text-darken-2">
            <form action="usuario" method="post">
                <div class="row">
                    <div class="input-field col s12 center"> <img src="img/login-icon.png" alt="" class="circle responsive-img valign profile-image-login">
                        <h5 class="text-darken-3">Arquitectura Rifa</h5> </div>
                </div>
                <div class="row">
                    <div class="input-field col s12"> <i class="material-icons prefix">account_circle</i>
                        <input id="ci" type="text" name="ci" required >
                        <label for="ci">Cédula</label>
                    </div>
                </div>
                <div class="row">
                    <div class="input-field col s12"> <i class="material-icons prefix">lock</i>
                        <input id="password" type="password" name="pass" required>
                        <label for="password">Contraseña</label>
                    </div>
                </div>
                <div class="row">
                    <div class="input-field col s12">
                        <button class="btn waves-effect waves-light col s12" type="submit" name="accion" value="ingresar">INGRESAR</button>
                    </div>
                </div>
                <div class="row">
                    <div class="input-field col s6"> <a href="usuario?accion=registro">Registrarse</a> </div>
                </div>
            </form>
        </div>
    </main>
    <script type="text/javascript" src="js/jquery-3.1.1.min.js"></script>
    <script type="text/javascript" src="js/materialize.min.js"></script>
</body>

</html>