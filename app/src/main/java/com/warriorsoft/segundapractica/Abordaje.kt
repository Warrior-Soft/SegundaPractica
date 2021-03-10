package com.warriorsoft.segundapractica

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.room.Room
import androidx.room.migration.Migration
import com.warriorsoft.segundapractica.modelos.AppDatabase
import com.warriorsoft.segundapractica.modelos.Meta
import org.w3c.dom.Text

class Abordaje : AppCompatActivity() {
    lateinit var saludo: TextView
    lateinit var pactual: EditText
    lateinit var pperder: EditText
    lateinit var btncontinuar :Button
    lateinit var db : AppDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_abordaje)
        saludo = findViewById(R.id.txt_saludo)
        pactual = findViewById(R.id.editPesoActual)
        pperder = findViewById(R.id.editPesoPerder)
        btncontinuar = findViewById(R.id.btn_continuar)
        val objetoIntent: Intent = intent
        var firstName = objetoIntent.getStringExtra("name")
        var iduser = objetoIntent.getStringExtra("iduser")
        saludo.text = "Hola, $firstName"

        db = Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java, "Dieta"
        ).allowMainThreadQueries().build()

        btncontinuar.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                if(pactual.text.toString() == "" || pperder.text.toString() ==""){
                    Toast.makeText(this@Abordaje,"Campos no vacios", Toast.LENGTH_SHORT).show()
                }else{
                    var pesoActual =  pactual.text.toString()
                    var pesoPerder = pperder.text.toString()
                    var restante: Double = pesoActual.toDouble()-pesoPerder.toDouble()
                    val meta = Meta(0,pesoActual.toDouble(),pesoPerder.toDouble(),restante,iduser?.toInt())
                    var dao = db.metaDao().insertAll(meta)
                    Toast.makeText(this@Abordaje,"Meta Creada", Toast.LENGTH_SHORT).show()
                    val intent:Intent = Intent(this@Abordaje,Progreso::class.java)
                    intent.putExtra("id",meta.persona_id.toString())
                    startActivity(intent)
                }
            }
        })

    }
}