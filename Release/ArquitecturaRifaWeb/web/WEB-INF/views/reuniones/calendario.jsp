<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags/" %>

<t:masterpage titulo="Calendario" href="calendario" padding="0">
    <div class="container">
        <form action="calendario" >
            <div class="row">
                <div class="col s12">
                    <div class="input-field inline right">
                        <select name="filtro" onchange="this.form.submit()">
                            <option value="" disabled selected>Escoga un filtro</option>
                            <option value="todas" ${modelo.filtro eq 'todas' ? 'selected' : ''}>Todas</option>
                            <option value="pendiente" ${modelo.filtro eq 'pendiente' ? 'selected' : ''}>Pendiente</option>
                            <option value="finalizada" ${modelo.filtro eq 'finalizada' ? 'selected' : ''}>Finalizada</option>
                        </select>
                    </div>
                </div>
            </div>
        </form>
        <div class="row">
            <c:forEach var="reunion" items="${modelo.reuniones}" varStatus="status">
                <c:set var="color" value="${reunion.finalizada? 'grey' : 'teal'}" scope="page"/>

                <div class="col l4 m6 s12">
                    <div class="card calendario ${color}">
                        <h6 class="white-text center"> <fmt:formatDate pattern="MMMM" value="${reunion.fecha}" /></h6>
                        <div class="card-content  ${color} lighten-1 white-text center">
                            <h6><fmt:formatDate pattern="EEEE" value="${reunion.fecha}" /></h6>
                            <div class="calendario-numero"><fmt:formatDate pattern="dd" value="${reunion.fecha}" /> </div>
                            <div class="calendario-hora"><fmt:formatDate pattern="HH:mm" value="${reunion.fecha}" /> </div>
                        </div>
                        <div class="card-content white grey-text text-darken-2 truncate">
                            ${reunion.titulo}
                        </div>
                        <div class="card-action white center"> <a href="reuniones?accion=detalles&id=${reunion.id}">detalles</a> </div>
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
</t:masterpage>


