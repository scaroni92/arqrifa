var $propuestasWrapper = $('#propuestas-wrapper');

$propuestasWrapper.on('click', '.propuesta-container', function () {
    enfocarPropuesta($(this));
});

$('#agregar-propuesta').on('click focus', function () {
    agregarPropuesta('', '');
});

$('.general-container').on('click', function () {
    $('.propuesta-container').removeClass('card');
});

$('form').on('submit', function (e) {
    var mensaje = '';
    var enviar = true;
    var params = '';

    if ($propuestasWrapper.children().length < 1) {
        enviar = false;
        mensaje = 'Agregue propuestas a la encuesta';
    }

    $.each($propuestasWrapper.children(), function (i, value) {
        var data = $(value).find('.chips').material_chip('data');

        if (data.length >= 2) {
            $.each(data, function (j, val) {
                params += '<input type="hidden" name="respuestas' + i + '" value="' + val.tag + '"></input>';
            });
        } else {
            enviar = false;
            mensaje = 'Las propuestas deben tener almenos dos respuestas';
            $(this).find('input').focus();
        }
    });

    if (enviar) {
        $(this).append(params);
    } else {
        e.preventDefault();
        Materialize.toast(mensaje, 4000);
    }
});

function agregarPropuesta(pregunta, respuestas) {
    var indiceProp = $propuestasWrapper.children().length;

    var $propuestaContainer = $('<div class="row propuesta-container"></div>');

    var btnEliminar = '<a class="icon-btn right" onclick="eliminarPropuesta(this)"><i class="material-icons">delete</i></a>';
    var inputPregunta = '<div class="col s12"><input id="pregunta' + indiceProp + '" name="preguntas" type="text" value="' + pregunta + '" placeholder="Pregunta" required ></input></div>';

    var $chips = $('<div class="chips chips-placeholder"></div>');
    $chips.material_chip({data: respuestas, placeholder: '+Respuestas', secondaryPlaceholder: '+Respuestas'});

    $propuestaContainer.append(btnEliminar, inputPregunta, $('<div class="col s12"></div>').append($chips));
    
    $propuestasWrapper.append($propuestaContainer);    
}

function eliminarPropuesta(btn) {
    $(btn).parent().remove();
}

function enfocarPropuesta(element) {
    if (!$(element)[0].classList.contains('card')) {
        $(element).addClass('card');
        $(element).siblings().removeClass('card');
    }
}


