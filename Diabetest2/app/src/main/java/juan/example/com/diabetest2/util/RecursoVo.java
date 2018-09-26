package juan.example.com.diabetest2.util;

import java.io.Serializable;
import java.util.Date;

public class RecursoVo  implements Serializable{
    private String titulo,autor;
    private String decripcion;
    private String foto;
    private String fecha,video,idRecurso;


    public RecursoVo(String titulo, String autor, String decripcion, String foto, String fecha,String video,String idRecurso) {
        this.titulo = titulo;
        this.autor = autor;
        this.decripcion = decripcion;
        this.foto = foto;
        this.fecha = fecha;
        this.video=video;
        this.idRecurso=idRecurso;
    }
    public RecursoVo(String titulo, String autor, String decripcion, String foto, String fecha,String video) {
        this.titulo = titulo;
        this.autor = autor;
        this.decripcion = decripcion;
        this.foto = foto;
        this.fecha = fecha;
        this.video=video;

    }

    public String getIdRecurso() {
        return idRecurso;
    }

    public void setIdRecurso(String idRecurso) {
        this.idRecurso = idRecurso;
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

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
