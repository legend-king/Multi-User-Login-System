package com.example.multiuserloginsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class RegisterPage extends AppCompatActivity {
    int x;
    EditText name, email, password, id;
    Button registerSubmitBtn;
    TextView gotoLogin, backtoRegister, registerTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

        name = findViewById(R.id.editTextName);
        email = findViewById(R.id.editTextEmail);
        password = findViewById(R.id.editTextPassword);
        id = findViewById(R.id.editTextId);
        registerSubmitBtn = findViewById(R.id.registerSubmitBtn);
        gotoLogin = findViewById(R.id.gotoLoginPage);
        backtoRegister = findViewById(R.id.backtoRegisterPage);
        registerTextView = findViewById(R.id.registerTextView);
        DBHelper db = new DBHelper(this);

        int max = 999999;
        int min = 100000;
        int range= max - min + 1;

        Intent intent = getIntent();
        x = intent.getIntExtra("message_key",-1);

        if (x==0){
            id.setHint(getString(R.string.enterRegno));
            registerTextView.setText(R.string.studentRegistration);
        }

        else if (x==1){
            id.setHint(getString(R.string.enterStaffCode));
            registerTextView.setText(R.string.teacherRegistration);
        }

        else if (x==2){
            id.setHint(getString(R.string.enterNumber));
            registerTextView.setText(R.string.personRegistration);
        }

        backtoRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent1);
            }
        });

        gotoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(), LoginPage.class);
                startActivity(intent1);
            }
        });

        registerSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameText = name.getText().toString();
                String emailText = email.getText().toString();
                String passwordText = password.getText().toString();
                String idText = id.getText().toString();
                if (nameText.equals("") || emailText.equals("") || passwordText.equals("")
                        || idText.equals("")){
                    Toast.makeText(getApplicationContext(), R.string.allValues, Toast.LENGTH_SHORT).show();
                }
                else if (passwordText.length()<6){
                    Toast.makeText(getApplicationContext(), R.string.passwordCharacters, Toast.LENGTH_SHORT).show();
                }
                else if (db.checkEmailThere(emailText)){
                    Toast.makeText(getApplicationContext(), R.string.emailRegistered, Toast.LENGTH_SHORT).show();
                }
                else if (db.checkIdThere(idText)){
                    if (x==0){
                        Toast.makeText(getApplicationContext(), R.string.rollnoRegistered, Toast.LENGTH_SHORT).show();
                    }
                    else if (x==1){
                        Toast.makeText(getApplicationContext(), R.string.staffCodeRegistered, Toast.LENGTH_SHORT).show();
                    }
                    else if (x==2){
                        Toast.makeText(getApplicationContext(), R.string.phoneNumberRegistered, Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    int rand = (int)(Math.random() * range) + min;
                    Intent intent1 = new Intent(getApplicationContext(), VerificationCode.class);
                    intent1.putExtra("message_name",nameText);
                    intent1.putExtra("message_email",emailText);
                    intent1.putExtra("message_password",passwordText);
                    intent1.putExtra("message_id",idText);
                    intent1.putExtra("message_key",1);
                    intent1.putExtra("message_random_value", rand);
                    if (x==0){
                        intent1.putExtra("message_type", "student");
                    }
                    else if (x==1){
                        intent1.putExtra("message_type", "teacher");
                    }
                    else if (x==2){
                        intent1.putExtra("message_type", "person");
                    }
                    startActivity(intent1);
                    Properties properties = new Properties();
                    properties.put("mail.smtp.auth", "true");
                    properties.put("mail.smtp.starttls.enable", "true");
                    properties.put("mail.smtp.host", "smtp.gmail.com");
                    properties.put("mail.smtp.port", "587");

                    final String myAccountEmail = "practicetest57@gmail.com";
                    final String myAccountPassword = "Test@1234";

                    Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
                        @Override
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(myAccountEmail, myAccountPassword);
                        }
                    });

                    Message message = prepareMessage(session, myAccountEmail, emailText, rand);
                    try {
                        assert message != null;
                        Transport.send(message);
                    } catch (MessagingException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    private static Message prepareMessage(Session session, String myAccountEmail, String email, int rand){
        try{
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject("Verification code");
            message.setText("Your 6-digit verification code is " + rand);
            return message;
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
}