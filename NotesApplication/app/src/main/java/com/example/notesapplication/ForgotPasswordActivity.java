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
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {

    private TextView mreturntologin;
    private EditText mforgotpasswordemail;
    private RelativeLayout mforgotpasswordButton;
    private String mail;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        mreturntologin=findViewById(R.id.returntologin);
        mforgotpasswordemail=findViewById(R.id.forgotpasswordemail);
        mforgotpasswordButton=findViewById(R.id.forgotpasswordButton);

        firebaseAuth=FirebaseAuth.getInstance();

        getSupportActionBar().hide();

    }

    public void passwordRecoveryButtonClicked(View view) {
        mail=mforgotpasswordemail.getText().toString().trim();
        if(mail.isEmpty())
        {
            Toast.makeText(getApplicationContext(), "type your email in the above box", Toast.LENGTH_SHORT).show();
        }
        else
        {
            //password recovery code
            firebaseAuth.sendPasswordResetEmail(mail).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful())
                    {
                        Toast.makeText(ForgotPasswordActivity.this, "Mail sent,you can recover your password", Toast.LENGTH_SHORT).show();
                        finish();
                        startActivity(new Intent(ForgotPasswordActivity.this,MainActivity.class));
                    }
                    else
                    {
                        Toast.makeText(ForgotPasswordActivity.this, "The given e-mail is incorrect or account doesn't exist", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }
//when login button is clicked
    public void goBackToLogin(View view) {
        Intent intent=new Intent(ForgotPasswordActivity.this,MainActivity.class);
        startActivity(intent);
    }
}