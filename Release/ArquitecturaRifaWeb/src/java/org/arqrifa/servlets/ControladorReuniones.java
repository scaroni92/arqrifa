package org.arqrifa.servlets;

//<editor-fold defaultstate="collapsed" desc="imports">
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import org.arqrifa.datatypes.DTReunion;
import org.arqrifa.datatypes.DTUsuario;
import org.arqrifa.viewmodels.VMAsistencias;
import org.arqrifa.viewmodels.VMCalendario;
import org.arqrifa.viewmodels.VMReunion;
import org.arqrifa.viewmodels.VMReunionMantenimiento;
//</editor-fold>

public class ControladorReuniones extends Controlador {

    public void ver_get() {
        try {
            DTReunion reunion = cliente.buscarReunion(Integer.parseInt(request.getParameter("id")));

            if (getUsuario().getRol().equals(DTUsuario.ADMIN) || getUsuario().getGeneracion() == reunion.getGeneracion()) {
                mostrarVista("Reunion/ver.jsp", new VMReunion(reunion, ""));
            } else {
                mostrarVista("Error/no_encontrado.jsp");
            }

        } catch (NumberFormatException | NullPointerException e) {
            mostrarVista("Error/no_encontrado.jsp");
        } catch (Exception e) {
            mostrarVista("Reunion/ver.jsp", new VMReunion(null, e.getMessage()));
        }

    }

    public void agendar_get() {
        mostrarVista("Reunion/agendar.jsp");
    }

    public void agendar_post() {
        VMReunionMantenimiento vm = (VMReunionMantenimiento) cargarModelo(new VMReunionMantenimiento());

        try {
            if (vm.getTitulo().isEmpty() || vm.getDescripcion().isEmpty() || vm.getLugar().isEmpty()) {
                throw new Exception("Complete todos los campos obligatorios.");
            }
            if (vm.getTemas().isEmpty()) {
                throw new Exception("Ingrese los temas a debatir en la reunión.");
            }

            DTReunion r = new DTReunion();
            r.setGeneracion(((DTUsuario) sesion.getAttribute("usuario")).getGeneracion());
            r.setTitulo(vm.getTitulo());
            r.setDescripcion(vm.getDescripcion());
            r.setFecha(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(vm.getFecha() + " " + vm.getHora()));
            r.setDuracion(Integer.parseInt(vm.getDuracion()));
            r.setObligatoria(vm.isObligatoria());
            r.setLugar(vm.getLugar());
            r.setTemas(Arrays.asList(vm.getTemas().split("\n")));

            cliente.agendarReunion(r);
            vm = new VMReunionMantenimiento();
            vm.setMensaje("Reuníon agendada exitosamente.");

        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
        }

        mostrarVista("Reunion/agendar.jsp", vm);
    }

    public void modificar_get() {
        VMReunionMantenimiento vm = new VMReunionMantenimiento();
        try {
            DTReunion reunion = cliente.buscarReunion(Integer.parseInt(request.getParameter("id")));

            String id, titulo, descripcion, fecha, hora, duracion, lugar, temas = "";
            boolean obligatoria;

            id = String.valueOf(reunion.getId());
            titulo = reunion.getTitulo();
            descripcion = reunion.getDescripcion();
            fecha = new SimpleDateFormat("yyyy-MM-dd").format(reunion.getFecha());
            hora = new SimpleDateFormat("HH:mm").format(reunion.getFecha());
            duracion = String.valueOf(reunion.getDuracion());
            obligatoria = reunion.isObligatoria();
            lugar = reunion.getLugar();

            for (String tema : reunion.getTemas()) {
                temas += tema + ",";
            }

            vm = new VMReunionMantenimiento(id, "", titulo, descripcion, fecha, hora, duracion, obligatoria, lugar, "", "", temas, "");

        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
        }
        mostrarVista("Reunion/modificar.jsp", vm);
    }

    public void modificar_post() {
        VMReunionMantenimiento vm = (VMReunionMantenimiento) cargarModelo(new VMReunionMantenimiento());
        try {
            if (vm.getTitulo().isEmpty() || vm.getDescripcion().isEmpty() || vm.getLugar().isEmpty()) {
                throw new Exception("Complete todos los campos obligatorios.");
            }

            List<String> temas = Arrays.asList(request.getParameterValues("temas"));
            if (temas.isEmpty()) {
                throw new Exception("Ingrese los temas a debatir en la reunión.");
            }

            DTReunion reunion = cliente.buscarReunion(Integer.parseInt(vm.getId()));

            reunion.setTitulo(vm.getTitulo());
            reunion.setDescripcion(vm.getDescripcion());
            reunion.setFecha(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(vm.getFecha() + " " + vm.getHora()));
            reunion.setDuracion(Integer.parseInt(vm.getDuracion()));
            reunion.setObligatoria(vm.isObligatoria());
            reunion.setLugar(vm.getLugar());
            reunion.setTemas(temas);

            cliente.modificarReunion(reunion);
            vm.setMensaje("Reunión modificada exitosamente");

        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
        }
        mostrarVista("Reunion/modificar.jsp", vm);
    }

    public void panel_get() {
        VMReunionMantenimiento vm = new VMReunionMantenimiento();
        try {
            DTReunion r = cliente.buscarReunion(Integer.parseInt(request.getParameter("id")));

            //añadir a método
            vm.setId(String.valueOf(r.getId()));
            vm.setTitulo(r.getTitulo());
            vm.setDescripcion(r.getDescripcion());
            vm.setFecha(new SimpleDateFormat("yyyy-MM-dd").format(r.getFecha()));
            vm.setHora(new SimpleDateFormat("HH:mm").format(r.getFecha()));
            vm.setDuracion(String.valueOf(r.getDuracion()));
            vm.setEstado(r.getEstado());
            String temas = "";
            for (String tema : r.getTemas()) {
                temas += tema + ",";
            }
            vm.setTemas(temas);
        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
        }
        mostrarVista("Reunion/panel.jsp", vm);
    }

    public void iniciar_post() {
        VMReunionMantenimiento vm = (VMReunionMantenimiento) cargarModelo(new VMReunionMantenimiento());
        try {
            cliente.iniciarReunion(cliente.buscarReunion(Integer.parseInt(request.getParameter("id"))));
            vm.setEstado("Iniciada");
            vm.setMensaje("Reunión iniciada exitosamente");
        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
        }
        mostrarVista("Reunion/panel.jsp", vm);
    }

    public void finalizar_post() {
        VMReunionMantenimiento vm = (VMReunionMantenimiento) cargarModelo(new VMReunionMantenimiento());
        try {
            if (vm.getResoluciones().isEmpty()) {
                throw new Exception("Debe ingresar las resoluciones de la reunión.");
            }

            DTReunion r = new DTReunion();
            r.setId(Integer.parseInt(vm.getId()));
            r.setObservaciones(vm.getObservaciones());
            r.setResoluciones(Arrays.asList(vm.getResoluciones().split("\n")));
            cliente.finalizarReunion(r);
            vm.setEstado("Finalizada");
            //redireccionar a ver reunión
            vm.setMensaje("Reunión finalizada exitosamente");
        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
        }
        mostrarVista("Reunion/panel.jsp", vm);
    }

    public void resumen_get() {
        VMReunion vm = new VMReunion();
        try {
            DTReunion reunion = cliente.buscarUltimaReunionFinalizada(getUsuario().getGeneracion());
            vm.setReunion(reunion);
        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
        }
        mostrarVista("Reunion/resumen.jsp", vm);
    }

    public void calendario_get() {
        VMCalendario vm = new VMCalendario("");
        try {
            if (getUsuario().getRol().equals(DTUsuario.ADMIN)) {
                vm.setReuniones(cliente.listarReunionesTodas());
            } else {
                vm.setReuniones(cliente.listarReunionesPorGeneracion(getUsuario().getGeneracion()));
            }
        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
        }
        mostrarVista("Reunion/calendario.jsp", vm);
    }

    public void asistencias_get() {
        VMAsistencias vm = new VMAsistencias();
        try {
            DTReunion reunion = cliente.buscarReunion(Integer.parseInt(request.getParameter("id")));
            vm = new VMAsistencias(reunion, cliente.listarAsistencias(reunion), "");
        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
        }
        mostrarVista("Reunion/asistencias.jsp", vm);
    }

    public void agregar_asistencia_get() {
        VMAsistencias vm = new VMAsistencias();
        try {
            DTReunion reunion = cliente.buscarReunion(Integer.parseInt(request.getParameter("id")));
            DTUsuario estudiante = new DTUsuario();
            estudiante.setCi(Integer.parseInt(request.getParameter("ci")));
            estudiante.setRol("Estudiante");

            cliente.agregarAsistencia(reunion, estudiante);

            vm = new VMAsistencias(reunion, cliente.listarAsistencias(reunion), "");
            vm.setMensaje("Asistencia marcada exitosamente.");
        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
        }
        mostrarVista("Reunion/asistencias.jsp", vm);
    }

    public void proxima_get() {
        VMReunion vm = new VMReunion();
        try {
            DTReunion reunion = cliente.buscarSiguienteReunion(getUsuario().getGeneracion());
            vm.setReunion(reunion);
        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
        }
        mostrarVista("Reunion/resumen.jsp", vm);
    }

    public void eliminar_get() {
        mostrarVista("Reunion/eliminar.jsp", (VMReunionMantenimiento) cargarModelo(new VMReunionMantenimiento()));
    }

    public void eliminar_post() {
        VMReunionMantenimiento vm = (VMReunionMantenimiento) cargarModelo(new VMReunionMantenimiento());
        try {
            DTReunion reunion = cliente.buscarReunion(Integer.parseInt(vm.getId()));
            cliente.eliminarReunion(reunion);
            this.calendario_get();
        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
            mostrarVista("Reunion/eliminar.jsp", vm);
        }

    }
}
