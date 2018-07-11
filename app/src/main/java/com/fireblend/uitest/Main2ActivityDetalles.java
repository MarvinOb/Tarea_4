package com.fireblend.uitest;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fireblend.uitest.DataBase.ContactDB;
import com.fireblend.uitest.DataBase.DatabaseHelper;
import com.fireblend.uitest.Utils.Preferences;
import com.fireblend.uitest.ui.MainActivity;
import com.j256.ormlite.dao.Dao;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;



public class Main2ActivityDetalles extends AppCompatActivity {

    DatabaseHelper helper;
    Dao<ContactDB, Integer> contactDao=null;
    TextView tvNombre,tvEdad,tvEmail,tvPhone,tvProvincia;
    Button btnRegresar,btnEliminar, btnRespaldar;
    int idContact=-1;
    private static final int PERM_CODE = 1001;

    Preferences preferencias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2_detalles);

        Intent intent=getIntent();
        idContact=intent.getIntExtra("IDContact",-1);
        tvNombre=(TextView)findViewById(R.id.textViewNombre);
        tvEmail=(TextView)findViewById(R.id.textViewEmail);
        tvEdad=(TextView)findViewById(R.id.textViewEdad);
        tvPhone=(TextView)findViewById(R.id.textViewPhone);
        tvProvincia=(TextView)findViewById(R.id.textViewProvincia);
        btnRegresar=(Button)findViewById(R.id.botonRegresar);
        btnEliminar=(Button)findViewById(R.id.botonEliminar);

        preferencias=new Preferences(getApplicationContext());

        //ajustar color
        RelativeLayout layout=(RelativeLayout)findViewById(R.id.layoutDetalles);
        layout.setBackgroundColor(getResources().getColor(preferencias.getColorBackground()));

        //ajustar preferencia de boton
        if(preferencias.isHabilitarBorrar()==true) {
            btnEliminar.setVisibility(View.VISIBLE);
        }
        else {
            btnEliminar.setVisibility(View.INVISIBLE);
        }

        btnRespaldar=(Button)findViewById(R.id.botonRespaldar);

        if (helper == null) {
            helper = new DatabaseHelper(getApplicationContext());
        }

        try
        {
            contactDao=helper.getContactDBDao();

            List<ContactDB> resultado= contactDao.queryBuilder().where().idEq(idContact).query();
            if(resultado.size()>0)
            {
                ContactDB contactoConsulta=resultado.get(0);

                tvNombre.setText(contactoConsulta.getName());
                tvEmail.setText(contactoConsulta.getEmail());
                tvEdad.setText(contactoConsulta.getAge()+"");
                tvPhone.setText(contactoConsulta.getPhone());
                tvProvincia.setText(contactoConsulta.getProvincia());

            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), MainActivity.class); //crear intent
                startActivity(intent); //iniciar nuevo intent
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                        AlertDialog alert=new AlertDialog.Builder(Main2ActivityDetalles.this)
                            .setTitle("Eliminar")
                            .setMessage("¿Desea eliminar el contacto?")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    try {
                                        List<ContactDB> resultado = contactDao.queryBuilder().where().idEq(idContact).query();
                                        if (resultado.size() > 0) {
                                            ContactDB contactoBorrar = resultado.get(0);
                                            contactDao.delete(contactoBorrar);

                                            Intent intent = new Intent(getApplicationContext(), MainActivity.class); //crear intent
                                            startActivity(intent); //iniciar nuevo intent
                                        }
                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            }).create();
                    alert.show();
                }catch (Exception e)
                {
                    e.printStackTrace();
                }

            }
        });

        btnRespaldar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int permissionCheck = ContextCompat.checkSelfPermission(Main2ActivityDetalles.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if(permissionCheck == PackageManager.PERMISSION_GRANTED){
                    //Si tenemos permiso, continuamos
                    exportarContacto();
                } else {
                    //Si no, pedimos permiso
                    askForPermission();
                }
            }
        });
    }

    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    private void exportarContacto(){
        File root = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS), "Contactos Exportados");
        if(!root.exists()) //si no existe la ruta
        {
            root.mkdirs(); //crearla
        }
        //Write to file
        String texto="Nombre:"+tvNombre.getText().toString()+","+
                "Edad:"+tvEdad.getText().toString()+","+
                "Email:"+tvEmail.getText().toString()+","+
                "Phone:"+tvPhone.getText().toString()+","+
                "Provincia:"+tvProvincia.getText().toString();

        try
        {
            File fileContacto=new File(root,tvNombre.getText().toString()+".txt"); //objeto de archivo

            if(fileContacto.exists()) //si ya existe
            {
                fileContacto.delete(); //borrarlo
            }

            FileWriter fileWrite = new FileWriter(fileContacto); //crear objeto para escribir archivo
            fileWrite.append(texto); //agregar
            fileWrite.flush(); //enviar
            fileWrite.close(); //cerrar
            Toast.makeText(this, "Contacto exportado", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            String s=e.getMessage();
            //Handle exception
        }
    }

    private void askForPermission() {
        //Se solicita permiso. Esta llamada es asincronica, por lo que tenemos que
        //implementar el metodo callback onRequestPermissionResult para procesar la
        //respuesta del usuario (ver abajo)
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                PERM_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //Si recibimos al menos un permiso y su valor es igual a PERMISSION_GRANTED, tenemos permiso
        if (grantResults.length > 0 &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED){
            btnRespaldar.callOnClick();
        } else {
            Toast.makeText(this, ":(", Toast.LENGTH_SHORT).show();
        }
    }
}
