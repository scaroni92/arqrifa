package org.arqrifa.viewmodels;


public class ViewModelList extends ViewModel{
    private String pagina;
    private int cantPaginas;
    private int cantFilas;

    public ViewModelList(String pagina, int cantPaginas, int cantFilas, String mensaje) {
        super(mensaje);
        this.pagina = pagina;
        this.cantPaginas = cantPaginas;
        this.cantFilas = cantFilas;
    }

    public ViewModelList() {
        this("1", 0, 3, "");
    }

    public String getPagina() {
        return pagina;
    }

    public void setPagina(String pagina) {
        this.pagina = pagina;
    }

    public int getCantPaginas() {
        return cantPaginas;
    }

    public void setCantPaginas(int cantPaginas) {
        this.cantPaginas = cantPaginas;
    }

    public int getCantFilas() {
        return cantFilas;
    }

    public void setCantFilas(int cantFilas) {
        this.cantFilas = cantFilas;
    }


    
    
}
