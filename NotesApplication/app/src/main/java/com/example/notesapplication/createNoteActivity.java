package com.example.notesapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class createNoteActivity extends AppCompatActivity {

    EditText mnotetitle,mnotescontent;
    FloatingActionButton msavenotefab;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);

        mnotetitle=findViewById(R.id.notestitle);
        mnotescontent=findViewById(R.id.notescontent);
        msavenotefab=findViewById(R.id.savenotefab);
        Toolbar toolbar=findViewById(R.id.toolbarcreatenote);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home)
        {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    public void saveNote(View view) {
        String title=mnotetitle.getText().toString();
        String content=mnotescontent.getText().toString();

        if(title.isEmpty()||content.isEmpty())
        {
            Toast.makeText(this, "Title and content are required", Toast.LENGTH_SHORT).show();
        }
        else
        {
            DocumentReference documentReference=firebaseFirestore.collection("notes").document(firebaseUser.getUid()).collection("myNotes").document();
            Map<String,Object> note=new HashMap<>();
            note.put("title",title);
            note.put("content",content);
            documentReference.set(note).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(createNoteActivity.this, "note saved successfully!", Toast.LENGTH_SHORT).show();
                    finish();
                    startActivity(new Intent(createNoteActivity.this,notesActivity.class));
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(createNoteActivity.this, "note couldn't be saved successfully!", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
}