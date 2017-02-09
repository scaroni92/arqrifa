package org.arqrifa.filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.arqrifa.datatypes.DTUsuario;
// SOLO ENCARGADOS

@WebFilter(filterName = "filtroAutenticarEncargado", urlPatterns = {"/encargado", "/solicitud", "/panel", "/cuestionario"})
public class FiltroAutenticarEncargado implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpSession sesion = ((HttpServletRequest) request).getSession();
        DTUsuario usuario = (DTUsuario) sesion.getAttribute("usuario");

        try {
            if (usuario == null) {
                request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
            } else if (!usuario.getRol().equals(DTUsuario.ENCARGADO) && !usuario.getRol().equals(DTUsuario.ADMIN)) {
                request.getRequestDispatcher("/WEB-INF/views/" + usuario.getRol().toLowerCase() + "/index.jsp").forward(request, response);
            } else {
                chain.doFilter(request, response);
            }
        } catch (Exception e) {
            System.out.println("Error al ejecutar el filtro");
        }

    }

    @Override
    public void destroy() {
        //
    }

}
