package com.fireblend.uitest.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fireblend.uitest.DataBase.ContactDB;
import com.fireblend.uitest.DataBase.DatabaseHelper;
import com.fireblend.uitest.Main2ActivityContact;
import com.fireblend.uitest.Main2ActivityDetalles;
import com.fireblend.uitest.R;
import com.fireblend.uitest.Utils.Preferences;
import com.fireblend.uitest.entities.Contact;
import com.fireblend.uitest.ui.DetailsActivity;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private List<ContactDB> mDataset;
    private LayoutInflater inflater;
    private Context context;
    private Preferences preferencias;

    public RecyclerAdapter(Context context,List<ContactDB> myDataset) {
        this.inflater=LayoutInflater.from(context);
        mDataset = myDataset;
        this.context=context;
        this.preferencias=new Preferences(this.context);
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        // create a new view
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_item,parent,false);
        ViewHolder vh = new ViewHolder(v, this.context);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        TextView itemName=holder.mTextViewName;
        itemName.setText("Nombre: "+mDataset.get(position).getName());
        itemName.setTextSize(preferencias.getTamanoLetra());

        TextView itemAge=holder.mTextViewAge;
        itemAge.setText("Edad: "+mDataset.get(position).getAge());
        itemAge.setTextSize(preferencias.getTamanoLetra());

        TextView itemEmail=holder.mTextViewEmail;
        itemEmail.setText("Email: "+mDataset.get(position).getEmail());
        itemEmail.setTextSize(preferencias.getTamanoLetra());

        TextView itemPhone=holder.mTextViewPhone;
        itemPhone.setText("Phone: "+mDataset.get(position).getPhone());
        itemPhone.setTextSize(preferencias.getTamanoLetra());

        TextView itemNacionalidad=holder.mTextViewProvincia;
        itemNacionalidad.setText("Provincia: "+mDataset.get(position).getProvincia());
        itemNacionalidad.setTextSize(preferencias.getTamanoLetra());

        holder.idElemento=mDataset.get(position).getContactId();
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    // convenience method for getting data at click position
    ContactDB getItem(int id) {
        return mDataset.get(id);
    }


    /*
    RELEVANCIA
    * La relevancia es que permite hacer más rápido el renderizado de los datos en forma de lista
    * en lugar de tener que cargar uno por uno para mostrarlo, esto permite mostrar muha información
    * ordenado en un patrón determinado.
    * */

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder{
        // each data item is just a string in this case
        public TextView mTextViewName;
        public TextView mTextViewAge;
        public TextView mTextViewPhone;
        public TextView mTextViewEmail;
        public TextView mTextViewProvincia;
        public Button buttonView, buttonViewBorrar;
        public Context context;
        DatabaseHelper helper;
        Dao<ContactDB, Integer> contactDao=null;
        public int idElemento=-1;


        public ViewHolder(View v, Context con) {
            super(v);
            this.context=con;

            if (helper == null) {
                helper = new DatabaseHelper(this.context);
            }

            try
            {
                contactDao=helper.getContactDBDao();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
            mTextViewName = (TextView) v.findViewById(R.id.name);
            mTextViewAge = (TextView) v.findViewById(R.id.age);
            mTextViewPhone = (TextView) v.findViewById(R.id.phone);
            mTextViewEmail = (TextView) v.findViewById(R.id.email);
            mTextViewProvincia = (TextView) v.findViewById(R.id.nacionalidad);
            buttonView=(Button)v.findViewById(R.id.row_btn);

            buttonView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(context, DetailsActivity.class); //crear intent
                    intent.putExtra("IDContact",idElemento);
                    context.startActivity(intent); //iniciar nuevo intent
                }
            });
        }
    }
}
