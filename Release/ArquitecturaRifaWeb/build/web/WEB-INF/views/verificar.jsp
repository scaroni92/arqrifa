<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html class="green darken-1">

<head>
    <link href="http://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link rel="stylesheet" href="css/materialize.css">
    <link rel="stylesheet" href="css/layouts/page-center.css">
    <meta name="viewport" content="width=device-width, initial-scale=1.0" /> 
</head>

<body>
    <main class="row">
        <div class="col s12 ">
            <div class="card">
                <div class="card-image blue .card-block" style="height:100px;">
                    <h5 class="card-title">${modelo.verificada? 'Éxito':'Error'}</h5> <a href="index" class="btn-floating halfway-fab waves-effect waves-light red"><i class="material-icons">input</i></a> </div>
                <div class="card-content">
                    <h5>${modelo.mensaje}</h5>
                    <p>${modelo.descripcion}</p>
                </div>
            </div>
        </div>
    </main>
    <script type="text/javascript" src="js/jquery-2.1.1.min.js"></script>
    <script type="text/javascript" src="/js/materialize.min.js"></script>
</body>

</html>
