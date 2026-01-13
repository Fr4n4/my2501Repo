package com.example.xmlklausurasmus;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.example.xmlklausurasmus.db.DatabaseHelperOpen;
import com.example.xmlklausurasmus.db.User;

import java.text.ParseException;


public class TwitterActivity extends AppCompatActivity implements View.OnClickListener {

    // 1. Javaelemente anlegen
    EditText inputUsername, inputPassword;
    Button anmeldeBTN;
    private static final String TABLE_TWUSER = "tbl_twitter";
    private String pwDB = "1234TW";
    private String nameDB = "meTW";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twitter);

        // 2. Objekte mit dem XML verknüpfen
        inputUsername = findViewById(R.id.editTextUsername);
        inputPassword = findViewById(R.id.editTextPassword);
        anmeldeBTN = findViewById(R.id.tw_login_btn);
        anmeldeBTN.setOnClickListener(this);

        if(getIntent() != null) { // überprüfen ob es einen Intent gibt (Paket: von wo, wohin)
            String username = getIntent().getStringExtra("username");
            String pw = getIntent().getStringExtra("pw");

            if(!username.isEmpty() && !pw.isEmpty()) { // ! bedeutet negieren
                Log.i("XXX", username);
                Log.i("XXX", pw);
            }
        }
    }

    @Override
    public void onClick(View view) {

        String username = inputUsername.getText().toString();
        String pw = inputPassword.getText().toString();

        try (DatabaseHelperOpen dbHelperOpen = new DatabaseHelperOpen(getApplicationContext())) {
            dbHelperOpen.createDataBase();
            User foundedUser = dbHelperOpen.getUserByUsername(username, TABLE_TWUSER); // Konstante IMMER groß geschrieben

            // TODO überprüfen des gefundenen Userobjekts
            // Wenn foundedUser == null, username nicht registriert
            if (foundedUser != null) { // zuerst prüfen dass Wert nicht null (nichts) ist
                if (foundedUser.getUsername().equals(username) && foundedUser.getPw().equals(pw)) {
                    Intent mainIntent = new Intent(TwitterActivity.this, MainMenuActivity.class); //Ausgang.this, Ziel.class
                    // Werte dem Intent mitgeben
                    mainIntent.putExtra("username", username); //key-value-paar
                    mainIntent.putExtra("pw", pw);
                    mainIntent.putExtra("from", "tw"); // key-value-paar unique als eigenes Objekt in Activity
                    startActivity(mainIntent);
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Benutzername oder Passwort falsch", Toast.LENGTH_SHORT).show();
                    // getResources().getString(R.string.benutzername_oder_passwort_falsch) Toast.LENGTH_SHORT).show();
                    // gibt String aus... Farbe = color + Name Farbe
                }
            } else {
                Toast.makeText(getApplicationContext(),
                        getResources().getString(R.string.sie_sind_noch_nicht_registriert), Toast.LENGTH_SHORT).show();
            }


        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}