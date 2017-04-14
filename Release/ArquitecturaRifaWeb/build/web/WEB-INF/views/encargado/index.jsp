<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags/" %>
<t:masterpage titulo="INICIO" footer="true">
    <jsp:body>
        <div class="slider">
            <ul class="slides">
                <li> 
                    <img src="img/slider1.jpeg">
                    <div class="caption left-align">
                        <h3>Próxima reunión <fmt:formatDate pattern="dd 'de' MMMM HH:mm" value="${modelo.proximaReunion.fecha}" /></h3>
                        <h5 class="light">${modelo.proximaReunion.descripcion}</h5> <a href="reunion?accion=detalles&id=${modelo.proximaReunion.id}" class="btn-flat white-text waves-effect waves-yellow ">detalles</a> 
                    </div>
                </li>
                <li> 
                    <img src="img/slider2.jpg">
                    <div class="caption right-align">
                        <h3>Últimas Resoluciones</h3>
                        <c:forEach var="resolucion" items="${modelo.ultimaReunion.resoluciones}">
                            <h5 class="light grey-text text-lighten-3">${resolucion}</h5>
                        </c:forEach>
                        <a href="reunion?accion=detalles&id=${modelo.ultimaReunion.id}" class="btn-flat white-text waves-effect waves-white">detalles</a> 
                    </div>
                </li>
                <li> 
                    <img src="img/slider3.jpg">
                    <div class="caption center-align">
                        <h3 >Calendario</h3>
                        <h5 class="light grey-text text-lighten-3">Visite el calendario de reuniones</h5>
                        <a href="usuario?accion=ver-calendario" class="btn-flat white-text waves-effect waves-red">consultar</a>
                    </div>
                </li>
            </ul>
        </div>
        <p class="flow-text center">Algún contenido</p>
        <div class="divider"></div>
        <br>
        <br>
        <br>
        <br>
        <br>
        <br> 
        <br>
        <br>


        <script>
            $('.slider').slider({
                interval: 10000
            });
            <c:if test="${!empty modelo.solicitudes}">
            window.setTimeout(function () {
                Materialize.toast('<span>Se encontraron solicitudes pendientes</span> <a href="encargado?accion=listar-solicitudes" class="green-text" style="margin-left:10px"> VER</a>', 16000);
            }, 2000);
            </c:if>

        </script>


    </jsp:body>
</t:masterpage>

