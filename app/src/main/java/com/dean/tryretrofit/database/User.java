package com.dean.tryretrofit.database;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_user")
    private int id;
    @ColumnInfo(name = "email")
    private String email;
    @ColumnInfo(name = "nrp")
    private String nrp;
    @ColumnInfo(name = "bagian")
    private String bagian;
    @ColumnInfo(name = "date")
    private String date;

    protected User(Parcel in) {
        id = in.readInt();
        email = in.readString();
        nrp = in.readString();
        bagian = in.readString();
        date = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNrp() {
        return nrp;
    }

    public void setNrp(String nrp) {
        this.nrp = nrp;
    }

    public String getBagian() {
        return bagian;
    }

    public void setBagian(String bagian) {
        this.bagian = bagian;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public User(int id, String email, String nrp, String bagian, String date) {
        this.id = id;
        this.email = email;
        this.nrp = nrp;
        this.bagian = bagian;
        this.date = date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(email);
        parcel.writeString(nrp);
        parcel.writeString(bagian);
        parcel.writeString(date);
    }
}
