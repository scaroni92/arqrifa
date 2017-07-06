package org.arqrifa.viewmodels;

import java.util.List;

public class Paginacion<T> {

    private List<T> elementos;
    private int cantidadElementosPorPagina;
    private int paginaSolicitada;
    private int cantidadPaginas;

    public Paginacion(List<T> elementos, int cantidadFilas) {
        this.cantidadElementosPorPagina = cantidadFilas;
        this.paginaSolicitada = 1;
        this.elementos = elementos;
    }

    public List<T> getElementos() {

        if (paginaSolicitada > getCantidadPaginas() || paginaSolicitada <= 0) {
            paginaSolicitada = 1;
        }

        int primerElemento = (paginaSolicitada - 1) * cantidadElementosPorPagina;
        int ultimoElemento = primerElemento + cantidadElementosPorPagina;

        if (ultimoElemento >= elementos.size()) {
            ultimoElemento = primerElemento + ((elementos.size() - primerElemento));
        }

        return elementos.subList(primerElemento, ultimoElemento);
    }

    public void setElementos(List<T> elementos) {
        this.elementos = elementos;
        cantidadPaginas = calcularCantidadPaginas();
    }

    private int calcularCantidadPaginas() {
        double cantPaginas = ((double) elementos.size() / (double) cantidadElementosPorPagina) * 100;
        //Si la cantidad es decimal se redondea hacia arriba
        return (int) Math.ceil(cantPaginas / 100.0);
    }

    public int getCantidadElementosPorPagina() {
        return cantidadElementosPorPagina;
    }

    public void setCantidadElementosPorPagina(int cantidadElementos) {
        this.cantidadElementosPorPagina = cantidadElementos;
    }

    public int getCantidadPaginas() {
        return cantidadPaginas;
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

    public boolean isPrimera() {
        return paginaSolicitada == 1;
    }

    public boolean isUltima() {
        return paginaSolicitada == cantidadPaginas;
    }

    public int getSiguiente() {
        return paginaSolicitada + 1;
    }

    public int getAnterior() {
        return paginaSolicitada - 1;
    }

}
