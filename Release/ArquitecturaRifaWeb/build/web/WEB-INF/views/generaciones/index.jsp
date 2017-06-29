<%@taglib prefix="t" tagdir="/WEB-INF/tags/" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<t:masterpage titulo="Generaciones">
    <jsp:body>
        <div class="container">
            <form action="generaciones" method="post">
                <div class="row">
                    <div class="col s12">
                        <div class=" center">
                            <div class="input-field inline">
                                <input id="anio" name="anio" type="number" required>
                                <label for="anio">Año</label>
                            </div>
                            <button class="btn green white-text waves-effect waves-light" type="submit" name="accion" value="agregar">agregar</button>
                        </div>
                    </div>
                </div>
            </form>
            <table class="card-panel centered highlight">
                <thead>
                    <tr>
                        <th>Generación</th>
                        <th>Integrantes</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${modelo.generaciones}" var="generacion">
                        <tr>
                            <td>${generacion.id}</td>
                            <td class="icon-btn">
                                <a href="generaciones?accion=integrantes&id=${generacion.id}"><i class="material-icons icon-btn">group</i></a> 
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </jsp:body>
</t:masterpage>
