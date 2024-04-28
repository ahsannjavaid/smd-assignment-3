package com.example.sql_8a;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText etUsername, etPassword;
    Button signupButton;
    TextView loginLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUser();

                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        loginLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void addUser() {
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString();

        MyDatabaseHelper database = new MyDatabaseHelper(this);
        database.open();

        database.addUser(username, password);

        database.close();
    }

    private void init() {
        setContentView(R.layout.activity_main);

        signupButton = findViewById(R.id.btnSignup);
        loginLabel = findViewById(R.id.tvLogin);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
    }
}