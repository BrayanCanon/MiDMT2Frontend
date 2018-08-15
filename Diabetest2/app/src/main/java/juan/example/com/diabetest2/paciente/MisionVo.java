package juan.example.com.diabetest2.paciente;

public class MisionVo {
    private String titulo;
    private String categoria;
    private String dificultad;
public MisionVo(String titulo,String categoria,String dificultad){
    this.titulo=titulo;
    this.categoria=categoria;
    this.dificultad=dificultad;

}
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDificultad() {
        return dificultad;
    }

    public void setDificultad(String dificultad) {
        this.dificultad = dificultad;
    }
}
