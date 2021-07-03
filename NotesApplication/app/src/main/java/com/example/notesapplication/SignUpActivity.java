package com.example.notesapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class SignUpActivity extends AppCompatActivity {

    private EditText msignupemail,msignuppassword;
    private RelativeLayout msignupButton;
    private TextView mreturntologin;
    private String mail;
    private String password;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        msignupButton=findViewById(R.id.signupButton);
        msignupemail=findViewById(R.id.signupemail);
        msignuppassword=findViewById(R.id.signuppassword);
        mreturntologin=findViewById(R.id.returntologin);

        mail=msignupemail.getText().toString().trim();
        password=msignuppassword.getText().toString().trim();

        firebaseAuth=FirebaseAuth.getInstance();

    }

    public void goBackToLogin(View view) {
        Intent intent=new Intent(SignUpActivity.this,MainActivity.class);
        startActivity(intent);
    }

    public void signUpButtonClicked(View view) {
        mail=msignupemail.getText().toString().trim();
        password=msignuppassword.getText().toString().trim();
        if(mail.isEmpty()||password.isEmpty())
        {
            Toast.makeText(this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
        }
        else
        {
            if(password.length()<7)
            {
                Toast.makeText(this, "Please enter a password of length more than 7", Toast.LENGTH_SHORT).show();
            }
            else
            {
                firebaseAuth.createUserWithEmailAndPassword(mail,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(SignUpActivity.this, "Registration successful!", Toast.LENGTH_SHORT).show();
                            sendVerificationEmail();
                        }
                        else
                        {
                            Toast.makeText(SignUpActivity.this, "Failed to register", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
            //verification of email and password and then loading user to firebase
        }
    }

    private void sendVerificationEmail() {
        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
        if(firebaseUser!=null)
        {
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(SignUpActivity.this, "Verification Email is sent,Verify and login again", Toast.LENGTH_SHORT).show();
                    firebaseAuth.signOut();
                    finish();
                    startActivity(new Intent(SignUpActivity.this,MainActivity.class));
                }
            });
        }
        else
        {
            Toast.makeText(SignUpActivity.this, "Failed to send Verification Mail", Toast.LENGTH_SHORT).show();
        }
    }
}