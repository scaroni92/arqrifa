<!DOCTYPE html>
<html class="green darken-1">

    <head>
        <link rel="stylesheet" href="http://fonts.googleapis.com/icon?family=Material+Icons">
        <link rel="stylesheet" href="css/materialize.css">
        <link rel="stylesheet" href="css/layouts/page-center.css">
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
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
                <form action="index" method="post">
                    <div class="row">
                        <div class="input-field col s12 ">
                            <h5 class="text-darken-3">Login</h5> </div>
                    </div>
                    <div class="row">
                        <div class="input-field col s12"> <i class="material-icons prefix">person</i>
                            <input id="ci" type="number" name="ci" required>
                            <label for="ci">C�dula</label>
                        </div>
                    </div>
                    <div class="row">
                        <div class="input-field col s12"> <i class="material-icons prefix">vpn_key</i>
                            <input id="password" type="password" name="pass" required>
                            <label for="password">Contrase�a</label>
                        </div>
                    </div>
                    <div class="center">
                        <button class="btn waves-effect waves-light green" type="submit" name="accion" value="ingresar">INGRESAR</button>
                    </div>
                    <div class="divider"></div>
                    <div class="row section-register">
                        <div class="input-field  center">
                            <a href="index?accion=registro" class="btn white black-text ">registrarse</a>
                        </div>
                    </div>
                </form>
            </div>
        </main>
        <script type="text/javascript" src="js/jquery-3.1.1.min.js"></script>
        <script type="text/javascript" src="js/materialize.min.js"></script>
    </body>

</html>