
$('#agregar-propuesta').click(function () {
    var propContainer = $('#propuestas-container');
    var propuestaIndex = propContainer.children().length + 1;
    var containerID = "propuesta-container" + propuestaIndex;
    var propuestaHTML = "<div id='" + containerID + "' class='row propuesta-container'><div class='input-field col s10'><input id='propuesta" + propuestaIndex + "' name='preguntas' type='text'><label for='propuesta" + propuestaIndex + "'>Pregunta</label></div><div class='col s2'><span class='icon-btn right'><i class='material-icons' onclick=\"eliminarPropuesta('" + containerID + "')\">close</i></span> </div><div class='col s12'><div class='chips chips-placeholder' id='chips" + containerID + "'></div> </div></div>";
    propContainer.append(propuestaHTML);
    $("#" + containerID + " input").focus();
    $('#chips' + containerID).material_chip({
        placeholder: '+Respuestas'
        , secondaryPlaceholder: '+Respuestas'
    });

});

function eliminarPropuesta(id) {
    if ($('#propuestas-container').children().length > 1) {
        $("#" + id).remove();
    }
}


//.submit()
function crearParametros() {
    var propContainers = $('#propuestas-container').children();

    for (var i = 0; i < propContainers.length; i++) {
        //comprobar que los campos no esten vacios
        var datos = $('#' + propContainers[i].id).find('.chips').material_chip('data');
        for (var j = 0; j < datos.length; j++) {
            $('#encuesta-form').append(" <input type='hidden' name='respuestas" + (i + 1) + "' value='" + datos[j].tag + "'/>");
        }
    }
}

