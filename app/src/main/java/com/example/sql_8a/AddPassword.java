package com.example.sql_8a;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddPassword extends AppCompatActivity {

    EditText etUsername, etPassword;
    Button btnAdd, btnCancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_password);
        etUsername= findViewById(R.id.etUsername);
        etPassword= findViewById(R.id.etPassword);
        btnAdd= findViewById(R.id.btnAdd);
        btnCancel= findViewById(R.id.btnCancel);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPassword();

                startActivity(new Intent(AddPassword.this, HomeActivity.class));
                finish();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddPassword.this, HomeActivity.class));
                finish();
            }
        });

    }

    private void addPassword()
    {
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString();

        MyDatabaseHelper myDatabaseHelper = new MyDatabaseHelper(this);
        myDatabaseHelper.open();

        myDatabaseHelper.addUser(username, password);

        myDatabaseHelper.close();
    }
}