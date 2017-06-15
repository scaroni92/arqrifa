<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags/" %>
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
                            <h3>Próxima reunión <span class="hide-on-small-only"><fmt:formatDate pattern="d 'de' MMMM HH:mm" value="${modelo.proximaReunion.fecha}" /></span></h3>
                            <h5 class="light hide-on-small-only light">${modelo.proximaReunion.titulo}</h5>
                            <a href="reuniones?accion=detalles&id=${modelo.proximaReunion.id}" class="btn-flat white-text waves-effect waves-yellow ">detalles</a> 
                        </div>
                    </li>
                </c:if>
                <c:if test="${modelo.ultimaReunion != null}">
                    <li> 
                        <img src="img/slider2.jpg">
                        <div class="caption right-align">
                            <h3>Última reunión efectuada</h3>
                            <h5 class="light hide-on-small-only light">${modelo.ultimaReunion.titulo}</h5>
                            <a href="reuniones?accion=detalles&id=${modelo.ultimaReunion.id}" class="btn-flat white-text waves-effect waves-white">detalles</a> 
                        </div>
                    </li>
                </c:if>
            </ul>
        </div>
        <c:if test="${modelo.ultimaReunion != null}">

        </c:if>
        <section class="container flow-text left-align">
            <p class="center-align">Resoluciones recientes</p>
            <div class="divider"></div>
            <c:forEach var="resolucion" items="${modelo.ultimaReunion.resoluciones}">
                <p class="flow-text">${resolucion}</p>
            </c:forEach>
        </section>
        <p class="center"><a href="reuniones?accion=detalles&id=${modelo.ultimaReunion.id}" class="btn btn-flat waves-effect waves-green">MAS INFORMACIÓN</a></p>
        <script>
            $('.slider').slider({interval: 10000});

            <c:if test="${!empty modelo.solicitudes}">
            window.setTimeout(function () {
                Materialize.toast('<span class="truncate">Se encontraron solicitudes pendientes<a href="solicitudes" class="green-text"> VER</a></span>', 16000);
            }, 2000);
            </c:if>

        </script>


    </jsp:body>
</t:masterpage>

