package com.example.administrador.wup;

/**
 * Created by Administrador on 19/10/2017.
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BaseDatosAlarma extends SQLiteOpenHelper {

    String sqlCreate = "CREATE TABLE Alarmas (id INTEGER PRIMARY KEY AUTOINCREMENT, fecha DATE, hora TIME, numPreguntas INT, sonido TEXT)";

    public BaseDatosAlarma(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Si no existe la BD, la crea.
        db.execSQL(sqlCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAnterio, int versionNueva) {

        //Se elimina la versión anterior de la tabla
        db.execSQL("DROP TABLE IF EXISTS Alarmas");

        //Se crea la nueva versión de la tabla.
        db.execSQL(sqlCreate);
    }
}