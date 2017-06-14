var $form = $('form:eq(1)');
var $actions = $('#actions');
var $btnIniciarReunion = $('#iniciar-reunion');
var $btnFinalizarReunion = $('#finalizar-reunion');
var $btnIniciarLista = $('#habilitar-lista');
var $btnFinalizarLista = $('#deshabilitar-lista');
var $btnAsistencias = $('#asistencias');
var $btnIniciarEncuesta = $('#habilitar-votacion');
var $btnFinalizarEncuesta = $('#deshabilitar-votacion');
var $btnCuestionario = $('#cuestionario');


function mostrarBotones(estado) {

    $actions.children().hide();

    switch (estado) {
        case 'Pendiente':
            $btnIniciarReunion.show();
            break;
        case 'Iniciada':
            $btnIniciarLista.show();
            $btnIniciarEncuesta.show();
            $btnFinalizarReunion.show();
            break;
        case 'Listado':
            $btnFinalizarLista.show();
            $btnAsistencias.show();
            break;
        case 'Votacion':
            $btnFinalizarEncuesta.show();
            $btnCuestionario.show();
            break;
    }
}

$form.on('submit', function (e) {
    var data = $('.chips').material_chip('data');
    if (data.length === 0) {
        e.preventDefault();
        Materialize.toast('Debe agregar las resoluciones alcanzadas', 4000);
        $('.chips input').focus();
        return;
    }
    $.each(data, function (i, val) {
        $form.append('<input type="hidden" name="resoluciones" value="' + val.tag + '" />');
    });
    $form.append('<input type="hidden" name="observaciones" value="' + $('textarea').val() + '" />');
});