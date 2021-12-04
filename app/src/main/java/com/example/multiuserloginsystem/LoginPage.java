package com.example.multiuserloginsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginPage extends AppCompatActivity {
    EditText emailLogin, emailPassword;
    Button submitLogin;
    TextView forgotPassword, backToRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        emailLogin = findViewById(R.id.editTextEmailLogin);
        emailPassword = findViewById(R.id.editTextPasswordLogin);
        submitLogin = findViewById(R.id.loginSubmitBtn);
        forgotPassword = findViewById(R.id.forgotPassword);
        backToRegister = findViewById(R.id.backRegisterPage);

        DBHelper db = new DBHelper(this);

        submitLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailLogin.getText().toString();
                String password = emailPassword.getText().toString();
                if (!db.checkEmailThere(email)){
                    Toast.makeText(getApplicationContext(), R.string.emailNotRegistered, Toast.LENGTH_SHORT).show();
                }
                else{
                    if (db.checkPasswordCorrect(email, password)){
                        Intent intent = new Intent(getApplicationContext(), FinalPage.class);
                        intent.putExtra("message_email",email);
                        startActivity(intent);
                        finish();
                    }
                    else{
                        Toast.makeText(getApplicationContext(), R.string.passwordWrong, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), LoginPage.class);
                        finish();
                        startActivity(intent);
                    }
                }
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ForgotPassword.class);
                startActivity(intent);
            }
        });

        backToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterPage.class);
                startActivity(intent);
            }
        });
    }
}