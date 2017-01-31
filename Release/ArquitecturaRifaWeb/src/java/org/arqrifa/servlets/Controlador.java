package org.arqrifa.servlets;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.arqrifa.datatypes.DTUsuario;
import org.arqrifa.rest.ClienteJersey;
import org.arqrifa.viewmodels.ViewModel;

@WebServlet(name = "Controlador", urlPatterns = {"/Controlador"})
public class Controlador extends HttpServlet {

    protected HttpSession sesion;
    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected ClienteJersey cliente = new ClienteJersey();

    public DTUsuario getUsuario() {
        return (DTUsuario) sesion.getAttribute("usuario");
    }

    protected void despacharMetodoAccion()
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        String accion = request.getParameter("accion") != null ? request.getParameter("accion").toLowerCase() : "index";
        String metodoRequest = request.getMethod().toLowerCase();
        String nombreMetodoAccion = accion + "_" + metodoRequest;

        Method metodoAccion = this.getClass().getMethod(nombreMetodoAccion);
        metodoAccion.invoke(this);
    }

    protected void mostrarVista(String vista) {
        mostrarVista(vista, null);
    }

    protected void mostrarVista(String vista, ViewModel modelo) {
        cargarEnModeloMensajeSesion(modelo);
        request.setAttribute("modelo", modelo);

        try {
            RequestDispatcher despachador = request.getRequestDispatcher("/WEB-INF/Vistas/" + vista);

            if (despachador != null) {
                despachador.forward(request, response);
            }
        } catch (IOException | ServletException ex) {
            System.out.println("¡ERROR! No se pudo mostrar la vista " + vista + ".");
        }
    }

    protected ViewModel cargarModelo(ViewModel modelo) {
        ArrayList<String> parametros = Collections.list(request.getParameterNames());

        String nombreSetter;

        for (String p : parametros) {
            nombreSetter = "set" + p.substring(0, 1).toUpperCase() + p.substring(1);

            for (Method m : modelo.getClass().getMethods()) {
                
                try {
                    if (m.getName().equals(nombreSetter) && m.getParameterTypes().length > 0) {
                        switch (m.getParameterTypes()[0].getSimpleName()) {
                            case "String":
                                m.invoke(modelo, request.getParameter(p));
                                break;
                            case "boolean":
                                m.invoke(modelo, request.getParameter(p) != null);
                                break;
                            case "List":
                                m.invoke(modelo, Arrays.asList(request.getParameterValues(p)));
                                break;
                            default:
                                break;
                        }
                    }
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                    System.out.println("¡ERROR! No se pudo invocar al setter " + nombreSetter + " al cargar el modelo.");
                }
            }
        }

        return modelo;
    }

    protected void cargarEnModeloMensajeSesion(ViewModel modelo) {
        String mensajeSesion = (String) request.getSession().getAttribute("mensaje");

        if (mensajeSesion != null && modelo != null) {
            if (modelo.getMensaje() == null) {
                modelo.setMensaje(mensajeSesion);
            } else {
                modelo.setMensaje(modelo.getMensaje() + "<br /><br />" + mensajeSesion);
            }

            request.getSession().removeAttribute("mensaje");
        }
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.sesion = request.getSession();
        this.request = request;
        this.response = response;

        try {
            despacharMetodoAccion();
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException ex) {
            System.out.println("¡ERROR! No se pudo despachar el método de la acción solicitada.");
            System.out.println(ex.getMessage());
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
