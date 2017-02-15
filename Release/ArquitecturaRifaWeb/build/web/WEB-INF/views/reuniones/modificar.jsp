<%@taglib prefix="t" tagdir="/WEB-INF/tags/" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:masterpage>
    <jsp:body>
        <div class="container">
            <div class="row">
                <form action="reunion" id="reunion-form" class="col s12 card-panel" style="padding:20px;" method="post">
                    <div class="row">
                        <div class="col s12">
                            <h5>Modificar reunión
                                <span class="right"> 
                                    <input type="checkbox" id="obligatoria" name="obligatoria" class="filled-in" ${modelo.obligatoria? 'checked' : ''} />
                                    <label for="obligatoria" class="right" >Obligatoria</label>
                                </span>
                            </h5> 
                        </div>
                    </div>
                    <div class="row">
                        <div class="input-field col s12">
                            <input id="id" name="id" type="text" readonly value="${modelo.id}">
                            <label for="id">ID</label>
                        </div>
                    </div>
                    <div class="row">
                        <div class="input-field col s12">
                            <input id="titulo" name="titulo" type="text" value="${modelo.titulo}">
                            <label for="titulo">Título</label>
                        </div>
                    </div>
                    <div class="row">
                        <div class="input-field col m6 s12">
                            <input id="fecha" name="fecha" type="date" class="datepicker" value="${modelo.fecha}" required>
                            <label for="fecha">Fecha</label>
                        </div>
                        <div class="input-field col m3 s12" >
                            <input id="hora" name="hora" type="time" value="${modelo.hora}" required>
                        </div>
                        <div class="input-field col m3 s12">
                            <input id="duracion" name="duracion" type="number" value="${modelo.duracion}" required>
                            <label for="duracion">Duración (m)</label>
                        </div>
                    </div>
                    <div class="row">
                        <div class="input-field col s12">
                            <input id="lugar" name="lugar" type="text" value="${modelo.lugar}">
                            <label for="lugar">Lugar</label>
                        </div>
                    </div>
                    <div class="row">
                        <div class="input-field col s12">
                            <textarea id="descripcion" name="descripcion" class="materialize-textarea" data-length="300" >${modelo.descripcion}</textarea>
                            <label for="descripcion">Descripción</label>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col s12">
                            <c:forEach var="tema" items="${modelo.temas}" varStatus="status">
                                <input id="tema${status.index}" type="hidden" name="temas" value="${tema}">
                                <div id="chip${status.index}" class="chip">${tema}<i class="close material-icons" onclick="eliminar('tema${status.index}')">close</i>
                                </div>
                            </c:forEach>
                            <div id="temas-chips" class="chips chips-placeholder"></div>

                        </div>
                    </div>
                    <div class="row">
                        <div class="input-field col s12 center">
                            <button class="btn waves-effect waves-light" type="submit" name="accion" value="modificar">guardar<i class="material-icons right">save</i> </button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </jsp:body>
</t:masterpage>