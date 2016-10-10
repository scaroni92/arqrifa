package org.arqrifa.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.arqrifa.rest.ClienteJersey;
import org.arqrifa.viewmodels.ViewModel;

public class Verificar extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession sesion = request.getSession();
        try {
            int codigo = Integer.parseInt(request.getParameter("codigo"));
            new ClienteJersey().verificarSolicitud(codigo);
            sesion.setAttribute("verificada", true);
        } catch (Exception ex) {
            sesion.setAttribute("modelo", new ViewModel(ex.getMessage()));
            sesion.setAttribute("verificada", false);
        }
        response.sendRedirect("verificar.jsp");
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
