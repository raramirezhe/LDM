package mx.tecnm.tepic.ldm_u3_practica2_basededatosfirebasecloudfirestore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main2.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        insertar.setOnClickListener {
            var horaF = SimpleDateFormat("hh:mm a")
            var fechaF = SimpleDateFormat("dd/m/yyyy")
            var hora = horaF.format(Date()).toString()
            var fecha = fechaF.format(Date()).toString()

            val nota = Notas(this)
            nota.titulo = tituloNuevo.text.toString()
            nota.contenido = contenidoNuevo.text.toString()
            nota.hora = hora
            nota.fecha = fecha

            val resultado = nota.insertar()
            if(resultado){
                Toast.makeText(this,"NOTA GUARDADA", Toast.LENGTH_LONG).show()
                tituloNuevo.text.clear()
                contenidoNuevo.text.clear()
            }else{
                Toast.makeText(this,"ERROR! NO SE GUARDO LA NOTA", Toast.LENGTH_LONG).show()
            }
        }
        regresar.setOnClickListener {
            val intento = Intent(this, MainActivity::class.java)
            startActivity(intento)
        }

    }
}