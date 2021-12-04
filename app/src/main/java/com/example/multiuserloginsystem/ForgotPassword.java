package com.example.multiuserloginsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class ForgotPassword extends AppCompatActivity {
    EditText email;
    Button next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        email = findViewById(R.id.forgotPasswordCodeEditText);
        next = findViewById(R.id.forgotPasswordBtn);

        DBHelper DB = new DBHelper(this);

        int max = 999999;
        int min = 100000;
        int range= max - min + 1;

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String forgotPasswordEmail = email.getText().toString();
                if (forgotPasswordEmail.equals("")) {
                    Toast.makeText(getApplicationContext(), R.string.fieldNotEmpty, Toast.LENGTH_SHORT).show();
                } else {
                    if (DB.checkEmailThere(forgotPasswordEmail)) {
                        int rand = (int) (Math.random() * range) + min;
                        Intent intent = new Intent(getApplicationContext(), VerificationCode.class);
                        intent.putExtra("message_email", forgotPasswordEmail);
                        intent.putExtra("message_key", 0);
                        intent.putExtra("message_random_value", rand);
                        startActivity(intent);
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

                        Message message = prepareMessage(session, myAccountEmail, forgotPasswordEmail, rand);
                        try {
                            assert message != null;
                            Transport.send(message);
                        } catch (MessagingException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), R.string.emailNotRegistered, Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(), ForgotPassword.class);
                        finish();
                        startActivity(intent);
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