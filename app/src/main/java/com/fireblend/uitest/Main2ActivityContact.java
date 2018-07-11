package com.fireblend.uitest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.fireblend.uitest.DataBase.ContactDB;
import com.fireblend.uitest.DataBase.DatabaseHelper;
import com.fireblend.uitest.entities.Contact;
import com.fireblend.uitest.ui.MainActivity;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;

public class Main2ActivityContact extends AppCompatActivity {

    SeekBar seekBar;
    TextView tvEdad;
    Spinner spinnerNacionalidad;
    ArrayList<Contact> listaContactos;
    Button agregar;
    EditText tvNombre,tvPhone,tvEmail;
    DatabaseHelper helper;
    Dao<ContactDB, Integer> contactDao=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2_contact);

        //si contactos es nulo es porque es la primera vez en entrar y se llama al método para poner los iniciales

        if (helper == null) {
            helper = new DatabaseHelper(this);
        }

        try
        {
            contactDao=helper.getContactDBDao();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }



        tvEdad=(TextView)findViewById(R.id.textViewEdad); //asociar text view

        seekBar=(SeekBar)findViewById(R.id.seekBarEdad); //asociar seekbar
        //eventos del seekbar
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progreso=1;

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(i>=1) {
                    progreso = i;
                    tvEdad.setText(progreso + "");
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //lista de provincias
        String[] provincias=new String[]{"Alajuela","Cartago","San José","Guanacaste","Puntarenas","Limón","Heredia"};

        //obtener referencia de spinner
        spinnerNacionalidad=(Spinner)findViewById(R.id.spinnerProvincia);

        //obtener referencia de text view de nombre
        tvNombre=(EditText) findViewById(R.id.editTextNombre);

        //obtener referencia de text view de phone
        tvPhone=(EditText)findViewById(R.id.editTextPhone);

        //obtener referencia de text view de email
        tvEmail=(EditText)findViewById(R.id.editTextEmail);

        //crear array adapter para las provincias
        ArrayAdapter<String> adapterSpinner=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,provincias);

        //poner la vista para el drop dowm list del spinner
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //poner el adapter al spinner
        spinnerNacionalidad.setAdapter(adapterSpinner);

        /*//obtener lista de contactos
        listaContactos= getIntent().getParcelableArrayListExtra("contacts");*/

        agregar=(Button)findViewById(R.id.botonAgregar);
        agregar.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        //crear nuevo contacto
                        /*Contact nuevoContacto=new Contact(tvNombre.getText().toString(),seekBar.getProgress(),tvEmail.getText().toString(),tvPhone.getText().toString(),spinnerNacionalidad.getSelectedItem().toString());
                        listaContactos.add(nuevoContacto); //agregarlo a la lista*/

                        try {
                            ContactDB nuevoContacto = new ContactDB(tvNombre.getText().toString(), seekBar.getProgress(), tvEmail.getText().toString(), tvPhone.getText().toString(), spinnerNacionalidad.getSelectedItem().toString());
                            contactDao.create(nuevoContacto);
                        }
                        catch (SQLException e)
                        {
                            e.printStackTrace();
                        }

                        Intent intent=new Intent(getApplicationContext(), MainActivity.class); //crear intent para regresar a pantalla principal
                        //intent.putParcelableArrayListExtra("contacts",listaContactos); //enviar lista con contactos
                        startActivity(intent); //iniciar nuevo intent
                    }
                }
        );

    }
}
