package arq.prototipo.servlets;

import arq.prototipo.datatypes.DTEstudiante;
import arq.prototipo.rest.ClienteJersey;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet(name = "Login", urlPatterns = {"/login"})
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

            DTEstudiante usuario = new ClienteJersey().login(ci, pass);
            if (usuario == null) {
                throw new Exception("Usuario o contraseña incorrectos.");
            } else {
                sesion.setAttribute("mensaje", usuario.getNombre());
                response.sendRedirect("mensaje.jsp");
            }
        } catch (Exception ex) {
            sesion.setAttribute("mensaje", ex.getMessage());
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
