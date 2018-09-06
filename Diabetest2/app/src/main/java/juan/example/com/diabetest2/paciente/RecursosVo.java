package juan.example.com.diabetest2.paciente;


public class RecursosVo {
    private int tipo_recurso;
    private String nombre,descripcion;

    public RecursosVo(int tipo_recurso, String nombre, String descripcion) {
        this.tipo_recurso = tipo_recurso;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public int getTipo_recurso() {
        return tipo_recurso;
    }

    public void setTipo_recurso(int tipo_recurso) {
        this.tipo_recurso = tipo_recurso;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
