package com.fireblend.uitest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.fireblend.uitest.Utils.Preferences;
import com.fireblend.uitest.ui.MainActivity;

public class Main2ActivityPreference extends AppCompatActivity {

    Button btnGuardar;
    Spinner comboTamanoLetra;
    RadioButton rbVistaGrid,rbVistaLista,rbWhite,rbHoloRedLight,rbHoloOrangeDark,rbHoloBlueDark;
    String[] tamanos=new String[]{"12","14","16"};
    Preferences preferencias;
    Switch habilitarBotonBorrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2_preference);

        preferencias=new Preferences(this);

        btnGuardar=(Button)findViewById(R.id.btnGuardar);
        comboTamanoLetra=(Spinner)findViewById(R.id.spinnerTamanoLetra);
        rbVistaGrid=(RadioButton)findViewById(R.id.rbVistaGrid);
        rbVistaLista=(RadioButton)findViewById(R.id.rbVistaLista);
        rbWhite=(RadioButton)findViewById(R.id.rbWhite);
        rbHoloRedLight=(RadioButton)findViewById(R.id.rbHoloRedLight);
        rbHoloOrangeDark=(RadioButton)findViewById(R.id.rbHoloOrangeDark);
        rbHoloBlueDark=(RadioButton)findViewById(R.id.rbHoloBlueDark);
        habilitarBotonBorrar=(Switch)findViewById(R.id.switchbtnEliminar);


        //crear array adapter para los tamaños
        ArrayAdapter<String> adapterSpinner=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,tamanos);

        //poner la vista para el drop dowm list del spinner
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //poner el adapter al spinner
        comboTamanoLetra.setAdapter(adapterSpinner);

        chequearColor(preferencias.getColorBackground()); //asignar check en radio button correcto
        asignarTamano(preferencias.getTamanoLetra());
        chequearLista(preferencias.getNumColumnas());
        habilitarBotonBorrar.setChecked(preferencias.isHabilitarBorrar());

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                preferencias.guardarPreferencias();

                Toast.makeText(getApplicationContext(),"Se guardaron las preferencias",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(getApplicationContext(), MainActivity.class); //crear intent
                startActivity(intent); //iniciar nuevo intent
            }
        });
        comboTamanoLetra.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int valor=Integer.parseInt(tamanos[i].trim());
                preferencias.setTamanoLetra(valor);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        habilitarBotonBorrar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                preferencias.setHabilitarBorrar(b);
            }
        });


    }

    public void chequearColor(int color)
    {
        if(color== android.R.color.white)
        {
            rbWhite.setChecked(true);
        }
        else
        if(color== android.R.color.holo_red_light)
        {
            rbHoloRedLight.setChecked(true);
        }
        else
        if(color== android.R.color.holo_orange_dark)
        {
            rbHoloOrangeDark.setChecked(true);
        }
        else
        if(color== android.R.color.holo_blue_dark)
        {
            rbHoloRedLight.setChecked(true);
        }
    }

    public void chequearLista(int vista)
    {
        if(vista== 1)
        {
            rbVistaLista.setChecked(true);
        }
        else
        if(vista== 2)
        {
            rbVistaGrid.setChecked(true);
        }
    }

    public void asignarTamano(int tamano)
    {
        for(int x=0;x<tamanos.length;x++){
            String temp=tamano+"";
            if(tamanos[x].trim().equals(temp.trim()))
            {
                comboTamanoLetra.setSelection(x);
                break;
            }
        }
    }



    public void onRadioButtonListaClicked(View vista)
    {
        if(rbVistaLista.isChecked())
        {
            preferencias.setNumColumnas(1);
        }

        if(rbVistaGrid.isChecked())
        {
            preferencias.setNumColumnas(2);
        }
    }

    public void onRadioButtonColoresClicked(View vista)
    {
        if(rbHoloRedLight.isChecked())
        {
            preferencias.setColorBackground(android.R.color.holo_red_light);
        }

        if(rbWhite.isChecked())
        {
            preferencias.setColorBackground(android.R.color.white);
        }

        if(rbHoloBlueDark.isChecked())
        {
            preferencias.setColorBackground(android.R.color.holo_blue_dark);
        }

        if(rbHoloOrangeDark.isChecked())
        {
            preferencias.setColorBackground(android.R.color.holo_orange_dark);
        }
    }
}
