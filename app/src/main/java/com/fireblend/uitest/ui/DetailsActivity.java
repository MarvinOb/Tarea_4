package com.fireblend.uitest.ui;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.fireblend.uitest.DataBase.ContactDB;
import com.fireblend.uitest.Main2ActivityDetalles;
import com.fireblend.uitest.R;
import com.fireblend.uitest.logica.GestorUsuario;
import com.fireblend.uitest.logica.LogicaUsuarios;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class DetailsActivity extends AppCompatActivity implements DetailsFragment.InterfazBotones {

    int idContact=-1;
    DetailsFragment fragment;
    FragmentManager fManager;
    ContactDB contacto;
    private static final int PERM_CODE = 1001;
    LogicaUsuarios logica;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent intent=getIntent();
        idContact=intent.getIntExtra("IDContact",-1);

        logica= GestorUsuario.obtenerLogicaUsuarios(this);
        contacto=logica.buscarContacto(idContact);

        fragment=new DetailsFragment();
        Bundle parametros=new Bundle();
        parametros.putParcelable("contacto",contacto);
        fragment.setArguments(parametros);

        fManager = getSupportFragmentManager();
        FragmentTransaction ft = fManager.beginTransaction();
        ft.add(R.id.frameLayout, fragment);
        ft.commit();
    }

    @Override
    public void eventoBotonEliminar()
    {
        AlertDialog alert=new AlertDialog.Builder(DetailsActivity.this)
                .setTitle("Eliminar")
                .setMessage("¿Desea eliminar el contacto?")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        logica.borrarContacto(idContact);
                        Intent intent=new Intent(getApplicationContext(), MainActivity.class); //crear intent
                        startActivity(intent); //iniciar nuevo intent
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).create();
        alert.show();
    }

    @Override
    public void eventoBotonDescargar()
    {
        int permissionCheck = ContextCompat.checkSelfPermission(DetailsActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(permissionCheck == PackageManager.PERMISSION_GRANTED){
            //Si tenemos permiso, continuamos
            exportarContacto();
        } else {
            //Si no, pedimos permiso
            askForPermission();
        }
    }

    @Override
    public void eventoBotonRegresar()
    {
        Intent intent=new Intent(getApplicationContext(), MainActivity.class); //crear intent
        startActivity(intent); //iniciar nuevo intent
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
        String texto="Nombre:"+contacto.getName()+","+
                "Edad:"+contacto.getAge()+","+
                "Email:"+contacto.getEmail()+","+
                "Phone:"+contacto.getPhone()+","+
                "Provincia:"+contacto.getProvincia();

        try
        {
            File fileContacto=new File(root,contacto.getName()+".txt"); //objeto de archivo

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
}
