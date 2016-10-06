package org.arqrifa.servlets;

import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.arqrifa.datatypes.DTSolicitud;
import org.arqrifa.datatypes.DTUsuario;
import org.arqrifa.rest.ClienteJersey;
import org.arqrifa.viewmodels.VMSolicitudes;
import org.arqrifa.viewmodels.ViewModel;

@WebServlet(name = "ControladorEncargado", urlPatterns = {"/ControladorEncargado"})
public class ControladorEncargado extends HttpServlet {

    HttpServletRequest request;
    HttpServletResponse response;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.request = request;
        this.response = response;
        String accion = request.getParameter("accion");
        if (accion.equals("solicitudes")) {
            solicitudes_get();
        }
    }

    protected void solicitudes_get() throws IOException {
        HttpSession sesion = request.getSession();
        try {
            List<DTSolicitud> lista = new ClienteJersey().listarSolicitudes((DTUsuario) sesion.getAttribute("usuario"));
            sesion.setAttribute("modelo", new VMSolicitudes(lista, ""));
            RequestDispatcher despachador = request.getRequestDispatcher("Vistas/Encargado/solicitudes.jsp");
            if (despachador != null) {
                despachador.forward(request, response);
            }
        } catch (Exception e) {
            sesion.setAttribute("modelo", new ViewModel(e.getMessage()));
            response.sendRedirect("Vistas/Encargado/solicitudes.jsp");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
