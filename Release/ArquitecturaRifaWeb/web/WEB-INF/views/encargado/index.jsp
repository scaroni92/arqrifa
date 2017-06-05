<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags/" %>
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
            </ul>
        </div>
        <p class="flow-text center">Algún contenido</p>
        <div class="divider"></div>
        <section class="row">
            <div class="col s12">
                <p class="flow-text center light"> Lorem ipsum dolor sit amet, consectetur adipiscing elit. Convallis nullam hendrerit quis montes duis litora, </p>
            </div>
        </section>

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

