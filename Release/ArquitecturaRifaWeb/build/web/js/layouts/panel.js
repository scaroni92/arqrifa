$('#panel-form').submit(function () {
    var datos = $('.chips').material_chip('data');
    for (i = 0; i < datos.length; i++) {
        $('#panel-form').append(" <input type='hidden' name='resoluciones' value='" + datos[i].tag + "'/>");
    }
});
$('#resoluciones-chips').material_chip({
    placeholder: '+Resolucion'
    , secondaryPlaceholder: 'Resoluciones'
});
