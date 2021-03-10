package com.warriorsoft.segundapractica

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.room.Room
import com.warriorsoft.segundapractica.modelos.AppDatabase
import com.warriorsoft.segundapractica.modelos.User

class Registrar : AppCompatActivity() {
    lateinit var txtLogin : TextView
    lateinit var nombre: TextView
    lateinit var apellido: TextView
    lateinit var email: TextView
    lateinit var password: TextView
    lateinit var btn_register: Button
    lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar)
        txtLogin = findViewById(R.id.txt_iniciar_sesion)
        txtLogin.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View?) {
                val intent : Intent = Intent(this@Registrar, MainActivity::class.java)
                startActivity(intent)
                Toast.makeText(this@Registrar,"Cambio a inicio de Sesion", Toast.LENGTH_SHORT).show()

            }
        })
        nombre = findViewById(R.id.nombre_register)
        apellido = findViewById(R.id.apellido_register)
        email = findViewById(R.id.email_register)
        password = findViewById(R.id.password_register)
        btn_register = findViewById(R.id.btn_crear_cuenta)

        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "Dieta"
        ).allowMainThreadQueries().build()

        btn_register.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                if (nombre.text.toString() == "" ||apellido.text.toString() == ""
                    || email.text.toString() == "" ||password.text.toString() == ""){
                    Toast.makeText(this@Registrar,"Campos no vacios", Toast.LENGTH_SHORT).show()
                }else{
                    val user = User(0,nombre.text.toString(),apellido.text.toString(),
                        email.text.toString(),password.text.toString())
                    var dao = db.userDao().insertAll(user)
                    Toast.makeText(this@Registrar,"Registro Completadp", Toast.LENGTH_SHORT).show()
                }
            }
        })


    }
}