package com.example.myapplicationfirebase

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val name: EditText = findViewById(R.id.editTextTextPersonName)
        val age: EditText = findViewById(R.id.editTextNumber)
        val switchValue: Switch = findViewById(R.id.switch1)

        //Stworzenie instancji bazy danych
        var db: FirebaseFirestore=FirebaseFirestore.getInstance();

        //Przycisk Submit - dodawanie nowego wpisu do bazy danych
        val button: Button = findViewById(R.id.buttonSubmit)
        button.setOnClickListener {
            //Pobieranie danych z wypełnionych pól
            val nameVal: String = name.getText().toString()
            val ageVal = age.text.toString()
            val switchVal = switchValue.isChecked.toString()
            //Tworzenie HashMapy z danymi
            var map : HashMap<String, Any> = HashMap<String, Any> ()
            map.put("name",nameVal);
            map.put("age",ageVal);
            map.put("switch",switchVal);
            //Dodawanie danych do bazy, nazwa kolekcji jako parametr
            db.collection("users")
                .add(map)
                .addOnSuccessListener {
                    Toast.makeText(this,"Data added ",Toast.LENGTH_LONG).show()
                }
                .addOnFailureListener {
                    Toast.makeText(this," Data not added ",Toast.LENGTH_LONG).show()
                }

            val intent = Intent(this@MainActivity, DownloadDataActivity::class.java)
            startActivity(intent)

        }


    }



}