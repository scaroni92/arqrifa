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

$form.on('submit', function (e) {
    
    var enviar = true;
    var respuestas = '';

    $.each($propuestasWrapper.children(), function (i, value) {
        var data = $(value).find('.chips').material_chip('data');

        if (data.length < 2) {
            enviar = false;
            $(this).find('input').focus();
        } else {
            $.each(data, function (j, val) {
                respuestas += '<input type="hidden" name="respuestas' + i + '" value="' + val.tag + '"></input>';
            });
        }
    });

    if (enviar) {
        $form.append(respuestas);
    } else {
        e.preventDefault();
        Materialize.toast('Ingrese almenos dos respuestas a la propuesta', 4000);
    }
});



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