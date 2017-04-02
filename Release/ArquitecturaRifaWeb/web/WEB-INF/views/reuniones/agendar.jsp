<%@taglib prefix="t" tagdir="/WEB-INF/tags/" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:masterpage>
    <jsp:body>
        <div class="container">
            <div class="row">
                <form action="reunion" class="col s12 card-panel" method="post" style="padding:40px 30px">
                    <div class="row">
                        <div class="col s12">
                            <h5>Nueva reunión
                                <span class="right"> 
                                    <input type="checkbox" id="obligatoria" name="obligatoria" ${modelo.obligatoria? 'checked' : ''} class="filled-in" />
                                    <label for="obligatoria" class="right">Obligatoria</label>
                                </span>
                            </h5> 
                        </div>
                    </div>
                    <div class="row">
                        <div class="input-field col s12">
                            <input id="titulo" name="titulo" type="text" value="${modelo.titulo}" required>
                            <label for="titulo">Título</label>
                        </div>
                    </div>
                    <div class="row">
                        <div class="input-field col m6 s12">
                            <input id="lugar" name="lugar" type="text" value="${modelo.lugar}" required>
                            <label for="lugar">Lugar</label>
                        </div>
                        <div class="input-field col m2 s12">
                            <input id="fecha" name="fecha" type="date" class="datepicker" value="${modelo.fecha}" required>
                            <label for="fecha">Fecha</label>
                        </div>
                        <div class="input-field col m2 s12" >
                            <input id="hora" name="hora" type="time" value="${modelo.hora}" required>
                            <label for="hora" class="active">Hora</label>
                        </div>
                        <div class="input-field col m2 s12">
                            <input id="duracion" name="duracion" type="number" value="${modelo.duracion}" required>
                            <label for="duracion">Duración (m)</label>
                        </div>
                            
                    </div>
                    <div class="row">
                        <div class="input-field col s12">
                            <textarea id="descripcion" name="descripcion" class="materialize-textarea" data-length="300" required>${modelo.descripcion}</textarea>
                            <label for="descripcion">Descripción</label>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col s12">
                            <div class="chips"></div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="input-field col s12 center">
                            <button class="btn waves-effect waves-light" type="submit" name="accion" value="agendar">Agregar al calendario<i class="material-icons right">event_available</i> </button>
                        </div>
                    </div>
                </form>
            </div>
        </div>

        <script>
            $('form').on('submit', function () {
                $.each($('.chips').material_chip('data'), function (i, val) {
                    $('form').append('<input type="hidden" name="temas" value="' + val.tag + '" />');
                });
            });

            $('.chips').material_chip({
                data: [<c:forEach var="tema" items="${modelo.temas}">{tag: '${tema}'},</c:forEach>],
                placeholder: 'Ingrese un tema',
                secondaryPlaceholder: '+Temas'
            });
         </script>
    </jsp:body>
</t:masterpage>