package com.example.multiuserloginsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

public class FinalPage extends AppCompatActivity {
    TextView textView2, textView4,textView6, textView7, textView8;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_page);
        textView2 = findViewById(R.id.textView2);
        textView4 = findViewById(R.id.textView4);
        textView6 = findViewById(R.id.textView6);
        textView7 = findViewById(R.id.textView7);
        textView8 = findViewById(R.id.textView8);

        Intent intent = getIntent();
        String email = intent.getStringExtra("message_email");
        DBHelper db = new DBHelper(this);
        Cursor res = db.getDeatils(email);
        res.moveToNext();
        String type = res.getString(4);
        String name = res.getString(2);
        String id = res.getString(3);
        textView4.setText(name);
        textView6.setText(email);
        textView8.setText(id);
        switch (type) {
            case "student":
                textView2.setText(R.string.studentWelcome);
                textView7.setText(R.string.rollno);
                break;
            case "teacher":
                textView2.setText(R.string.teacherWelcome);
                textView7.setText(R.string.staffCode);
                break;
            case "person":
                textView2.setText(R.string.personWelcome);
                textView7.setText(R.string.phoneNo);
                break;
        }
    }
}