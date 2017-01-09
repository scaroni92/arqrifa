package org.arqrifa.filtros;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.arqrifa.datatypes.DTUsuario;

@WebFilter(filterName = "filtroAutenticarAdmin", urlPatterns = {"/Admin", "/Reunion"})
public class FiltroAutenticarAdmin implements Filter {

    
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest pedido = (HttpServletRequest) request;
        HttpServletResponse respuesta = (HttpServletResponse) response;
        HttpSession sesion = pedido.getSession();

        DTUsuario usuario = (DTUsuario) sesion.getAttribute("usuario");

        try {
            if (usuario == null) {
                request.getRequestDispatcher("/WEB-INF/Vistas/login.jsp").forward(request, response);
            } else if (!usuario.getRol().equals("Admin")) {
                request.getRequestDispatcher("/WEB-INF/Vistas/" + usuario.getRol() + "/index.jsp").forward(request, response);
            } else {
                chain.doFilter(request, response);
            }
        } catch (Exception e) {
            System.out.println("Error al ejecutar el filtro");
        }

    }

    @Override
    public void destroy() {

    }

}
