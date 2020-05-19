package com.example.tienditapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Splash extends AppCompatActivity {

    TextView tvCarga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        tvCarga = findViewById(R.id.tvBien);

        Thread timer = new Thread(){
            public void run(){
                try {
                    sleep(4000);
                }catch (Exception e){

                }finally {
                    Intent intent = new Intent(Splash.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        };
        timer.start();
    }
}
