<%@taglib prefix="t" tagdir="/WEB-INF/tags/" %>
<%@taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<t:masterpage titulo="Modificar">
    <jsp:body>
        <div class="container">
            <div class="row">
                <form action="usuario" method="post" class="col s12">
                    <div class="wrapper card-panel formulario">
                        <div class="row">
                            <div class="col s12">
                                <h5>Modificar usuario</h5> 
                            </div>
                        </div>
                        <div class="row">
                            <div class="input-field col s12">
                                <input id="ci" name="ci" type="number" value="${modelo.ci}" readonly>
                                <label for="ci">Cédula</label>
                            </div>
                        </div>
                        <div class="row">
                            <div class="input-field col s12">
                                <input id="nombre" name="nombre" type="text" value="${modelo.nombre}" required>
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
                                <input id="email" name="email" type="email" value="${modelo.email}" readonly>
                                <label for="email">Email</label>
                            </div>
                        </div>
                        <div class="row">
                            <div class="input-field col s12">
                                <input id="pass" name="contrasena" type="text" value="${modelo.contrasena}" required>
                                <label for="pass">Contraseña</label>
                            </div>
                        </div>
                        <div class="row">
                            <div class="input-field col s12">
                                <input id="generacion" name="generacion" type="text" value="${modelo.generacion}" readonly>
                                <label for="generacion">Generacion</label>
                            </div>
                        </div>
                        <div class="row">
                            <div class="input-field col s12 center">
                                <button class="btn waves-effect waves-light" type="submit" name="accion" value="modificar">guardar<i class="material-icons right">save</i> </button>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>

    </jsp:body>
</t:masterpage>
