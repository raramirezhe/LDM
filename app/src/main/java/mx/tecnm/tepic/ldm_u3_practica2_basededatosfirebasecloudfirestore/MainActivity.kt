package mx.tecnm.tepic.ldm_u3_practica2_basededatosfirebasecloudfirestore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    val baseRemota = FirebaseFirestore.getInstance()
    var listaID = ArrayList<String>()
    var idN = ArrayList<Int>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        consultaNota()

        baseRemota.collection("NOTAS")
            .addSnapshotListener { querySnapshot, error ->
                if(error!=null){
                    error.message!!
                    return@addSnapshotListener
                }
                listaID.clear()
                for(document in querySnapshot!!){
                    listaID.add(document.id.toString())
                }
            }

        sync.setOnClickListener {
            eliminarId()
            var respuesta = Notas(this).sincronizar()
            if(respuesta == true){
                Toast.makeText(this,"SINCRONIZACIÓN EXITOSA",Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(this,"NO HAY DATOS",Toast.LENGTH_LONG).show()
            }
        }

        añadirNota.setOnClickListener{
            val intento = Intent(this, MainActivity2::class.java)
            startActivity(intento)
        }
    }

    private fun consultaNota() {
        val arregloNotas = Notas(this).consultar()
        listaDeNota.adapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arregloNotas)
        idN.clear()
        idN = Notas(this).obtenerIDs()
        activarEvento(listaDeNota)
    }

    private fun activarEvento(listaDeNota: ListView) {
        listaDeNota.setOnItemClickListener { adapterView, view, indiceSeleccionado, l ->
            val idSeleccionado = idN[indiceSeleccionado]
            AlertDialog.Builder(this)
                .setTitle("ATENCION")
                .setMessage("¿QUE DESEA HACER CON LA NOTA?")
                .setPositiveButton("EDITAR"){d, i->actualizar(idSeleccionado)}
                .setNegativeButton("ELIMINAR"){d, i-> eliminar(idSeleccionado)}
                .setNeutralButton("CANCELAR"){d, i->}
                .show()
        }
    }

    private fun eliminar(idSeleccionado: Int) {
        AlertDialog.Builder(this)
            .setTitle("ATENCIÓN")
            .setMessage("¿SEGURO QUE DESEAS ELIMINAR ID: ${idSeleccionado}?")
            .setPositiveButton("SI"){d,i->
                val resultado = Notas(this).eliminar(idSeleccionado)
                if(resultado){
                    Toast.makeText(this,"SE ELIMINO CON EXITO", Toast.LENGTH_LONG).show()
                    consultaNota()
                }else{
                    Toast.makeText(this,"ERROR: NO SE LOGRO ELIMINAR", Toast.LENGTH_LONG).show()
                }
            }
            .setNegativeButton("NO"){d,i->
                d.cancel()
            }
            .show()
    }

    private fun actualizar(idSeleccionado: Int) {
        val intento = Intent(this, MainActivity3::class.java)
        intento.putExtra("idActualizar",idSeleccionado.toString())
        startActivity(intento)

        AlertDialog.Builder(this).setMessage("DESEAS ACTUALIZAR LA LISTA?")
            .setPositiveButton("SI"){d,i->consultaNota()}
            .setNegativeButton("NO"){d,i->d.cancel()}
            .show()
    }

    fun eliminarId(){
        (0..listaID.size-1).forEach{
            val i = listaID.get(it)
            eliminarIDS(i)
        }
    }

    private fun eliminarIDS(i: String) {
        baseRemota.collection("NOTAS")
            .document(i)
            .delete()
    }
}