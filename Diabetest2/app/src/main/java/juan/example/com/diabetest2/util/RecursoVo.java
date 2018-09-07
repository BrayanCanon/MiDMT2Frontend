package juan.example.com.diabetest2.util;

import java.io.Serializable;
import java.util.Date;

public class RecursoVo  implements Serializable{
    private String titulo,autor;
    private String decripcion;
    private int foto;
    private String fecha,video;


    public RecursoVo(String titulo, String autor, String decripcion, int foto, String fecha,String video) {
        this.titulo = titulo;
        this.autor = autor;
        this.decripcion = decripcion;
        this.foto = foto;
        this.fecha = fecha;
        this.video=video;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
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
