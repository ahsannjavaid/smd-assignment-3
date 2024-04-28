package com.example.sql_8a;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity
{

    FloatingActionButton fabAdd;
    RecyclerView rvUsers;
    LinearLayoutManager manager;
    UserAdapter adapter;
    ArrayList<User> users;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        init();

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, AddPassword.class));
            }
        });

    }
    private void init()
    {
        fabAdd = findViewById(R.id.fabAdd);
        rvUsers = findViewById(R.id.rvUsers);
        rvUsers.setHasFixedSize(true);
        manager = new LinearLayoutManager(this);
        rvUsers.setLayoutManager(manager);

        MyDatabaseHelper database = new MyDatabaseHelper(this);
        database.open();
        users = database.readAllUsers();
        database.close();

        adapter = new UserAdapter(this, users);
        rvUsers.setAdapter(adapter);
    }
}