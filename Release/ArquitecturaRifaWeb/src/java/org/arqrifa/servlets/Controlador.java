package org.arqrifa.servlets;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.arqrifa.viewmodels.ViewModel;

@WebServlet(name = "Controlador", urlPatterns = {"/Controlador"})
public class Controlador extends HttpServlet {

    protected HttpSession sesion;
    protected HttpServletRequest request;
    protected HttpServletResponse response;

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

        request.setAttribute("modelo", modelo);

        try {
            RequestDispatcher despachador = request.getRequestDispatcher(vista);

            if (despachador != null) {
                despachador.forward(request, response);
            }
        } catch (Exception ex) {
            System.out.println("¡ERROR! No se pudo mostrar la vista " + vista + ".");
        }
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.sesion = request.getSession();
        this.request = request;
        this.response = response;

        try {
            despacharMetodoAccion();
        } catch (Exception ex) {
            System.out.println("¡ERROR! No se pudo despachar el método de la acción solicitada.");
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
