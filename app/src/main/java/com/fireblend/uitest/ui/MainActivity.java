/*
    TAREA # 2 DEL CURSO DE PROGRAMACIÓN DE ANDROID

    MARVIN OCONITRILLO BONILLA
    JUNIO 2018
 */

package com.fireblend.uitest.ui;

import android.content.Intent;
import java.sql.SQLException;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import com.fireblend.uitest.Adapters.RecyclerAdapter;
import com.fireblend.uitest.DataBase.DatabaseHelper;
import com.fireblend.uitest.DataBase.ContactDB;
import com.fireblend.uitest.Main2ActivityContact;
import com.fireblend.uitest.Main2ActivityPreference;
import com.fireblend.uitest.R;
import com.fireblend.uitest.Utils.Preferences;
import com.fireblend.uitest.entities.Contact;
import com.j256.ormlite.dao.Dao;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView list;
    RecyclerView.Adapter adapter;
    //LinearLayoutManager layout;
    GridLayoutManager layout;
    Button nuevoContacto,btnPreferencias;
    ArrayList<ContactDB> contacts;
    DatabaseHelper helper;
    //int numColumnas=2, tamanoLetra=16,tipoVista=2;
    //boolean habilitarBorrar=false;
    //int colorBackground=android.R.color.background_dark;
    Preferences preferencias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Tarea #3 de Marvin");
        preferencias=new Preferences(getApplicationContext());

        //obtener la lista de contactos si es llamado de nuevo después de agregar uno
        //contacts = getIntent().getParcelableArrayListExtra("contacts");

        //setupList();
        cargarLista();

        list = (RecyclerView)findViewById(R.id.lista_contactos); //obtener recycler
        list.setHasFixedSize(true);
        list.setItemViewCacheSize(5);
        list.setDrawingCacheEnabled(true);
        list.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        layout=new GridLayoutManager(getApplicationContext(),preferencias.getNumColumnas()); //crear manager
        list.setLayoutManager(layout); //poner manager
        adapter=new RecyclerAdapter(this, contacts); //crear adapter y pasar contactos
        list.setAdapter(adapter); //poner adapter a recycler

        nuevoContacto=  (Button)findViewById(R.id.nuevoContacto);
        nuevoContacto.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), Main2ActivityContact.class); //crear intent
                //intent.putParcelableArrayListExtra("contacts",contacts); //pasar lista de contactos
                startActivity(intent); //iniciar nuevo intent
            }
        });

        btnPreferencias=(Button)findViewById(R.id.preferencias);
        btnPreferencias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), Main2ActivityPreference.class); //crear intent
                startActivity(intent); //iniciar nuevo intent
            }
        });


    }

    private void cargarLista()
    {
        //si contactos es nulo es porque es la primera vez en entrar y se llama al método para poner los iniciales
        if(contacts==null){
            //contacts = setupList(); //obtener lista inicial
            if (helper == null) {
                helper = new DatabaseHelper(this);
            }

            Dao<ContactDB, Integer> contactDao=null;
            contacts=new ArrayList();

            try
            {
                contactDao=helper.getContactDBDao();
                List<ContactDB> listaBD=contactDao.queryForAll();
                int cantidad=listaBD.size();

                for (int x = 0; x < cantidad; x++) {
                    ContactDB temp = listaBD.get(x);
                    ContactDB contacto = new ContactDB(temp.getName(), temp.getAge(), temp.getEmail(), temp.getPhone(), temp.getProvincia(), temp.getContactId());
                    contacts.add(contacto);
                }
            }
            catch (SQLException e)
            {e.printStackTrace();}
        }

    }


    private ArrayList<ContactDB> setupList() {
        final ArrayList<ContactDB> contacts = new ArrayList();

        //Lista ejemplo con datos estaticos. Normalmente, estos serían recuperados de una BD o un REST API.
        contacts.add(new ContactDB("Sergio", 28, "sergiome@gmail.com", "88854764", "Cartago",1));
        contacts.add(new ContactDB("Andres", 1, "alex@gmail.com", "88883644","Puntarenas",2));
        contacts.add(new ContactDB("Andrea", 2, "andrea@gmail.com", "98714764","Heredia",3));
        contacts.add(new ContactDB("Fabian", 3, "fabian@gmail.com", "12345678","Limón",4));
        contacts.add(new ContactDB("Ivan", 4, "ivan@gmail.com", "87654321","Guanacaste",5));
        contacts.add(new ContactDB("Gabriela", 5, "gabriela@gmail.com", "09871234","San José",6));
        contacts.add(new ContactDB("Alex", 6, "sergiome@gmail.com", "43215678","Cartago",7));


        return contacts;
    }
}
