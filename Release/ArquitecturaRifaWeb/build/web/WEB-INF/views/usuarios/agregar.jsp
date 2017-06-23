<%@taglib prefix="t" tagdir="/WEB-INF/tags/" %>
<%@taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:masterpage titulo="Agregar">
    <jsp:body>
        <main>
            <div class="container">
                <div class="row">
                    <form action="usuario" method="post" class="col s12">
                        <div class="wrapper card-panel formulario">
                            <div class="row">
                                <div class="col s12">
                                    <h5>Nuevo usuario</h5> </div>
                            </div>
                            <div class="row">
                                <div class="input-field col s12">
                                    <input id="ci" name="ci" type="number" value="${modelo.ci}">
                                    <label for="ci">Cédula</label>
                                </div>
                            </div>
                            <div class="row">
                                <div class="input-field col s12">
                                    <input id="nombre" name="nombre" type="text" value="${modelo.nombre}">
                                    <label for="nombre">Nombre</label>
                                </div>
                            </div>
                            <div class="row">
                                <div class="input-field col s12">
                                    <input id="apellido" name="apellido" type="text" value="${modelo.apellido}">
                                    <label for="apellido">Apellido</label>
                                </div>
                            </div>
                            <div class="row">
                                <div class="input-field col s12">
                                    <input id="email" name="email" type="email" value="${modelo.email}">
                                    <label for="email">Email</label>
                                </div>
                            </div>
                            <div class="row">
                                <div class="input-field col s12">
                                    <input id="pass" name="contrasena" type="password" value="${modelo.contrasena}">
                                    <label for="pass">Contraseña</label>
                                </div>
                            </div>
                            <div class="row">
                                <div class="input-field col  s12">
                                    <select name="generacion">
                                        <c:forEach items="${modelo.generaciones}" var="generacion">
                                            <option>${generacion.id}</option>
                                        </c:forEach>
                                    </select>
                                    <label>Escoga una generación</label>
                                </div>
                                <div class="input-field col  s12">
                                    <select name="rol">
                                        <option value="Encargado">Encargado</option>
                                        <option value="Estudiante">Estudiante</option>
                                    </select>
                                    <label>Escoga una generación</label>
                                </div>
                            </div>
                            <div class="row">
                                <div class="input-field col s12 center">
                                    <button class="btn waves-effect waves-light" type="submit" name="accion" value="agregar">Agregar<i class="material-icons right">send</i> </button>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </main>
    </jsp:body>
</t:masterpage>
