package juan.example.com.diabetest2.paciente;

import java.io.Serializable;

public class MisionVo implements Serializable {
    private String titulo;
    private String categoria;
    private String dificultad;
    private String idMision,idMisionPaciente;
    private String descripcion;
    private int condicionImagen;





    public MisionVo(String titulo, String categoria, String dificultad, String idMision, String descripcion){
        this.titulo=titulo;
        this.categoria=categoria;
        this.dificultad=dificultad;
        this.descripcion=descripcion;
        this.idMision=idMision;

    }

    public MisionVo(String titulo, String categoria, String dificultad, String idMision, String idMisionPaciente, String descripcion, int condicionImagen) {
        this.titulo = titulo;
        this.categoria = categoria;
        this.dificultad = dificultad;
        this.idMision = idMision;
        this.idMisionPaciente = idMisionPaciente;
        this.descripcion = descripcion;
        this.condicionImagen = condicionImagen;
    }

    public MisionVo(String titulo, String categoria, String dificultad, String idMision, String descripcion, String idMisionPaciente) {
        this.titulo = titulo;
        this.categoria = categoria;
        this.dificultad = dificultad;
        this.idMision = idMision;
        this.idMisionPaciente = idMisionPaciente;
        this.descripcion = descripcion;
    }
    public MisionVo(String titulo, String categoria, String dificultad, String idMision, String descripcion,int condicionImagen) {
        this.titulo = titulo;
        this.categoria = categoria;
        this.dificultad = dificultad;
        this.idMision = idMision;
        this.idMisionPaciente = idMisionPaciente;
        this.descripcion = descripcion;
        this.condicionImagen = condicionImagen;
    }


    public String getIdMisionPaciente() {
        return idMisionPaciente;
    }

    public void setIdMisionPaciente(String idMisionPaciente) {
        this.idMisionPaciente = idMisionPaciente;
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

    public int getCondicionImagen() {
        return condicionImagen;
    }

    public void setCondicionImagen(int condicionImagen) {
        this.condicionImagen = condicionImagen;
    }
}
