package com.example.xmlklausurasmus;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.xmlklausurasmus.db.DatabaseHelperOpen;
import com.example.xmlklausurasmus.db.User;

import java.text.ParseException;

/**
 * huhu
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // 1. Java Objekte anlegen
    Button btnFB, btnTw;
    EditText inputUsername, inputPassword;
    TextView forgotPwLeft, forgotPwRight, signIn;

    /*
    commit text for github
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*
        mehr
        zeilig
         */

        // Layout festlegen
        setContentView(R.layout.activity_main);

        // 2.  XML Elemente mit Javaobjekt verknüpfen

        btnFB = findViewById(R.id.buttonFb);
        btnFB.setOnClickListener(this);
        // anonymen Clicklistener
        /*btnFB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("XXX", "Facebook Button anonym geklickt");
            }
        });*/

        btnTw = findViewById(R.id.buttonTw);
        btnTw.setOnClickListener(this);

        inputUsername = findViewById(R.id.editTextUsername);
        inputPassword = findViewById(R.id.editTextPw);

        forgotPwLeft = findViewById(R.id.forgotPwLeft);
        forgotPwLeft.setOnClickListener(this);
        forgotPwRight = findViewById(R.id.forgotPwRight);
        forgotPwRight.setOnClickListener(this);

        signIn = findViewById(R.id.signInTV);
        signIn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == btnFB.getId()) {
            // neuen View laden
            // 1. Parameter von wo aufgerufen wird
            // 2. Parameter was aufgerufen werden soll (Zielactivity)
            // Expliziter Intent

            Intent fbIntent = new Intent(MainActivity.this, FacebookActivity.class);
            // Werte dem Intent mitgeben
            fbIntent.putExtra("username", "ike");
            fbIntent.putExtra("pw", "geheim123");
            startActivity(fbIntent);

            Log.i("XXX", "Facebook Button geklickt");
        } else if (view.getId() == btnTw.getId()) {

            Intent twIntent = new Intent(MainActivity.this, TwitterActivity.class);
            // Werte dem Intent mitgeben
            twIntent.putExtra("username", "ike");
            twIntent.putExtra("pw", "geheim123");
            startActivity(twIntent);

            Log.i("XXX", "Twitter Button geklickt");
        } else if (view.getId() == forgotPwLeft.getId() || view.getId() == forgotPwRight.getId()) {
            Intent twIntent = new Intent(MainActivity.this, ForgotPwActivity.class);
            startActivity(twIntent);
            Log.i("XXX", "forgotPwLeft geklickt");
        } else if (view.getId() == signIn.getId()) {
            Log.i("XXX", "SignIn geklickt");

            // Werte aus EditTexts herauslesen und speichern
            String username = inputUsername.getText().toString();
            String pw = inputPassword.getText().toString();

            try (DatabaseHelperOpen dbHelperOpen = new DatabaseHelperOpen(getApplicationContext())) {
                dbHelperOpen.createDataBase();
                User foundedUser = dbHelperOpen.getUserByUsername(username, "tbl_user");

                // TODO überprüfen des gefundenen Userobjekts
                // Wenn foundedUser == null, username nicht registriert
                if(foundedUser != null) { // zuerst prüfen dass Wert nicht null (nichts) ist
                    if (foundedUser.getUsername().equals(username) && foundedUser.getPw().equals(pw)) {
                        Intent mainIntent = new Intent(MainActivity.this, MainActivity.class);
                        mainIntent.putExtra("username", username); //key-value-paar
                        //mainIntent.putExtra("pw", pw); Passwörter werden eigtl nicht verschickt??
                        mainIntent.putExtra("from", "main"); // key-value-paar unique als eigenes Objekt in Activity
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
                Intent fbIntent = new Intent(MainActivity.this, MainMenuActivity.class);
                // Werte dem Intent mitgeben
                fbIntent.putExtra("username", username); //key-value-paar
                fbIntent.putExtra("pw", pw);
                startActivity(fbIntent); // neue Activity starten und das ensprechende Layout anzeigen
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
}