package juan.example.com.diabetest2.paciente;

public class PasoVo {
    private String descripcion;
    private Boolean habCheckbox;
    private int orden;
    private String idMisionPaciente;


    public PasoVo(String descripcion, Boolean  habCheckBox, int orden,String idMisionPaciente) {
        this.orden=orden;
        this.descripcion = descripcion;
        this.habCheckbox = habCheckBox;
        this.idMisionPaciente= idMisionPaciente;
    }

    public String getIdMisionPaciente() {
        return idMisionPaciente;
    }

    public void setIdMisionPaciente(String idMisionPaciente) {
        this.idMisionPaciente = idMisionPaciente;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Boolean getHabCheckbox() {
        return habCheckbox;
    }

    public void setHabCheckbox(Boolean habCheckbox) {
        this.habCheckbox = habCheckbox;
    }

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }
}
