package com.example.xmlklausurasmus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.example.xmlklausurasmus.db.DatabaseHelperOpen;
import com.example.xmlklausurasmus.db.User;

import java.text.ParseException;


public class ForgotPwActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnSp;
    EditText inputUsrname, inputPwd, inputPasswordRedo;
    private String name2DB = "planlos";
    private String pw2DB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_forgot_pw);

        // TODO
        // 1. XML mit Java Objekte verknüpfen

        btnSp = findViewById(R.id.buttonSp);
        inputUsrname = findViewById(R.id.editTextUser);
        inputPwd = findViewById(R.id.editTextPwd);
        inputPasswordRedo = findViewById(R.id.editTextRedo);

        // 2. Clicklistener einbauen

        btnSp.setOnClickListener(this);

        // 3. Daten aus den Eingabefeldern in Variablen speichern



        // 4. Passwörter auf Gleichheit prüfen



        // 5. Benutzer in DB überprüfen
    }

    @Override
    public void onClick(View view) {

        String username =inputUsrname.getText().toString();
        String pw = inputPwd.getText().toString();
        String pwAgain = inputPasswordRedo.getText().toString();

        if(!username.isEmpty() && !pw.isEmpty() && !pwAgain.isEmpty()) {
            // 4. Passwörter auf Gleichheit prüfen
            if (pw.equals(pwAgain)) {
                try (DatabaseHelperOpen databaseHelperOpen = new DatabaseHelperOpen(getApplicationContext())) {
                    // 5. Benutzer in DB überprüfen
                    User user = databaseHelperOpen.getUserByUsername(username, "tbl_user");
                    if (user != null) {
                        user.setPw(pw);
                        boolean savedPW = databaseHelperOpen.updateUser(user);
                        if (savedPW) {
                            Toast.makeText(getApplicationContext(), "Passwort geändert", Toast.LENGTH_LONG).show();
                            Intent backIntent = new Intent(ForgotPwActivity.this, MainActivity.class);
                            startActivity(backIntent);
                        } else {
                            Toast.makeText(getApplicationContext(), "Fehler", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Benutzername unbekannt", Toast.LENGTH_LONG).show();
                    }
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            } else {
                Toast.makeText(getApplicationContext(), "Passwörter stimmen nicht überein", Toast.LENGTH_LONG).show();
            }
        }
        else {
            Toast.makeText(getApplicationContext(), "Alle Eingaben tätigen", Toast.LENGTH_LONG).show();
        }
    }
}











