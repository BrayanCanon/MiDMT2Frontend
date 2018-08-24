package juan.example.com.diabetest2.util;

import android.os.Parcel;
import android.os.Parcelable;

public class Recurso implements Parcelable{

    private String nombre;
    private boolean chekeado;
    private String recursonom;

    public  Recurso(String nombre,String recursonom,boolean chekeado){
        this.nombre=nombre;
        this.chekeado=chekeado;
        this.recursonom=recursonom;

    }

    public String getRecursonom() {
        return recursonom;
    }

    public void setRecursonom(String recursonom) {
        this.recursonom = recursonom;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isChekeado() {
        return chekeado;
    }

    public void setChekeado(boolean chekeado) {
        this.chekeado = chekeado;
    }

    protected Recurso(Parcel in) {
    }

    public static final Creator<Recurso> CREATOR = new Creator<Recurso>() {
        @Override
        public Recurso createFromParcel(Parcel in) {
            return new Recurso(in);
        }

        @Override
        public Recurso[] newArray(int size) {
            return new Recurso[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
    }
}
