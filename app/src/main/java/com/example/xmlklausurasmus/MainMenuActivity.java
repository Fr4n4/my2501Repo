package com.example.xmlklausurasmus;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

public class MainMenuActivity extends AppCompatActivity {

    private TextView userTV;
    private ImageView iconIV;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_menu);

        userTV = findViewById(R.id.usernameTV);
        iconIV = findViewById(R.id.imageviewIV);

        // Werte herausholen (Siehe Mainactivity Zeile 71 und 72)
        if(getIntent() != null) {
            String username = getIntent().getStringExtra("username");
            String from = getIntent().getStringExtra("from");

            userTV.setText(username);

            // if(from.equals("main")){ // kommt von MAIN
            //hat kein bild also weglassen...
            if (from != null && from.equals("fb")){ // kommt von Facebook
                iconIV.setImageDrawable(AppCompatResources.getDrawable(getApplicationContext(), R.drawable.fb)); // kein Theme übergeben
            }
            else if (from != null &&from.equals("tw")){ // kommt von Twitter
                iconIV.setImageDrawable(AppCompatResources.getDrawable(getApplicationContext(), R.drawable.tw));
            }
        }
    }

    
}