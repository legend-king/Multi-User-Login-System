package com.example.multiuserloginsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ChangePassword extends AppCompatActivity {
    EditText newPassword, reNewPassword;
    Button change;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        newPassword = findViewById(R.id.newPassword);
        reNewPassword = findViewById(R.id.reenterNewPassword);
        change = findViewById(R.id.changePasswordBtn);

        Intent intent = getIntent();
        String email = intent.getStringExtra("message_email");
        DBHelper db = new DBHelper(this);

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newPass = newPassword.getText().toString();
                String reNewPass = reNewPassword.getText().toString();
                if (newPass.equals("") || reNewPass.equals("") || newPass.length()<6 ||
                        reNewPass.length()<6){
                    Toast.makeText(getApplicationContext(), R.string.passwordCharacters, Toast.LENGTH_SHORT).show();
                }
                else if (!newPass.equals(reNewPass)){
                    Toast.makeText(getApplicationContext(), R.string.passwordMisMatch, Toast.LENGTH_SHORT).show();
                }
                else{
                    boolean x = db.updateData(email, newPass);
                    if (x){
                        Toast.makeText(getApplicationContext(), R.string.passwordUpdated, Toast.LENGTH_SHORT).show();
                        Intent intent1 = new Intent(getApplicationContext(), LoginPage.class);
                        finish();
                        startActivity(intent1);
                    }
                    else{
                        Toast.makeText(getApplicationContext(), R.string.unableToUpdatePassword, Toast.LENGTH_SHORT).show();
                        Intent intent1 = new Intent(getApplicationContext(), ForgotPassword.class);
                        finish();
                        startActivity(intent1);
                    }
                }
            }
        });
    }
}