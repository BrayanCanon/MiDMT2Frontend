package juan.example.com.diabetest2.util;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Mision implements Parcelable {
    String nombre,tipo,categoria,nomnivel;
    Date fechainicio,fechafinal;
    int nivel,idMision,idCategoria;

    public  Mision(){}
    protected Mision(Parcel in) {
        nombre = in.readString();
        tipo = in.readString();
        categoria = in.readString();
        nomnivel=in.readString();
        nivel = in.readInt();
        idMision = in.readInt();
        idCategoria = in.readInt();
    }

    public static final Creator<Mision> CREATOR = new Creator<Mision>() {
        @Override
        public Mision createFromParcel(Parcel in) {
            return new Mision(in);
        }

        @Override
        public Mision[] newArray(int size) {
            return new Mision[size];
        }
    };

    public String getNomnivel() {
        return nomnivel;
    }

    public void setNomnivel(String nomnivel) {
        this.nomnivel = nomnivel;
    }

    public int getIdMision() {
        return idMision;
    }

    public void setIdMision(int idMision) {
        this.idMision = idMision;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNombre() {
        return nombre;
    }


    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Date getFechainicio() {
        return fechainicio;
    }

    public void setFechainicio(Date fechainicio) {
        this.fechainicio = fechainicio;
    }

    public Date getFechafinal() {
        return fechafinal;
    }

    public void setFechafinal(Date fechafinal) {
        this.fechafinal = fechafinal;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(nombre);
        parcel.writeString(tipo);
        parcel.writeString(categoria);
        parcel.writeString(nomnivel);
        parcel.writeInt(nivel);
        parcel.writeInt(idMision);
        parcel.writeInt(idCategoria);
    }
}
