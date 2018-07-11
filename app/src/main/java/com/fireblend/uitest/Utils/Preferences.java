package com.fireblend.uitest.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferences {

    private static final String PREFS = "app.preferences";
    private SharedPreferences prefs;
    final static String PREF_FILE="app.prefs";
    private Context contexto;

    private int numColumnas=2;
    private int tamanoLetra=14;
    private boolean habilitarBorrar=true;
    private int colorBackground=android.R.color.white;

    public Preferences(Context contexto)
    {
        this.contexto=contexto;
        prefs =this.contexto.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE);
        leerPreferencias();
    }

    private void leerPreferencias()
    {
        try
        {
            setHabilitarBorrar(prefs.getBoolean("habilitarBorrar",true));
            setNumColumnas(prefs.getInt("numeroColumnas",1));
            setTamanoLetra(prefs.getInt("tamanoLetra",14));
            setColorBackground(prefs.getInt("colorBackground",android.R.color.white));
        }
        catch (Exception e)
        {

        }
    }

    public boolean guardarPreferencias()
    {
        try {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("habilitarBorrar", isHabilitarBorrar());
            editor.putInt("numeroColumnas", getNumColumnas());
            editor.putInt("tamanoLetra", getTamanoLetra());
            editor.putInt("colorBackground", getColorBackground());
            editor.commit();
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }

    public int getNumColumnas() {
        return numColumnas;
    }

    public void setNumColumnas(int numColumnas) {
        this.numColumnas = numColumnas;
    }

    public int getTamanoLetra() {
        return tamanoLetra;
    }

    public void setTamanoLetra(int tamanoLetra) {
        this.tamanoLetra = tamanoLetra;
    }

    public boolean isHabilitarBorrar() {
        return habilitarBorrar;
    }

    public void setHabilitarBorrar(boolean habilitarBorrar) {
        this.habilitarBorrar = habilitarBorrar;
    }

    public int getColorBackground() {
        return colorBackground;
    }

    public void setColorBackground(int colorBackground) {
        this.colorBackground = colorBackground;
    }
}
