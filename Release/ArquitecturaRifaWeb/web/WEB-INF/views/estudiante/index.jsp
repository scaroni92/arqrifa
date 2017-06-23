<%@taglib prefix="t" tagdir="/WEB-INF/tags/" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<t:masterpage titulo="INICIO" footer="true">
    <jsp:body>
        <div class="slider">
            <ul class="slides">
                <li> 
                    <img src="img/slider1.jpg">
                    <div class="caption center-align">
                        <h3 >Calendario</h3>
                        <h5 class="light grey-text text-lighten-3">Visite el calendario de reuniones</h5>
                        <a href="calendario" class="btn red white-text waves-effect waves-red">consultar</a>
                    </div>
                </li>
                <c:if test="${modelo.proximaReunion != null}">
                    <li> 
                        <img src="img/slider2.jpeg">
                        <div class="caption left-align">
                            <h3>Próxima reunión</h3>
                            <h5><fmt:formatDate pattern="d 'de' MMMM HH:mm'h'" value="${modelo.proximaReunion.fecha}" /></h5>
                            <h5 class="light hide-on-small-only light">${modelo.proximaReunion.titulo}</h5>
                            <a href="reuniones?accion=detalles&id=${modelo.proximaReunion.id}" class="btn white-text waves-effect yellow darken-2 ">detalles</a> 
                        </div>
                    </li>
                </c:if>
                <c:if test="${modelo.ultimaReunion != null}">
                    <li> 
                        <img src="img/slider3.jpg">
                        <div class="caption right-align">
                            <h3>Última reunión efectuada</h3>
                            <h5 class="light hide-on-small-only light">${modelo.ultimaReunion.titulo}</h5>
                            <a href="reuniones?accion=detalles&id=${modelo.ultimaReunion.id}" class="btn-flat white-text waves-effect waves-white">detalles</a> 
                        </div>
                    </li>
                </c:if>
                <li> <img src="img/slider4.jpeg">
                    <div class="caption center-align">
                        <h3>Arquitectura Rifa Móvil</h3>
                        <h5 class="light grey-text text-lighten-3">Mantengase informado</h5>
                        <div class="btn">Descargar ahora</div>
                    </div>
                </li>
            </ul>
        </div>
        <br>
        <br>
        <br>
        <section class="container flow-text left-align">
            <p class="center-align">Resoluciones recientes</p>
            <div class="divider"></div>
            <c:forEach var="resolucion" items="${modelo.ultimaReunion.resoluciones}">
                <p class="flow-text">${resolucion}</p>
            </c:forEach>
        </section>
        <p class="center"><a href="reuniones?accion=detalles&id=${modelo.ultimaReunion.id}" class="btn btn-flat waves-effect waves-green">MAS INFORMACIÓN</a></p>
        <script>
            $('.slider').slider({interval: 6000});
        </script>
    </jsp:body>
</t:masterpage>

