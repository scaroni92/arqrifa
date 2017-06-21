$('#propuestas-wrapper').on('click', '.propuesta-container', function () {
    focusPropuesta($(this));
});

$('#agregar-propuesta').on('click focus', function () {
    addPropuesta('', '');
});

$('.general-container').on('click', function () {
    $('.propuesta-container').removeClass('card z-depth-2');
});

$('form').on('submit', function (e) {
    var $propuestasContainer = $('#propuestas-wrapper').children();

    // Verifica cantidad de propuestas
    if ($propuestasContainer.length < 1) {
        preventSubmit('Agregue propuestas a la encuesta');
        return;
    }

    function preventSubmit(mensaje) {
        e.preventDefault();
        Materialize.toast(mensaje, 4000);
    }
    
    // Crea inputs con los valores de las respuestas para enviarlas como parámetros 
    var params = '';
    $.each($propuestasContainer, function (i, value) {
        var data = $(value).find('.chips').material_chip('data');
        if (data < 2) {
            preventSubmit('Las propuestas deben tener almenos dos respuestas');
            $(this).find('input').focus();
            return;
        }
        $.each(data, function (j, val) {
            params += '<input type="hidden" name="respuestas' + i + '" value="' + val.tag + '"></input>';
        });
    });
    $(this).append(params);
});

function addPropuesta(pregunta, respuestas) {
    var $propuestasWrapper = $('#propuestas-wrapper');
    var indiceProp = $propuestasWrapper.children().length;
    var inputPreguntaId = "pregunta" + indiceProp;

    // Estructura HTML de la propuesta
    var $propuestaContainer = $('<div class="row propuesta-container"></div>');
    var btnEliminar = '<a class="icon-btn right" onclick="deletePropuesta(this)"><i class="material-icons">delete</i></a>';
    var inputPregunta = '<div class="col s12"><input id="' + inputPreguntaId + '" name="preguntas" type="text" value="' + pregunta + '" placeholder="Pregunta" required ></input></div>';
    var $chips = $('<div class="chips chips-placeholder"></div>');
    var chipsContainer = $('<div class="col s12"></div>').append($chips);
    
    $chips.material_chip({data: respuestas, placeholder: '+Respuestas', secondaryPlaceholder: '+Respuestas'});

    $propuestaContainer.append(btnEliminar, inputPregunta, chipsContainer);
    $propuestasWrapper.append($propuestaContainer);

    // Si la propuesta es nueva se le establece el foco
    if (pregunta === '') {
        $("#" + inputPreguntaId).trigger("click");
    }
}

function deletePropuesta(element) {
    $(element).parent().remove();
}

function focusPropuesta(element) {
    if (!$(element)[0].classList.contains('card')) {
        $(element).addClass('card z-depth-2');
        $(element).siblings().removeClass('card z-depth-2');
        $(element).find("input[type=text]").focus();
    }
}


