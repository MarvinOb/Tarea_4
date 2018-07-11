package com.fireblend.uitest.ui;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fireblend.uitest.DataBase.ContactDB;
import com.fireblend.uitest.R;
import com.fireblend.uitest.Utils.Preferences;

import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsFragment extends Fragment implements View.OnClickListener {

    TextView tvNombre;
    TextView tvEdad;
    TextView tvEmail;
    TextView tvPhone;
    TextView tvProvincia;
    Button btnRegresar;
    Button btnEliminar;
    Button btnRespaldar;


    InterfazBotones interfazBotones;

    public DetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);


        tvNombre=(TextView)view.findViewById(R.id.tvNombre);
        tvEmail=(TextView)view.findViewById(R.id.tvEmail);
        tvEdad=(TextView)view.findViewById(R.id.tvEdad);
        tvPhone=(TextView)view.findViewById(R.id.tvPhone);
        tvProvincia=(TextView)view.findViewById(R.id.tvProvincia);
        btnRegresar=(Button)view.findViewById(R.id.botonRegresar);
        btnEliminar=(Button)view.findViewById(R.id.botonEliminar);
        btnRespaldar=(Button)view.findViewById(R.id.botonRespaldar);

        btnRegresar.setOnClickListener(this);
        btnEliminar.setOnClickListener(this);
        btnRespaldar.setOnClickListener(this);

        ContactDB contacto = getArguments().getParcelable("contacto");

        tvNombre.setText(contacto.getName());
        tvEmail.setText(contacto.getEmail());
        tvEdad.setText(contacto.getAge()+"");
        tvPhone.setText(contacto.getPhone());
        tvProvincia.setText(contacto.getProvincia());

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //Se asigna a la instancia de la interfaz el contexto recibido en este
        //metodo, que corresponde al activity padre de este fragment.
        interfazBotones = (InterfazBotones) context;
    }

    @Override
    public void onClick(View view)
    {
        if(view.equals(btnRegresar))
        {
            interfazBotones.eventoBotonRegresar();
        }
        else
            if(view.equals(btnEliminar))
            {
                interfazBotones.eventoBotonEliminar();
            }
            else
                if(view.equals((btnRespaldar)))
                {
                    interfazBotones.eventoBotonDescargar();
                }
    }

    public interface InterfazBotones{
        void eventoBotonEliminar();
        void eventoBotonDescargar();
        void eventoBotonRegresar();
    }
}
