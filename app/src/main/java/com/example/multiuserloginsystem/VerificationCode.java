package com.example.multiuserloginsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class VerificationCode extends AppCompatActivity {
    EditText verifyCodeEditText;
    Button verifyBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification_code);

        verifyCodeEditText = findViewById(R.id.verifyCodeEditText);
        verifyBtn = findViewById(R.id.verifyBtn);

        DBHelper db = new DBHelper(this);

        String name = null, id = null, password = null, email, type = null;
        Intent intent = getIntent();
        email = intent.getStringExtra("message_email");
        int rand = intent.getIntExtra("message_random_value", -1);
        int x = intent.getIntExtra("message_key",-1);
        if (x==1){
            name = intent.getStringExtra("message_name");
            id = intent.getStringExtra("message_id");
            password = intent.getStringExtra("message_password");
            type = intent.getStringExtra("message_type");
        }


        String finalName = name;
        String finalId = id;
        String finalPassword = password;
        String finalType = type;
        verifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (verifyCodeEditText.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), R.string.fieldNotEmpty, Toast.LENGTH_SHORT).show();
                }
                else{
                    int enteredCode = Integer.parseInt(verifyCodeEditText.getText().toString());
                    if (rand!=enteredCode){
                        Toast.makeText(getApplicationContext(), R.string.codeWrong, Toast.LENGTH_LONG).show();
                    }
                    else{
                        if (x==1){
                            boolean inserted = db.insertData(finalName, finalId,email, finalPassword, finalType);
                            if (inserted){
                                Intent intent1 = new Intent(getApplicationContext(), FinalPage.class);
                                intent1.putExtra("message_email", email);
                                startActivity(intent1);
                            }
                            else{
                                Toast.makeText(getApplicationContext(), R.string.registrationFailed, Toast.LENGTH_SHORT).show();
                                Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent1);
                            }
                            finish();
                        }
                        else if (x==0){
                            Intent intent1 = new Intent(getApplicationContext(), ChangePassword.class);
                            intent1.putExtra("message_email",email);
                            startActivity(intent1);
                            finish();
                        }
                    }
                }


            }
        });
    }


}