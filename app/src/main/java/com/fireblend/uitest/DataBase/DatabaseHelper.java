package com.fireblend.uitest.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import java.sql.SQLException;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    private Dao<ContactDB, Integer> mContactDao=null;

    public DatabaseHelper(Context context)
    {
        super(context,"ormlite.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, ContactDB.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db,                          ConnectionSource connectionSource,
                          int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, ContactDB.class, true);
            onCreate(db, connectionSource);
        } catch (java.sql.SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //Metodos de ayuda para Usuario.java
    public Dao<ContactDB, Integer> getContactDBDao() throws SQLException {
        if (mContactDao == null) {
            mContactDao = getDao(ContactDB.class);
        }
        return mContactDao;
    }
    @Override
    public void close() {
        mContactDao = null;
        super.close();
    }
}
