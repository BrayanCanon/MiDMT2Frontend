package juan.example.com.diabetest2.paciente;

import java.io.Serializable;

public class MisionVo implements Serializable {
    private String titulo;
    private String categoria;
    private String dificultad;
    private String idMision;
    private String descripcion;




    public MisionVo(String titulo, String categoria, String dificultad, String idMision, String descripcion){
        this.titulo=titulo;
        this.categoria=categoria;
        this.dificultad=dificultad;
        this.descripcion=descripcion;
        this.idMision=idMision;

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
    public String getIdMision() {
        return idMision;
    }

    public void setIdMision(String idMision) {
        this.idMision = idMision;
    }
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
