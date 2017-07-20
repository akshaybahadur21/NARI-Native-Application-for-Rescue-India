package com.example.sarthak_tyagi.nari;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Register extends AppCompatActivity {
    Button useRegister;
    Button subRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        useRegister = (Button)findViewById(R.id.button3);
        subRegister = (Button)findViewById(R.id.button4);
        useRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Register.this,loginPage.class);
                Register.this.startActivity(myIntent);
            }
        });

        // intended for subscriber registration and obtaining the token from the device

        subRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent= new Intent(Register.this,SubscriberRegistration.class);
                Register.this.startActivity(myIntent);
            }
        });

        checkLogin();
    }

    private void logout()
    {
        SharedPreferences sharedPreferences = getSharedPreferences("prefs", 1);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("login", false);
        editor.commit();
    }
    private void checkLogin() {
        SharedPreferences sharedPreferences = getSharedPreferences("prefs", 1);
        if(sharedPreferences.getBoolean("login", false))
        {
            Intent myIntent = new Intent(Register.this,PanicButton.class);
            Register.this.startActivity(myIntent);
        }
    }
}
