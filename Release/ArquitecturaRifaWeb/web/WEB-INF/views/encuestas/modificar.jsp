<%@taglib  prefix="t" tagdir="/WEB-INF/tags/" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="encuesta" scope="page" value="${reunion.encuesta}"/>
<t:masterpage titulo="Agregar">
    <jsp:body>
        <div class="container">
            <div class="row">
                <form action="encuesta" class="col s12 card-panel" style="padding:30px;" method="post">
                    <div class="row">
                        <div class="col s12">
                            <h5>Modificar encuesta</h5> </div>
                    </div>
                    <div class="row general-container">
                        <div class="input-field col m10 s12">
                            <input id="titulo" name="titulo" type="text" value="${encuesta.titulo}"  style="font-size: 2em;" required>
                            <label for="titulo">Título</label>
                        </div>
                        <div class="input-field col m2 s12">
                            <input id="duracion" name="duracion" type="number" value="${encuesta.duracion}" required>
                            <label for="duracion">Duración</label>
                        </div>
                    </div>
                    <div id="propuestas-wrapper">
                    </div>
                    <div class="row">
                        <div class="col">
                            <input id="agregar-propuesta" type="text" placeholder="Agregar propuesta"> </div>
                    </div>
                    <div class="row">
                        <div class="input-field col s12 center">
                            <button class="btn waves-effect waves-light" type="submit" name="accion" value="modificar">guardar</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>


        <script>
            var $propuestasWrapper = $('#propuestas-wrapper');
            var $form = $('form');

            $('#agregar-propuesta').on('click focus', function () {
                agregarPropuesta('', '');
            });

            $propuestasWrapper.on('click', 'span', function () {
                $(this).parent().parent().remove();
            });

            $propuestasWrapper.on('click focus', '.propuesta-container', function () {
                $(this).addClass('card');
                $(this).siblings().removeClass('card');
            });

            $('.general-container').on('click', function () {
                $('.propuesta-container').removeClass('card');
            });

            $form.on('submit', function () {
                // Se crean los parametros de las respuestas
                $.each($propuestasWrapper.children(), function (i, value) {
                    $.each($(value).find('.chips').material_chip('data'), function (j, val) {
                        $form.append('<input type="hidden" name="respuestas' + i + '" value="' + val.tag + '"></input>');
                    });
                });
            });

            <c:forEach var="propuesta" items="${reunion.encuesta.propuestas}">
            var respuestas = [<c:forEach var="respuesta" items="${propuesta.respuestas}">{tag: '${respuesta.respuesta}'},</c:forEach>];
            agregarPropuesta('${propuesta.pregunta}', respuestas);
            </c:forEach>

            function agregarPropuesta(pregunta, respuestas) {
                var indiceProp = $propuestasWrapper.children().length;

                var $propuestaContainer = $('<div></div>').addClass('row propuesta-container');
                var $preguntaContainer = $('<div></div>').addClass('input-field col s12').append($('<span class="icon-btn right"><i class="material-icons">delete</i></span>'),
                        $('<input id="pregunta' + indiceProp + '" name="preguntas" type="text" value="' + pregunta + '" required ></input><label for="pregunta' + indiceProp + '">Propuesta ' + (+indiceProp + 1) + '</label>'));
                var $chips = $('<div class="chips chips-placeholder"></div>');

                $propuestaContainer.append($preguntaContainer, $('<div class="col s12"></div>').append($chips));
                $propuestasWrapper.append($propuestaContainer);

                $chips.material_chip({data: respuestas, placeholder: '+Respuestas', secondaryPlaceholder: '+Respuestas'});
                $propuestaContainer.trigger('click');
            }
        </script>
    </jsp:body>
</t:masterpage>