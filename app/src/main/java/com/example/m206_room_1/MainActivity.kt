package com.example.m206_room_1

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        fetchData()

        val ed_nom = findViewById<EditText>(R.id.editTextNom)
        val ed_prix = findViewById<EditText>(R.id.editTextPrix)
        val ed_image = findViewById<EditText>(R.id.editTextImage)
        val btn_add = findViewById<Button>(R.id.buttonAdd)
        val listViewSmartphones = findViewById<ListView>(R.id.lv)

        btn_add.setOnClickListener{
            val nom = ed_nom.text.toString()
            val prix = ed_prix.text.toString().toDoubleOrNull()
            val image = ed_image.text.toString()


            if (nom.isNotBlank() && prix != null && image.isNotBlank()) {


                val smartphone = Smartphone(nom = nom, prix = prix, image = image)
                SmartphoneDatabase.getDatabase(applicationContext).smartphoneDao()
                            .insertSmartphone(smartphone)



                Toast.makeText(this, "Smartphone ajouté avec succès !", Toast.LENGTH_SHORT).show()
                fetchData()
            } else {
                Toast.makeText(this, "Veuillez remplir tous les champs.", Toast.LENGTH_SHORT).show()
            }




        }

        listViewSmartphones.setOnItemClickListener { _, _, position, _ ->
            val smartphones = SmartphoneDatabase.getDatabase(applicationContext).smartphoneDao().getAllSmartphones()
            val selectedSmartphone = smartphones[position]

            val intent = Intent(this, UpdateDelete::class.java).apply {
                putExtra("id", selectedSmartphone.id)
                putExtra("name", selectedSmartphone.nom)
                putExtra("price", selectedSmartphone.prix)
                putExtra("image", selectedSmartphone.image)
            }
            startActivity(intent)
        }

    }

    private fun fetchData() {
        val listViewSmartphones = findViewById<ListView>(R.id.lv)
        val smartphonesList = mutableListOf<String>()
        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, smartphonesList)
        listViewSmartphones.adapter = arrayAdapter





                val db = SmartphoneDatabase.getDatabase(applicationContext)
                val smartphoneDao = db.smartphoneDao()
                val smartphones =  smartphoneDao.getAllSmartphones()
                smartphonesList.clear()

                for (smartphone in smartphones) {
                    smartphonesList.add("${smartphone.nom} - ${smartphone.prix} MAD")
                }


            arrayAdapter.notifyDataSetChanged()

    }

    override fun onResume() {
        super.onResume()
        fetchData()
    }

}