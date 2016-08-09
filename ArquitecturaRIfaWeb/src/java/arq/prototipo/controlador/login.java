package arq.prototipo.controlador;

import arq.prototipo.datatypes.*;
import arq.prototipo.rest.ClienteRest;
import com.google.gson.Gson;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class login extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        DTEstudiante estudiante = null;
        HttpSession sesion = request.getSession();

        try {
            String ci = request.getParameter("user");
            String pass = request.getParameter("pass");
            String resultado = new ClienteRest().autenticar(pass, ci);
            
            if (resultado.contains("null")) {
                sesion.setAttribute("mensaje", "Usuario o  contraseña incorrectos.");
                response.sendRedirect("index.jsp");
            } else if (resultado.contains("excepcion")) {
                ExcepcionRest ex = new Gson().fromJson(resultado, ExcepcionRest.class);
                sesion.setAttribute("mensaje", ex.getMensaje());
                response.sendRedirect("index.jsp");
            }
            else {
                estudiante = new Gson().fromJson(resultado, DTEstudiante.class);
                sesion.setAttribute("mensaje", "Bienvenido " + estudiante.getNombre() + " " + estudiante.getApellido());
                response.sendRedirect("mensaje.jsp");
            }

        } catch (Exception ex) {
            sesion.setAttribute("mensaje", ex.getMessage());
            response.sendRedirect("index.jsp");
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
