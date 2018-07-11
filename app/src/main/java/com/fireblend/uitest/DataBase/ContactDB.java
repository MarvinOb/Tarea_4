package com.fireblend.uitest.DataBase;

import android.os.Parcel;
import android.os.Parcelable;

import com.fireblend.uitest.entities.Contact;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "contacts")
public class ContactDB implements Parcelable {
    @DatabaseField(generatedId = true, columnName = "contact_id")
    private int contactId;

    @DatabaseField(columnName = "name", canBeNull = false)
    private String name;

    @DatabaseField(columnName = "age", canBeNull = false)
    private int age;

    @DatabaseField(columnName = "email", canBeNull = false)
    private String email;

    @DatabaseField(columnName = "phone", canBeNull = true)
    private String phone;

    @DatabaseField(columnName = "provincia", canBeNull = false)
    private String provincia;

    public ContactDB()
    {}

    public ContactDB(String name, int age, String email, String phone, String nacionalidad, int id){
        this.name = name;
        this.age = age;
        this.email = email;
        this.phone = phone;
        this.provincia=nacionalidad;
        this.contactId=id;
    }

    public ContactDB(String name, int age, String email, String phone, String nacionalidad){
        this.name = name;
        this.age = age;
        this.email = email;
        this.phone = phone;
        this.provincia=nacionalidad;
    }

    public ContactDB(Parcel in){
        this.setName(in.readString());
        this.setAge(in.readInt());
        this.setEmail(in.readString());
        this.setPhone(in.readString());
        this.setProvincia(in.readString());
        this.setContactId(in.readInt());
    }

    public int getContactId() {
        return contactId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
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
        dest.writeInt(contactId);
    }

    public static final Parcelable.Creator<ContactDB> CREATOR=new Parcelable.Creator<ContactDB>()
    {
        public ContactDB createFromParcel(Parcel in)
        {
            return new ContactDB(in);
        }

        @Override
        public ContactDB[] newArray(int size)
        {
            return new ContactDB[size];
        }
    };
}
