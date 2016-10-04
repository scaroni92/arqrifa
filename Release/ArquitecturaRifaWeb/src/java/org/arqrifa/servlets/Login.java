package org.arqrifa.servlets;

import org.arqrifa.datatypes.DTUsuario;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.arqrifa.rest.ClienteJersey;
import org.arqrifa.viewmodels.ViewModel;

@WebServlet(name = "login", urlPatterns = {"/login"})
public class Login extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession sesion = request.getSession();

        try {
            String ci = request.getParameter("user");
            String pass = request.getParameter("pass");

            if (ci.isEmpty() || pass.isEmpty()) {
                throw new Exception("Debe completar todos los campos");
            }

            DTUsuario usuario = new ClienteJersey().login(ci, pass);
            if (usuario == null) {
                throw new Exception("Usuario o contraseña incorrectos.");
            }
            sesion.setAttribute("usuario", usuario);
            if (usuario.getRol().equals("estudiante")) {
                response.sendRedirect("Vistas/Estudiante/index.jsp");
            }
            if (usuario.getRol().equals("encargado")) {
                response.sendRedirect("Vistas/Encargado/index.jsp");
            }

        } catch (Exception ex) {
            sesion.setAttribute("modelo", new ViewModel(ex.getMessage()));
            response.sendRedirect("index.jsp");
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
