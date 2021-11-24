package mx.tecnm.tepic.ldm_u3_practica2_basededatosfirebasecloudfirestore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main2.*
import kotlinx.android.synthetic.main.activity_main3.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity3 : AppCompatActivity() {
    var id = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)

        var extra = intent.extras
        id = extra!!.getString("idActualizar")!!

        val nota = Notas(this).consultar(id)
        tituloAct.setText(nota.titulo)
        contenidoAct.setText(nota.contenido)

        actualizar.setOnClickListener {
            val actNota = Notas(this)
            var horaF = SimpleDateFormat("hh:mm a")
            var fechaF = SimpleDateFormat("dd/m/yyyy")
            var hora = horaF.format(Date()).toString()
            var fecha = fechaF.format(Date()).toString()

            actNota.titulo = tituloAct.text.toString()
            actNota.contenido = contenidoAct.text.toString()
            actNota.hora = hora
            actNota.fecha = fecha

            val resultado = actNota.actualizar(id)
            if(resultado){
                Toast.makeText(this,"EXITO SE ACTUALIZO", Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(this,"ERROR! NO SE LOGRO ACTUALIZAR", Toast.LENGTH_LONG).show()
            } }
        regresarAct.setOnClickListener {
            val intento = Intent(this, MainActivity::class.java)
            startActivity(intento)
        }
    }
}