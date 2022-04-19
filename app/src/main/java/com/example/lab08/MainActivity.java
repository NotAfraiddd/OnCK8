package com.example.lab08;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button btnSign, btnRegister;
    public static final String MODE = "mode";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_chinh);

        btnSign = findViewById(R.id.btn_sign_in);
        btnRegister = findViewById(R.id.btn_register);

        Intent intent =new Intent(MainActivity.this,Login_Register.class);

        btnSign.setOnClickListener(view -> {
            intent.putExtra(MODE,1);
            startActivity(intent);
        });

        btnRegister.setOnClickListener(view -> {
            intent.putExtra(MODE,2);
            startActivity(intent);
        });
    }

}