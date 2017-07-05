<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags/" %>

<t:masterpage titulo="Detalles">
    <jsp:body>
        <div class="container">
            <div class="row">
                <div class="col s12 m6 offset-m3">
                    <div class="card-panel grey-text text-darken-2">
                        <h4 class="light truncate">${modelo.usuario.nombre} ${modelo.usuario.apellido}<span class="badge" data-badge-caption="${modelo.usuario.rol}"></span></h4>
                        <p class="collection-item">${modelo.usuario.email}</p>
                        <p class="collection-item">Cédula: ${modelo.usuario.ci}</p>
                        <p class="collection-item">Contraseña: ${modelo.usuario.contrasena}</p>
                        <p class="collection-item">Generación: ${modelo.usuario.generacion}</p>
                        <c:if test="${modelo.usuario.estudiante}">
                            <div class="card-action center"><div class="chip ${modelo.usuario.inasistencias eq 0 ? 'green' : 'red'} white-text">${modelo.usuario.inasistencias} Inasistencias</div></div>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
    </jsp:body>
</t:masterpage>