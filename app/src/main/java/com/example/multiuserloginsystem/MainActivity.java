package com.example.multiuserloginsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button student, teacher, person;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        student = findViewById(R.id.studentButton);
        teacher = findViewById(R.id.teacherButton);
        person = findViewById(R.id.personButton);
//
//        DBHelper db = new DBHelper(this);
//        boolean x = db.deleteData("mustafakhorakiwala6@gmail.com");

        student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterPage.class);
                intent.putExtra("message_key", 0);
                startActivity(intent);
            }
        });

        teacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterPage.class);
                intent.putExtra("message_key", 1);
                startActivity(intent);
            }
        });

        person.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterPage.class);
                intent.putExtra("message_key", 2);
                startActivity(intent);
            }
        });
    }
}