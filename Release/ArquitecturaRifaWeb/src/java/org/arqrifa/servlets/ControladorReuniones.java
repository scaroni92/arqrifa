package org.arqrifa.servlets;

//<editor-fold defaultstate="collapsed" desc="imports">
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import org.arqrifa.datatypes.DTReunion;
import org.arqrifa.datatypes.DTUsuario;
import org.arqrifa.exceptions.ArquitecturaRifaException;
import org.arqrifa.viewmodels.VMAsistencias;
import org.arqrifa.viewmodels.VMCalendario;
import org.arqrifa.viewmodels.VMReunion;
import org.arqrifa.viewmodels.VMReunionMantenimiento;
//</editor-fold>

public class ControladorReuniones extends Controlador {
    
    public void index_get() {
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
            
            String[] temas = request.getParameterValues("temas");
            if (temas == null) {
                throw new ArquitecturaRifaException("Agregue los temas a debatir en la reunión");
            }
            vm.setTemas(Arrays.asList(temas));
            
            DTReunion reunion = new DTReunion();
            
            reunion.setTitulo(vm.getTitulo());
            reunion.setDescripcion(vm.getDescripcion());
            reunion.setFecha(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(vm.getFecha() + " " + vm.getHora()));
            reunion.setDuracion(Integer.parseInt(vm.getDuracion()));
            reunion.setObligatoria(vm.isObligatoria());
            reunion.setLugar(vm.getLugar());
            reunion.setTemas(vm.getTemas());
            reunion.setGeneracion(getUsuario().getGeneracion());
            
            cliente.agregarReunion(reunion);
            
            sesion.setAttribute("mensaje", "Reunión agendada exitosamente");
            response.sendRedirect("Reuniones");
        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
            mostrarVista("Reunion/agendar.jsp", vm);
        }
    }
    
    public void modificar_get() {
        VMReunionMantenimiento vm = new VMReunionMantenimiento();
        try {
            
            cargarVMReunionMantenimiento(vm);
            
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
            
            String[] temas = request.getParameterValues("temas");
            if (temas == null) {
                throw new Exception("Ingrese los temas a debatir en la reunión.");
            }
            vm.setTemas(Arrays.asList(temas));
            
            DTReunion reunion = cliente.buscarReunion(Integer.parseInt(vm.getId()));
            
            reunion.setTitulo(vm.getTitulo());
            reunion.setDescripcion(vm.getDescripcion());
            reunion.setFecha(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(vm.getFecha() + " " + vm.getHora()));
            reunion.setDuracion(Integer.parseInt(vm.getDuracion()));
            reunion.setObligatoria(vm.isObligatoria());
            reunion.setLugar(vm.getLugar());
            reunion.setTemas(vm.getTemas());
            
            cliente.modificarReunion(reunion);
            
            sesion.setAttribute("mensaje", "Reunión modificada exitosamente");
            response.sendRedirect("Reuniones");
        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
            mostrarVista("Reunion/modificar.jsp", vm);
        }
        
    }
    
    public void panel_get() {
        VMReunionMantenimiento vm = new VMReunionMantenimiento();
        try {
            
            cargarVMReunionMantenimiento(vm);
            
        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
        }
        mostrarVista("Reunion/panel.jsp", vm);
    }
    
    public void iniciar_post() {
        VMReunionMantenimiento vm = (VMReunionMantenimiento) cargarModelo(new VMReunionMantenimiento());
        try {
            cliente.iniciarReunion(cliente.buscarReunion(Integer.parseInt(vm.getId())));
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
            String[] resoluciones = request.getParameterValues("resoluciones");
            if (resoluciones == null) {
                throw new Exception("Ingrese alguna resolución de la reunión.");
            }
            vm.setResoluciones(Arrays.asList(resoluciones));
            
            DTReunion reunion = new DTReunion();
            reunion.setId(Integer.parseInt(vm.getId()));
            reunion.setObservaciones(vm.getObservaciones());
            reunion.setResoluciones(vm.getResoluciones());
            
            cliente.finalizarReunion(reunion);
            sesion.setAttribute("mensaje", "Reunión finalizada exitosamente.");
            response.sendRedirect("Reuniones");
            
        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
            mostrarVista("Reunion/panel.jsp", vm);
        }
    }
    
    public void resumen_get() {
        VMReunion vm = new VMReunion();
        try {
            vm.setReunion(cliente.buscarUltimaReunionFinalizada(getUsuario().getGeneracion()));
        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
        }
        mostrarVista("Reunion/resumen.jsp", vm);
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
    
    public void agregar_asistencia_post() {
        VMAsistencias vm = new VMAsistencias();
        try {
            DTUsuario estudiante = cliente.buscarUsuario(Integer.parseInt(request.getParameter("ci")));
            
            DTReunion reunion = new DTReunion();
            reunion.setId(Integer.parseInt(request.getParameter("id")));
            
            cliente.agregarAsistencia(reunion, estudiante);
            
            vm = new VMAsistencias(cliente.buscarReunion(reunion.getId()), cliente.listarAsistencias(reunion), "");
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
            
            sesion.setAttribute("mensaje", "Reunion eliminada exitosamente");
            response.sendRedirect("Reuniones");
        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
            mostrarVista("Reunion/eliminar.jsp", vm);
        }
        
    }
    
    private void cargarVMReunionMantenimiento(VMReunionMantenimiento vm) throws Exception {
        DTReunion reunion = cliente.buscarReunion(Integer.parseInt(request.getParameter("id")));
        
        vm.setId(String.valueOf(reunion.getId()));
        vm.setTitulo(reunion.getTitulo());
        vm.setDescripcion(reunion.getDescripcion());
        vm.setFecha(new SimpleDateFormat("yyyy-MM-dd").format(reunion.getFecha()));
        vm.setHora(new SimpleDateFormat("HH:mm").format(reunion.getFecha()));
        vm.setDuracion(String.valueOf(reunion.getDuracion()));
        vm.setObligatoria(reunion.isObligatoria());
        vm.setLugar(reunion.getLugar());
        vm.setEstado(reunion.getEstado());
        vm.setTemas(reunion.getTemas());
    }
}
