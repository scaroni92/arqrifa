<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags/" %>

<t:masterpage titulo="Calendario">
    <div class="container">
        <div class="section center">
            <form action="usuario">
                <input type="hidden" name="accion" value="ver-calendario" />
                <input class="with-gap" name="filtro" type="radio" id="todas" value="todas" />
                <label for="todas">Todas</label>
                <input class="with-gap" name="filtro" type="radio" id="pendiente" value="pendiente" />
                <label for="pendiente">Pendientes</label>
                <input class="with-gap" name="filtro" type="radio" id="finalizada" value="finalizada" />
                <label for="finalizada">Finalizadas</label>
            </form>
        </div>
        <div class="row">
            <c:forEach var="reunion" items="${modelo.reuniones}" varStatus="status">
                <c:set var="color" value="${reunion.estado eq 'Finalizada'? 'grey' : 'teal'}" scope="page"/>

                <div class="col l4 m6 s12">
                    <div class="card calendario ${color}">
                        <h6 class="white-text center"> <fmt:formatDate pattern="MMMM" value="${reunion.fecha}" /></h6>
                        <div class="card-content  ${color} lighten-1 white-text center">
                            <h6><fmt:formatDate pattern="EEEE" value="${reunion.fecha}" /></h6>
                            <div class="calendario-numero"><fmt:formatDate pattern="dd" value="${reunion.fecha}" /> </div>
                            <div class="calendario-hora"><fmt:formatDate pattern="hh:mm" value="${reunion.fecha}" /> </div>
                        </div>
                        <div class="card-content white grey-text text-darken-2">
                            ${reunion.titulo}
                        </div>
                        <div class="card-action white center"> <a href="reunion?accion=detalles&id=${reunion.id}">detalles</a> </div>
                    </div>
                </div>

            </c:forEach>
        </div>
    </div>

    <c:if test="${usuario.rol eq 'Encargado'}"> 
        <div class="fixed-action-btn">
            <a href="reunion?accion=agendar" class="btn-floating btn-large red"> <i class="large material-icons">add</i></a>
        </div>    
    </c:if>

    <script>
        $('form').on('change', 'input', function(){
            this.form.submit();
        });
        
        $('#' + '${modelo.filtro}').attr('checked', true);
    </script>
</t:masterpage>


