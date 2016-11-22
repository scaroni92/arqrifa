package org.arqrifa.servlets;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.arqrifa.datatypes.DTReunion;
import org.arqrifa.datatypes.DTUsuario;
import org.arqrifa.validador.Validador;
import org.arqrifa.viewmodels.VMReunion;

public class ControladorReuniones extends Controlador {
    
    public void ver_get() {
        VMReunion vm = new VMReunion();
        try {
            
            DTReunion r = cliente.buscarReunion(Validador.validarId(request.getParameter("id")));
            
            if (r == null) {
                throw new Exception("Reunión no encontrada");
            }
            
            if (r.getGeneracion() != ((DTUsuario) sesion.getAttribute("usuario")).getGeneracion()) {
                throw new Exception("La reunión no pertenece a su generación");
            }
            
            SimpleDateFormat sdfFecha = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat sdfHora = new SimpleDateFormat("HH:mm");
            
            vm.setId(String.valueOf(r.getId()));
            vm.setTitulo(r.getTitulo());
            vm.setDescripcion(r.getDescripcion());
            vm.setResoluciones(r.getResoluciones());
            vm.setFecha(sdfFecha.format(r.getFecha()));
            vm.setHora(sdfHora.format(r.getFecha()));
            vm.setObligatoria(r.isObligatoria());
            vm.setGeneracion(String.valueOf(r.getGeneracion()));
            vm.setEstado(r.getEstado());
            vm.setLugar(r.getLugar());

            // Si la reunión está en condiciones de iniciarse se habilita su inicio
            if (r.getEstado().equals("Pendiente") && vm.getFecha().equals(sdfFecha.format(new Date()))) {
                vm.setHabilitarInicio(r.getFecha().after(new Date()));
            }
        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
        }
        mostrarVista("Vistas/Reunion/ver.jsp", vm);
    }
    
    public void iniciar_link_post() {
        mostrarVista("Vistas/Reunion/efectuar.jsp", (VMReunion) cargarModelo(new VMReunion()));
    }
    
    public void iniciar_post() {
        VMReunion vm = (VMReunion) cargarModelo(new VMReunion());
        try {
            
            DTReunion r = cliente.buscarReunion(Validador.validarId(request.getParameter("id")));
            cliente.iniciarReunion(r);
            vm.setEstado("Iniciada");
            
            vm.setMensaje("Reunión iniciada exitosamente");
        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
        }
        mostrarVista("Vistas/Reunion/efectuar.jsp", vm);
    }
    
    public void finalizar_post() {
        VMReunion vm = (VMReunion) cargarModelo(new VMReunion());
        
        try {
            if (vm.getResoluciones().isEmpty()) {
                throw new Exception("Debe ingresar las resoluciones de la reunión.");
            }
            
            DTReunion r = new DTReunion();
            r.setId(Integer.parseInt(vm.getId()));
            r.setResoluciones(vm.getResoluciones());
            cliente.finalizarReunion(r);
            vm.setEstado("Finalizada");
            vm.setMensaje("reunión finalizada exitosamente");
        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
        }
        mostrarVista("Vistas/Reunion/efectuar.jsp", vm);
    }
}
