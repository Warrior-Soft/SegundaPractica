package com.warriorsoft.segundapractica

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.warriorsoft.segundapractica.modelos.AppDatabase
import com.warriorsoft.segundapractica.modelos.Progress

class Progreso : AppCompatActivity() {
    lateinit var txtoriginal:TextView
    lateinit var txtobjetivo:TextView
    lateinit var txtrestante:TextView
    lateinit var totalPeso : TextView
    lateinit var totalSemana : TextView
    lateinit var totalMes : TextView

    lateinit var progressList : RecyclerView
    lateinit var db: AppDatabase
    lateinit var Add : FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val idIntent: Intent = intent
        var iduser = idIntent.getStringExtra("id")
        setContentView(R.layout.activity_progreso)
        db = Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java, "Dieta"
        ).allowMainThreadQueries().build()
        val meta = db.metaDao().findByPersona(iduser?.toInt())
        progressList = findViewById(R.id.progressList)
        Add = findViewById(R.id.btn_add_progreso)
        Add.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                val intent: Intent = Intent(this@Progreso,RegistroProgreso::class.java)
                intent.putExtra("metaid",meta.uid.toString())
                startActivity(intent)
                Toast.makeText(this@Progreso,"Registro de Progreso", Toast.LENGTH_SHORT ).show()
            }
        })
        totalPeso = findViewById(R.id.txt_total)
        totalSemana = findViewById(R.id.txt_semana)
        totalMes = findViewById(R.id.txt_mes)

        txtobjetivo = findViewById(R.id.txt_objetivo)
        txtoriginal = findViewById(R.id.txt_original)
        txtrestante = findViewById(R.id.txt_restante)
        if (meta == null){
            Toast.makeText(this@Progreso,"No tiene Metas propuestas", Toast.LENGTH_SHORT ).show()
        }else{
            txtoriginal.text = "${meta.peso_Actual} lb"
            txtobjetivo.text = "${meta.peso_Objetivo} lb"
            txtrestante.text = "${meta.peso_Restante} lb"

            val dao = db.progressDao().loadAllByIds(meta.uid)

            val dao2 = db.progressDao().findLastProgress(meta.uid)
            val dao3 = db.progressDao().findMonthProgress(meta.uid)

            if (dao != null && dao2 != null && dao3 != null){
                var totalP = 0.0;
                var totalPM = 0.0;

                for (i in dao3){
                    totalPM += i.progreso
                }

                for (p in dao){
                    totalP += p.progreso

                }
                totalPeso.text = "$totalP"
                totalSemana.text = "${dao2.progreso}"
                totalMes.text = "$totalPM"
            }




            if(dao == null){
                Toast.makeText(this,"No tienes Progresos", Toast.LENGTH_SHORT).show()
            }else{
                progressList.layoutManager = LinearLayoutManager(this);
                progressList.adapter = ProgressListAdapter(dao)
            }

        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.opt_abordaje ->{
                val inten:Intent = Intent(this@Progreso,Abordaje::class.java)
                val nameIntent: Intent = intent
                var firstName = nameIntent.getStringExtra("first_name")
                val idIntent: Intent = intent
                var iduser = idIntent.getStringExtra("id")

                inten.putExtra("name",firstName)
                inten.putExtra("iduser",iduser)
                startActivity(inten)
                Toast.makeText(this@Progreso,"Abordaje", Toast.LENGTH_SHORT ).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }
}

class ProgressListAdapter(var progress:List<Progress>): RecyclerView.Adapter<ProgressListAdapter.ViewHolder>(){
    public class ViewHolder(iteView : View) : RecyclerView.ViewHolder(iteView){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.progress_list_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val proge = progress[position]
       holder.itemView.findViewById<TextView>(R.id.txfecha).text = proge.fecha
        holder.itemView.findViewById<TextView>(R.id.txpeso).text = proge.progreso.toString()

    }

    override fun getItemCount(): Int {
       return progress.size
    }
}