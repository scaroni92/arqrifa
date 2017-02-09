$('#reunion-form').submit(function () {
    var datos = $('.chips').material_chip('data');
    for (i = 0; i < datos.length; i++) {
        $('#reunion-form').append(" <input type='hidden' name='temas' value='" + datos[i].tag + "'/>");
    }
});

$('#temas-chips').material_chip({
    placeholder: 'Ingrese un tema'
    , secondaryPlaceholder: '+Temas'
});
