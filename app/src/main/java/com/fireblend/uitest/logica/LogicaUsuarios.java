package com.fireblend.uitest.logica;

import android.content.Context;
import android.content.Intent;

import com.fireblend.uitest.DataBase.ContactDB;
import com.fireblend.uitest.DataBase.DatabaseHelper;
import com.fireblend.uitest.entities.Contact;
import com.fireblend.uitest.ui.MainActivity;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

public class LogicaUsuarios {

    DatabaseHelper helper;
    Dao<ContactDB, Integer> contactDao=null;
    Context contexto;


    public  LogicaUsuarios(Context contexto)
    {
        this.contexto=contexto;
        if (helper == null) {
            helper = new DatabaseHelper(contexto);
        }

        try
        {
            contactDao=helper.getContactDBDao();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public ContactDB buscarContacto(int idContact)
    {
        try {
            List<ContactDB> resultado = contactDao.queryBuilder().where().idEq(idContact).query();
            if (resultado.size() > 0) {
                ContactDB contacto = resultado.get(0);
                return contacto;
            }
            else
                return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean borrarContacto(int idContact)
    {
        try {
            List<ContactDB> resultado = contactDao.queryBuilder().where().idEq(idContact).query();
            if (resultado.size() > 0) {
                ContactDB contacto = resultado.get(0);
                int resultadoBorrado=contactDao.delete(contacto);
                if(resultadoBorrado>0)
                    return true;
                else
                    return false;
            }
            else
                return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
