package juan.example.com.diabetest2.paciente;

public class PasoVo {
    private String descripcion;
    private String nombre;
    private int orden;

    public PasoVo(String descripcion, String nombre, int orden) {
        this.orden=orden;
        this.descripcion = descripcion;
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }
}
