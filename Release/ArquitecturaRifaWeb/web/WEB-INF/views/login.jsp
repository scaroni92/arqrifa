<!DOCTYPE html>
<html class="green darken-1">
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Arquitectura Rifa | Login</title>
        <link rel="shortcut icon" href="favicon-16x16.ico" type="image/x-icon">
        <link rel="stylesheet" href="http://fonts.googleapis.com/icon?family=Material+Icons">
        <link rel="stylesheet" href="css/materialize.css">
        <link rel="stylesheet" href="css/layouts/page-center.css">
        <style>
            .section-register {
                margin-bottom: 40px;
            }

            .divider {
                margin: 30px 0;
            }
        </style>
    </head>
    <body onload="Materialize.toast('${modelo.mensaje}', 4000)">
        <main class="row">
            <div class="col s12 z-depth-4 card-panel grey-text text-darken-2">
                <form action="login" method="post">
                    <div class="row">
                        <div class="input-field col s12 ">
                            <h5 class="text-darken-3">Login</h5> </div>
                    </div>
                    <div class="row">
                        <div class="input-field col s12"> <i class="material-icons prefix">person</i>
                            <input id="ci" type="text" name="ci" maxlength="7" pattern="\d*" required>
                            <label for="ci">Cédula</label>
                        </div>
                    </div>
                    <div class="row">
                        <div class="input-field col s12"> <i class="material-icons prefix">vpn_key</i>
                            <input id="password" type="password" name="pass" required>
                            <label for="password">Contraseña</label>
                        </div>
                    </div>
                    <div class="center">
                        <button class="btn waves-effect waves-light green" type="submit">ingresar</button>
                    </div>
                    <div class="divider"></div>
                    <div class="row section-register">
                        <div class="input-field  center">
                            <a href="registro" class="btn white black-text ">registrarse</a>
                        </div>
                    </div>
                </form>
            </div>
        </main>
        <script type="text/javascript" src="js/jquery-3.1.1.min.js"></script>
        <script type="text/javascript" src="js/materialize.min.js"></script>
    </body>
</html>