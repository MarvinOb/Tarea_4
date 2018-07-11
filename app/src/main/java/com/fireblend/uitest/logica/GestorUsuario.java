package com.fireblend.uitest.logica;

import android.content.Context;

public class GestorUsuario {

    private static LogicaUsuarios singleton;

    public static LogicaUsuarios obtenerLogicaUsuarios(Context context)
    {
        if(singleton==null)
        {
            singleton=new LogicaUsuarios(context);
        }

        return singleton;
    }
}
