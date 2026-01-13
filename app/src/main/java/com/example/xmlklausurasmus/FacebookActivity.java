package com.example.xmlklausurasmus;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.xmlklausurasmus.db.DatabaseHelperOpen;
import com.example.xmlklausurasmus.db.User;

import java.text.ParseException;

public class FacebookActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TABLE_FBUSER = "tbl_facebook";
    // 1. Javaelemente anlegen
    EditText inputUsername, inputPassword;
    Button anmeldeBTN;
    private String name1DB = "meFB";
    private String pw1DB = "1234FB";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook);

        // 2. Objekte mit dem XML verknüpfen
        inputUsername = findViewById(R.id.editTextUsername);
        inputPassword = findViewById(R.id.editTextPassword);
        anmeldeBTN = findViewById(R.id.fb_login_btn);
        anmeldeBTN.setOnClickListener(this);

        // Werte herausholen (Siehe Mainactivity Zeile 71 und 72)
        if(getIntent() != null) {
            String username = getIntent().getStringExtra("username");
            String pw = getIntent().getStringExtra("pw");

            Log.i("XXX", username);
            Log.i("XXX", pw);
        }
    }

    @Override
    public void onClick(View view) {

        String username = inputUsername.getText().toString();
        String pw = inputPassword.getText().toString();

        try (DatabaseHelperOpen dbHelperOpen = new DatabaseHelperOpen(getApplicationContext())) {
            dbHelperOpen.createDataBase();
            User foundedUser = dbHelperOpen.getUserByUsername(username, TABLE_FBUSER); // Konstante IMMER groß geschrieben

            // TODO überprüfen des gefundenen Userobjekts
            // Wenn foundedUser == null, username nicht registriert
            if(foundedUser != null) { // zuerst prüfen dass Wert nicht null (nichts) ist
                if (foundedUser.getUsername().equals(username) && foundedUser.getPw().equals(pw)) {
                    Intent mainIntent = new Intent(FacebookActivity.this, MainMenuActivity.class); //Ausgang.this, Ziel.class
                    // Werte dem Intent mitgeben
                    mainIntent.putExtra("username", username); //key-value-paar
                    mainIntent.putExtra("pw", pw);
                    mainIntent.putExtra("from", "fb"); // key-value-paar unique als eigenes Objekt in Activity
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

            // Werte dem Intent mitgeben


        } catch (ParseException e) {
            throw new RuntimeException(e);
            /*
             * try Block: versuchen
             * was im Block steht wird ausgeführt > Fehler könnte auftreten
             * theoretisch alle möglichen Fehler, z.B. xyz-Exception
             * daher im CATCH Block: auffangen (Art der Exception)
             * hier Oberklasse der Exceptions (ParseException??), würde theoretisch alles fangen?
             * was in Klammern steht = nur die Exceptoion die gefangen wird
             *
             * Spezialisierung (von oben nach unten lesen[Vererbung])
             * & Generalisierung(von unten nach oben lesen[Oberklasse])
             *
             * Multiple catch > mehrere catch nacheinander
             *
             * Finally
             * erst mit catch alles auffangen
             * mit finally dann ausführen z.B. um Verbindungen zu schließen
             * alles was geöffnet wurde sollte auch wieder geschlossen werden
             * */
        }
    }

}