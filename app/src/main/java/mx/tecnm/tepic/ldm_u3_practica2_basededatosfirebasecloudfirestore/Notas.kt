package mx.tecnm.tepic.ldm_u3_practica2_basededatosfirebasecloudfirestore

import android.content.ContentValues
import android.content.Context
import com.google.firebase.firestore.FirebaseFirestore

class Notas (p: Context) {
    val baseRemota = FirebaseFirestore.getInstance()
    var titulo = ""
    var contenido = ""
    var hora = ""
    var fecha = ""
    val pnt = p

    fun insertar() : Boolean{
        val tblNotas = BaseDatos(pnt, "U3P2", null,1).writableDatabase

        val datos = ContentValues()
        datos.put("titulo",titulo)
        datos.put("contenido",contenido)
        datos.put("hora",hora)
        datos.put("fecha",fecha)

        val resultado = tblNotas.insert("NOTAS", null, datos)
        if(resultado == -1L){
            return false
        }
        return true
    }
    fun sincronizar():Boolean{
        val tblNotas = BaseDatos(pnt, "U3P2", null,1).readableDatabase
        val cursor = tblNotas.query("NOTAS", arrayOf("*"),null,null,null,null,null)
        if(cursor.moveToFirst()){
            do{
                var datos = hashMapOf(
                    "titulo" to cursor.getString(1).toString(),
                    "contenido" to cursor.getString(2).toString(),
                    "hora" to cursor.getString(3).toString(),
                    "fecha" to cursor.getString(4).toString()
                )
                baseRemota.collection("NOTAS").add(datos)
            }while(cursor.moveToNext())
        }else{
            return false
        }
        return true
    }
    fun consultar() : ArrayList<String>{
        val tblNotas = BaseDatos(pnt, "U3P2", null,1).readableDatabase
        val resultado = ArrayList<String>()
        val cursor = tblNotas.query("NOTAS", arrayOf("*"),null,null,null,null,null)
        if(cursor.moveToFirst()){
            do{
                var dato = "**${cursor.getString(1)}**"+"\n"+cursor.getString(2)
                resultado.add(dato)
            }while(cursor.moveToNext())
        }else{
            resultado.add("NO SE ENCONTRARON DATOS")
        }
        return resultado
    }
    fun obtenerIDs() : ArrayList<Int>{
        val tblNotas = BaseDatos(pnt, "U3P2", null,1).readableDatabase
        val resultado = ArrayList<Int>()
        val cursor = tblNotas.query("NOTAS", arrayOf("*"),null,null,null,null,null)
        if(cursor.moveToFirst()){
            do{
                resultado.add(cursor.getInt(0))
            }while(cursor.moveToNext())
        }
        return resultado
    }
    fun eliminar(idEliminar:Int) : Boolean {
        val tblNotas = BaseDatos(pnt, "U3P2", null,1).writableDatabase
        val resultado = tblNotas.delete("NOTAS","ID=?", arrayOf(idEliminar.toString()))
        if(resultado==0) return false
        return true
    }
    fun consultar(idABuscar:String) : Notas{
        val tblNotas = BaseDatos(pnt, "U3P2", null,1).writableDatabase
        val cursor = tblNotas.query("NOTAS", arrayOf("*"),"ID=?", arrayOf(idABuscar),null,null,null)
        val nota = Notas(MainActivity())
        if(cursor.moveToFirst()){
            nota.titulo = cursor.getString(1)
            nota.contenido = cursor.getString(2)
            nota.hora = cursor.getString(3)
            nota.fecha = cursor.getString(4)
        }
        return nota
    }
    fun actualizar(idActualizar:String): Boolean{
        val tblNotas = BaseDatos(pnt, "U3P2", null,1).readableDatabase
        val datos = ContentValues()
        datos.put("titulo",titulo)
        datos.put("contenido",contenido)
        datos.put("hora",hora)
        datos.put("fecha",fecha)

        val resultado = tblNotas.update("NOTAS",datos, "ID=?", arrayOf(idActualizar))
        if(resultado == 0) return false
        return true
    }
}