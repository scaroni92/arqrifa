package arq.prototipo.datatypes;

public class DTEstudiante implements java.io.Serializable {

    private int ci;
    private String nombre;
    private String apellido;
    private String contrasena;

    //<editor-fold defaultstate="collapsed" desc="Getters&Setters">
    public int getCi() {
        return ci;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setCi(int ci) {
        this.ci = ci;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
    //</editor-fold>

    public DTEstudiante(int ci, String nombre, String apellido, String contrasena) {
        setCi(ci);
        setNombre(nombre);
        setApellido(apellido);
        setContrasena(contrasena);
    }

    public DTEstudiante() {
        this(0, "n/d", "n/d", "n/d");
    }
}
