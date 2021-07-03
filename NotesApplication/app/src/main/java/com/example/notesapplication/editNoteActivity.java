package com.example.notesapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class editNoteActivity extends AppCompatActivity {
    String title,content,DocId;
    EditText meditnotestitle,meditnotescontent;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);


        meditnotescontent=findViewById(R.id.editnotescontent);
        meditnotestitle=findViewById(R.id.editnotestitle);
        Intent intent=getIntent();
        title=intent.getStringExtra("title");
        content=intent.getStringExtra("content");
        DocId=intent.getStringExtra("Id");

        meditnotestitle.setText(title);
        meditnotescontent.setText(content);

        Toolbar toolbar=findViewById(R.id.toolbareditnote);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
        firebaseFirestore=FirebaseFirestore.getInstance();

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

        String newtitle=meditnotestitle.getText().toString();
        String newcontent=meditnotescontent.getText().toString();

        if(newtitle.isEmpty()||newcontent.isEmpty())
        {
            Toast.makeText(this, "Title and content are required", Toast.LENGTH_SHORT).show();
        }
        else
        {
            DocumentReference documentReference=firebaseFirestore.collection("notes").document(firebaseUser.getUid()).collection("myNotes").document(DocId);
            Map<String,Object> note=new HashMap<>();
            note.put("title",newtitle);
            note.put("content",newcontent);
            documentReference.set(note).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(editNoteActivity.this, "note saved successfully!", Toast.LENGTH_SHORT).show();
                    finish();
                    startActivity(new Intent(editNoteActivity.this,notesActivity.class));
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(editNoteActivity.this, "note couldn't be saved successfully!", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

}