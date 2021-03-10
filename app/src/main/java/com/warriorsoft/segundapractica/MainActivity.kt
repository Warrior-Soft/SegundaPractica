package com.warriorsoft.segundapractica

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

class MainActivity : AppCompatActivity() {
    lateinit var txtRegister : TextView
    lateinit var login : Button
    lateinit var email: EditText
    lateinit var password: EditText
    lateinit var db : AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        login = findViewById(R.id.btn_login)
        email = findViewById(R.id.email_login)
        password = findViewById(R.id.password_login)

        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "Dieta"
        ).allowMainThreadQueries().build()

        txtRegister = findViewById(R.id.txt_Registrer)
        txtRegister.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View?) {
                val intent : Intent = Intent(this@MainActivity, Registrar::class.java)
                startActivity(intent)
                Toast.makeText(this@MainActivity,"Cambio a Registro", Toast.LENGTH_SHORT).show()
            }
        })

        login.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
                val emailuser =  email.text.toString()
                val passworduser = password.text.toString()
                if (emailuser == "" || passworduser == ""){
                    Toast.makeText(this@MainActivity,"Campos no vacios", Toast.LENGTH_SHORT).show()
                }else{
                    try {
                        val user = db.userDao().findByName(emailuser,passworduser)
                        if (user == null){
                            Toast.makeText(this@MainActivity,"Usuario No Registrado", Toast.LENGTH_SHORT).show()
                        }else{
                            val intent:Intent = Intent(this@MainActivity,Progreso::class.java)
                            intent.putExtra("first_name",user.firstName)
                            intent.putExtra("id",user.uid.toString())
                            startActivity(intent)
                            Toast.makeText(this@MainActivity,"Progreso", Toast.LENGTH_SHORT).show()
                        }
                    } catch (e:Exception){
                        Log.e("Insert Error",e.toString())
                    }

                }

            }
        })
    }
}