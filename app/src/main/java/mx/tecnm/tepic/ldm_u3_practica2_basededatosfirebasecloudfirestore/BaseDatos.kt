package mx.tecnm.tepic.ldm_u3_practica2_basededatosfirebasecloudfirestore

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class BaseDatos(
    context: Context?,
    name: String?,
    factory: SQLiteDatabase.CursorFactory?,
    version: Int
) : SQLiteOpenHelper(context, name, factory, version) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE NOTAS(ID INTEGER PRIMARY KEY AUTOINCREMENT, TITULO VARCHAR(200), CONTENIDO VARCHAR(800), HORA VARCHAR(200), FECHA VARCHAR(200))")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }
}