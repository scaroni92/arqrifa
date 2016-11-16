package org.arqrifa.servlets;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.arqrifa.rest.ClienteJersey;
import org.arqrifa.viewmodels.VMVerificar;
import org.arqrifa.viewmodels.ViewModel;

public class Verificar extends HttpServlet {

    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected ClienteJersey cliente = new ClienteJersey();

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
        this.request = request;
        this.response = response;
        
        VMVerificar vm = null;
        try {
            int codigo = Integer.parseInt(request.getParameter("codigo"));
            cliente.verificarSolicitud(codigo);

            vm = new VMVerificar(true, "");

        } catch (Exception ex) {
            vm = new VMVerificar(false, ex.getMessage());

        }
        mostrarVista("verificar.jsp", vm);
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
