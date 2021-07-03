package com.example.notesapplication;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class DetailsNoteActivity extends AppCompatActivity {
    FloatingActionButton meditnotefab;
    TextView mdetailsnotestitle,mdetailsnotescontent;
    String DocId,title,content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_note);

        meditnotefab=findViewById(R.id.editnotefab);
        mdetailsnotescontent=findViewById(R.id.detailsnotescontent);
        mdetailsnotestitle=findViewById(R.id.detailsnotestitle);
        //toolbar enabled
        Toolbar toolbar=findViewById(R.id.toolbardetailsnote);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent=getIntent();
        title=intent.getStringExtra("title");
        content=intent.getStringExtra("content");
        DocId=intent.getStringExtra("Id");
        mdetailsnotestitle.setText(title);
        mdetailsnotescontent.setText(content);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home)
        {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    public void editNote(View view)
    {

        Intent intent=new Intent(DetailsNoteActivity.this,editNoteActivity.class);
        intent.putExtra("title",title);
        intent.putExtra("content",content);
        intent.putExtra("Id",DocId);
        finish();
        startActivity(intent);
    }
}