package org.arqrifa.controllers;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import org.arqrifa.rest.RecursoUsuarios;
import org.arqrifa.viewmodels.ViewModel;

@WebServlet(name = "ControladorLogin", urlPatterns = {"/login"})
public class ControladorLogin extends Controlador {
    
    public void index_get() {
        sesion.removeAttribute("usuario");
        mostrarVista("login.jsp");
    }
    
    public void index_post() {
        try {
            usuario = new RecursoUsuarios().login(request.getParameter("ci"), request.getParameter("pass"));
            sesion.setAttribute("usuario", usuario);
            response.sendRedirect("index");
        } catch (Exception ex) {
            mostrarVista("login.jsp", new ViewModel(ex.getMessage()));
        }
    }
    
    public void logout_get() {
        try {
            response.sendRedirect("login");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
