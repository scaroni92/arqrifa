<%@taglib prefix="t" tagdir="/WEB-INF/tags/" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<t:masterpage titulo="INICIO" footer="true">
    <jsp:body>
            <div class="slider">
                <ul class="slides">
                    <li> 
                    <img src="img/slider3.jpg">
                    <div class="caption center-align">
                        <h3 >Calendario</h3>
                        <h5 class="light grey-text text-lighten-3">Visite el calendario de reuniones</h5>
                        <a href="calendario" class="btn red white-text waves-effect waves-red">consultar</a>
                    </div>
                </li>
                <li> 
                    <img src="img/slider1.jpeg">
                    <div class="caption left-align">
                        <h3>Próxima reunión <fmt:formatDate pattern="d 'de' MMMM HH:mm" value="${modelo.proximaReunion.fecha}" /></h3>
                        <h5 class="light hide-on-small-only light">${modelo.proximaReunion.descripcion}</h5>
                        <a href="reuniones?accion=detalles&id=${modelo.proximaReunion.id}" class="btn-flat white-text waves-effect waves-yellow ">detalles</a> 
                    </div>
                </li>
                <li> 
                    <img src="img/slider2.jpg">
                    <div class="caption right-align">
                        <h3>Últimas Resoluciones</h3>
                        <c:forEach var="resolucion" items="${modelo.ultimaReunion.resoluciones}">
                            <h5 class="light grey-text text-lighten-3 hide-on-small-only">${resolucion}</h5>
                        </c:forEach>
                        <a href="reuniones?accion=detalles&id=${modelo.ultimaReunion.id}" class="btn-flat white-text waves-effect waves-white">detalles</a> 
                    </div>
                </li>
                    <li> <img src="img/slider4.jpeg">
                        <div class="caption center-align">
                            <h3>Arquitectura Rifa Móvil</h3>
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
            $('.slider').slider({interval: 6000});
        </script>
    </jsp:body>
</t:masterpage>

