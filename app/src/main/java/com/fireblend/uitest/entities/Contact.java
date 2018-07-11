package com.fireblend.uitest.entities;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Sergio on 8/20/2017.
 */

public class Contact implements Parcelable {
    //Clase entidad para contener cada elemento de la lista, el cual representa un Contacto.
    public String name;
    public int age;
    public String email;
    public String phone;
    public String provincia;
    public  int id;


    public Contact(String name, int age, String email, String phone, String nacionalidad, int id){
        this.name = name;
        this.age = age;
        this.email = email;
        this.phone = phone;
        this.provincia=nacionalidad;
        this.id=id;
    }

    public Contact()
    {
        this.name = "";
        this.age = 0;
        this.email = "";
        this.phone = "";
        this.provincia="";
        this.id=0;
    }

    public Contact(Parcel in){
        this.name = in.readString();
        this.age = in.readInt();
        this.email = in.readString();
        this.phone = in.readString();
        this.provincia= in.readString();
        this.id=in.readInt();
    }

    public int getId(){return id;}

    public void setId(int id){this.id=id;}

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name=name;
    }

    public int getAge(){
        return age;
    }

    public void setAge(int age){
        this.age=age;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email=email;
    }

    public String getPhone(){
        return phone;
    }

    public void setPhone(String phone){
        this.phone=phone;
    }

    public String getProvincia(){
        return provincia;
    }

    public void setProvincia(String provinvia){
        this.provincia=provinvia;
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest,int flags){
        dest.writeString(name);
        dest.writeInt(age);
        dest.writeString(email);
        dest.writeString(phone);
        dest.writeString(provincia);
        dest.writeInt(id);
    }

    public static final Parcelable.Creator<Contact> CREATOR=new Parcelable.Creator<Contact>()
    {
        public Contact createFromParcel(Parcel in)
        {
            return new Contact(in);
        }

        @Override
        public Contact[] newArray(int size)
        {
            return new Contact[size];
        }
    };
}
