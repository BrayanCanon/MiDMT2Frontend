package juan.example.com.diabetest2.paciente;

public class VerificacionVo {
    private int numDia;
    private Boolean verif;

    public VerificacionVo(int numDia, Boolean verif) {
        this.numDia = numDia;
        this.verif = verif;
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
