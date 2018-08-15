package juan.example.com.diabetest2.util;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {
    private String title, genre, year;
    private int val;
    private boolean sel;


    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }

    public Movie(String title, String genre, String year, int val,boolean sel) {
        this.title = title;
        this.genre = genre;
        this.year = year;
        this.val=val;
        this.sel=sel;

    }

    public boolean isSel() {
        return sel;
    }

    public void setSel(boolean sel) {
        this.sel = sel;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(genre);
        parcel.writeString(year);
        parcel.writeInt(val);
        parcel.writeByte((byte) (sel ? 1 : 0));
    }
    public Movie(Parcel in) {
        this.title=in.readString();
        this.genre=in.readString();
        this.year=in.readString();
        this.val=in.readInt();
        this.sel=in.readByte()==1;
    }

}