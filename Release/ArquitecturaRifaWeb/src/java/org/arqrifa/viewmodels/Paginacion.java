package org.arqrifa.viewmodels;

import java.util.List;

public class Paginacion<T> {

    private List<T> elementos;
    private int cantidadElementos;
    private int paginaSolicitada;

    public Paginacion(List<T> elementos, int cantidadFilas) {
        this.cantidadElementos = cantidadFilas;
        this.paginaSolicitada = 1;
        this.elementos = elementos;
    }

    public List<T> getElementos() {
        
        if (paginaSolicitada > getCantidadPaginas() || paginaSolicitada <= 0) {
            paginaSolicitada = 1;
        }
        
        int primerElemento = (paginaSolicitada - 1) * cantidadElementos;
        int ultimoElemento = primerElemento + cantidadElementos;

        if (ultimoElemento >= elementos.size()) {
            ultimoElemento = primerElemento + ((elementos.size() - primerElemento));
        }

        return elementos.subList(primerElemento, ultimoElemento);
    }

    public void setElementos(List<T> elementos) {
        this.elementos = elementos;
    }

    public int getCantidadElementos() {
        return cantidadElementos;
    }

    public void setCantidadElementos(int cantidadElementos) {
        this.cantidadElementos = cantidadElementos;
    }

    public int getCantidadPaginas() {
        double cantPaginas = ((double) elementos.size() / (double) cantidadElementos) * 100;
        //Si la cantidad es decimal se redondea hacia arriba
        return (int) Math.ceil(cantPaginas / 100.0);
    }

    public int getPaginaSolicitada() {
        return paginaSolicitada;
    }

    public void setPaginaSolicitada(String paginaSolicitada) {

        try {
            this.paginaSolicitada = Integer.parseInt(paginaSolicitada);
        } catch (NumberFormatException e) {
        }

    }

}
