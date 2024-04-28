package com.example.sql_8a;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    EditText etUsername, etPassword;
    Button loginButton;
    TextView signupLabel;
    ArrayList<User> users;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if (validateUser()) {
                    //Toast.makeText(getApplicationContext(), "Login successful!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
               /* }
                else {
                    Toast.makeText(getApplicationContext(), "Invalid credentials", Toast.LENGTH_SHORT).show();
                }*/
            }
        });
        signupLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void init() {
        setContentView(R.layout.activity_login);

        loginButton = findViewById(R.id.btnLogin);
        signupLabel = findViewById(R.id.tvSignup);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
    }

    private boolean validateUser() {
        MyDatabaseHelper database = new MyDatabaseHelper(this);
        try {
            database.open();

            String username = etUsername.getText().toString();
            String password = etPassword.getText().toString();

            // Query the database for the user
            User user = database.getUser(username);

            if (user.getUsername() != null && user.getPassword().equals(password)) {
                return true; // User found and password matches
            } else {
                return false; // User not found or password doesn't match
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Error occurred
        } finally {
            database.close();
        }
    }
}
