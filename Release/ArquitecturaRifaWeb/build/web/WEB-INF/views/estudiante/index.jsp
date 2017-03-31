<%@taglib prefix="t" tagdir="/WEB-INF/tags/" %>
<t:masterpage titulo="INICIO">
    <jsp:body>
        <div class="slider">
            <ul class="slides">
                <li> <img src="img/slider1.jpeg">
                    <div class="caption left-align">
                        <h3>Próxima reunión 02/10/16</h3>
                        <h5 class="light">Lorem ipsum dolor sit amet, consectetur adipisicing elit. Aspernatur sequi impedit ab tempore officiis vero veniam doloremque placeat magnam deserunt amet saepe, accusantium voluptatum eveniet nam quaerat modi maxime provident.</h5> <a href="#" class="btn-flat white-text waves-effect waves-yellow ">detalles</a> </div>
                </li>
                <li> <img src="img/slider2.jpg">
                    <div class="caption right-align">
                        <h3>Últimas Resoluciones</h3>
                        <h5 class="light grey-text text-lighten-3">Lorem ipsum dolor sit amet, consectetur adipisicing elit. Quo ad optio in sunt officia a nam debitis laudantium officiis omnis qui, quos dignissimos provident, necessitatibus maiores, eos rerum deserunt? Delectus.</h5> <a href="#" class="btn-flat white-text waves-effect waves-white">detalles</a> </div>
                </li>
                <li class="red"> <img src="/img/calendarç.jpeg">
                    <div class="caption center-align ">
                        <h3>Calendario</h3>
                        <p><i class="material-icons large">date_range</i></p>
                        <h5 class="light grey-text text-lighten-3">Visite el calendario de reuniones</h5> <a href="#" class="btn-flat white-text waves-effect waves-green">consultar</a> </div>
                </li>
                <li> <img src="img/slider4.jpeg">
                    <div class="caption center-align">
                        <h3>Aplicación Móvil</h3>
                        <h5 class="light grey-text text-lighten-3">Disponible para Android</h5>
                        <div class="btn">Descargar ahora</div>
                    </div>
                </li>
            </ul>
        </div>
        <br>
        <br>
        <br>
        <p class="flow-text center">Algún contenido</p>
        <div class="divider"></div>
        <script>
            $(document).ready(function () {
                $('.slider').slider({
                    interval: 116000
                });
            });
        </script>
    </jsp:body>
</t:masterpage>

