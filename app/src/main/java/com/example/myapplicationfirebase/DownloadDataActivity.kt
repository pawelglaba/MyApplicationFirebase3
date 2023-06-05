package com.example.myapplicationfirebase

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class DownloadDataActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_download_data)

        val name: EditText = findViewById(R.id.editTextTextPersonName2)
        val age: EditText = findViewById(R.id.editTextNumber2)
        val switchVal: Switch = findViewById(R.id.switch3)

        //Stworzenie instancji bazy danych
        var db: FirebaseFirestore = FirebaseFirestore.getInstance();
        val buttonDownload: Button = findViewById(R.id.buttonDownload)

        //zmienna przechowująca ID zmienianego elementu
        var idOfChangedField = ""

        //Przycisk Download - pobieranie danych z bazy
        buttonDownload.setOnClickListener {
            db.collection("users")
                    //Wyświetlanie danych z bazy z kolekcji users, gdzie imię = Paweł
                .whereEqualTo("name", "Pawel")
                    //pobieranie danych
                .get()
                    //W zmiennej result znajduje się nasz wynik, pola name, age i switch
                    //są uzupełniane danymi z bazy
                .addOnSuccessListener { result ->
                    for (document in result) {
                        Log.d(TAG, "${document.id} => ${document.data}")
                        //pobieranie ID pobieranego elementu
                        idOfChangedField=document.id
                        name.setText(document.getData().get("name").toString())
                        age.setText(document.getData().get("age").toString())
                        if(document.getData().get("switch").toString()== "true") {
                            switchVal.setChecked(true);
                        }else{
                            switchVal.setChecked(false);
                        }
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(this, " Data not added ", Toast.LENGTH_LONG).show()
                }
        }

        //Przycisk update - aktualizowanie danych
        val button: Button = findViewById(R.id.buttonUpdate)
        button.setOnClickListener {
            //po wprowadzeniu zmian przez użytkownika i kliknięciu przycisku Update
            //program pobiera wartości z pól i zapisuje je do bazy
            val name: EditText = findViewById(R.id.editTextTextPersonName2)
            val age: EditText = findViewById(R.id.editTextNumber2)
            val switchValue1: Switch = findViewById(R.id.switch3)
            //zapisywanie wartości do bazy - należy podać konkretne ID zmienianego elementu
            db.collection("users").document(idOfChangedField)
                .update(
                    mapOf(
                        "name" to name.text.toString(),
                        "age" to age.text.toString(),
                        "switch" to switchValue1.isChecked.toString()
                    )
                )
            .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully updated!")
                Toast.makeText(this,"Data updated ",Toast.LENGTH_LONG).show()
            }
            .addOnFailureListener { e -> Log.w(TAG, "Error updating document", e)
                Toast.makeText(this," Data not updated ",Toast.LENGTH_LONG).show()}

        }

        val buttonBack: Button = findViewById(R.id.buttonBack)
        buttonBack.setOnClickListener {
            val intent = Intent(this@DownloadDataActivity, MainActivity::class.java)
            startActivity(intent)
        }
    }
}