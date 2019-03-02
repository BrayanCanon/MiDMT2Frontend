package juan.example.com.diabetest2.paciente;

public class VerificacionVo {
    private int numDia;
    private Boolean verif;
    private String fecha;

    public VerificacionVo(int numDia, Boolean verif) {
        this.numDia = numDia;
        this.verif = verif;
    }

    public VerificacionVo(int numeroDia, boolean verifPaciente, String fecha) {
        this.numDia = numeroDia;
        this.verif = verifPaciente;
        this.fecha=fecha;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getNumDia() {
        return numDia;
    }

    public void setNumDia(int numDia) {
        this.numDia = numDia;
    }

    public Boolean getVerif() {
        return verif;
    }

    public void setVerif(Boolean verif) {
        this.verif = verif;
    }
}
