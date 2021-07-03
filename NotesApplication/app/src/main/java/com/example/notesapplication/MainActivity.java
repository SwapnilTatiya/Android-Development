package com.example.notesapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private EditText mloginemail, mloginpassword;
    private RelativeLayout mloginButton, msignupButton;
    private TextView mforgotpassword;
    private String mail, password;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    ProgressBar mloginprogressindicator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mloginemail = findViewById(R.id.loginemail);
        mloginpassword = findViewById(R.id.loginpassword);
        mloginButton = findViewById(R.id.loginButton);
        msignupButton = findViewById(R.id.signupButton);
        mforgotpassword = findViewById(R.id.forgotpassword);
        mloginprogressindicator=findViewById(R.id.loginprogressindicator);


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser!=null)
        {
            finish();
            startActivity(new Intent(MainActivity.this,notesActivity.class));
        }

    }


    public void loginButtonClicked(View view) {
        mail = mloginemail.getText().toString().trim();
        password = mloginpassword.getText().toString().trim();
        if (mail.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
        } else {
            mloginprogressindicator.setVisibility(View.VISIBLE);
            //check if email and password match and then proceed
            firebaseAuth.signInWithEmailAndPassword(mail, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        IsEmailVerified();
                        mloginprogressindicator.setVisibility(View.INVISIBLE);
                    }
                    else
                    {
                        mloginprogressindicator.setVisibility(View.INVISIBLE);
                        Toast.makeText(MainActivity.this, "Password and usernaame don't match or account doesn't exist", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    public void forgotPasswordClicked(View view) {
        Intent intent = new Intent(MainActivity.this, ForgotPasswordActivity.class);
        startActivity(intent);
    }

    public void signUpButtonClicked(View view) {
        Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
        startActivity(intent);

    }

    public void IsEmailVerified() {
        firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null) {
            if (firebaseUser.isEmailVerified()) {
                Toast.makeText(MainActivity.this, "Successfully Logged in", Toast.LENGTH_SHORT).show();
                mloginprogressindicator.setVisibility(View.INVISIBLE);
                finish();
                startActivity(new Intent(MainActivity.this, notesActivity.class));
            } else {
                mloginprogressindicator.setVisibility(View.INVISIBLE);
                Toast.makeText(this, "Verify your email first", Toast.LENGTH_SHORT).show();
                firebaseAuth.signOut();
            }
        }
        else
            {
                mloginprogressindicator.setVisibility(View.INVISIBLE);
               Toast.makeText(this, "Account not yet created", Toast.LENGTH_SHORT).show();
        }
    }
}