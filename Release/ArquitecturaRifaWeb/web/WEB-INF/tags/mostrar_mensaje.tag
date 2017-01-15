<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>        

<p class="${fn:contains(modelo.mensaje, '#error#') ? "mensaje-error" : ""}">${fn:replace(modelo.mensaje, '#error#', '')}</p>