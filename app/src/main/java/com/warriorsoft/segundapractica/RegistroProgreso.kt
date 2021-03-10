package com.warriorsoft.segundapractica

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.room.Room
import com.warriorsoft.segundapractica.modelos.AppDatabase
import com.warriorsoft.segundapractica.modelos.Meta
import com.warriorsoft.segundapractica.modelos.Progress
import java.util.*

class RegistroProgreso : AppCompatActivity() {
    lateinit var fecha: TextView
    lateinit var txtPeso : EditText
    lateinit var btnGuardar : Button
    lateinit var db:AppDatabase
    var Anio: Int = 0
    var Mes: Int = 0
    var Dia: Int=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_progreso)
        val idMeta: Intent = intent
        var metaId = idMeta.getStringExtra("metaid")
        val c = Calendar.getInstance()
        Anio = c.get(Calendar.YEAR)
        Mes = c.get(Calendar.MONTH)
        Dia = c.get(Calendar.DAY_OF_MONTH)
        txtPeso = findViewById(R.id.peso_plai_text)
        btnGuardar = findViewById(R.id.btn_guardar)
        fecha = findViewById(R.id.txt_date_peaker)
        fecha.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                val dpd = DatePickerDialog(this@RegistroProgreso, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                    fecha.setText(""+dayOfMonth+"/"+month+"/"+year)
                },Anio,Mes,Dia)
                dpd.show()
            }
        })

        db = Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java, "Dieta"
        ).allowMainThreadQueries().build()

        btnGuardar.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                if (txtPeso.text.toString() == ""){
                    Toast.makeText(this@RegistroProgreso,"No Campos Vacios", Toast.LENGTH_SHORT).show()
                }else{
                    try {
                        val p = txtPeso.text.toString()
                        val progre = Progress(0,p.toDouble(),fecha.text.toString(),metaId?.toInt())
                        val dao = db.progressDao().insertAll(progre)

                        val m = db.metaDao().findByMeta(metaId?.toInt())
                        val r = m.peso_Restante
                        val s = r?.minus(p.toDouble())

                        val meta2 = Meta(m.uid,m.peso_Actual,m.peso_Objetivo,s,m.persona_id)
                        val dao2 = db.metaDao().updateMeta(meta2)
                        val intent:Intent = Intent(this@RegistroProgreso,Progreso::class.java)
                        intent.putExtra("id",m.persona_id.toString())
                        startActivity(intent)
                        Toast.makeText(this@RegistroProgreso,"Registro completo", Toast.LENGTH_SHORT).show()

                    }catch (e:Exception){
                        Log.e("Insert Error",e.toString())
                    }
                }
            }
        })

    }
}