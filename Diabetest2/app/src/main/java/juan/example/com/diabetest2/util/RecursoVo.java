package juan.example.com.diabetest2.util;

public class RecursoVo {
    private String titulo;
    private String decripcion;
    private int foto;

    public RecursoVo(String titulo, String decripcion, int foto) {
        this.titulo = titulo;
        this.decripcion = decripcion;
        this.foto = foto;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDecripcion() {
        return decripcion;
    }

    public void setDecripcion(String decripcion) {
        this.decripcion = decripcion;
    }

    public int getFoto() {
        return foto;
    }

    public void setFoto(int foto) {
        this.foto = foto;
    }
}
