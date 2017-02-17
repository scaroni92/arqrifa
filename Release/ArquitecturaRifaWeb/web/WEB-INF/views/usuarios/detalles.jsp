<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags/" %>

<t:masterpage titulo="Detalles">
    <jsp:body>
        <div class="container">
            <div class="card-panel">
                <h5>${modelo.usuario.nombre} ${modelo.usuario.apellido}</h5>
                <p>${modelo.usuario.email}</p>
                <p>Contrase�a: ${modelo.usuario.contrasena}</p>
                <p>Generaci�n: ${modelo.usuario.generacion}</p>
                <p>${modelo.usuario.rol}</p>
                <c:if test="${modelo.usuario.rol eq 'Estudiante'}">
                    <p>Inasitencias: ${modelo.usuario.inasistencias}</p>    
                </c:if>
            </div>
        </div>
    </jsp:body>
</t:masterpage>