package com.example.notesapplication;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import org.w3c.dom.Document;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class notesActivity extends AppCompatActivity {
    FloatingActionButton maddnotesfab;//The floating button with + on it which is used to create notes in the app
    FirebaseAuth firebaseAuth;//Firebase Authentication object
    RecyclerView mrecyclerview;//recycler view of this activity
    StaggeredGridLayoutManager staggeredGridLayoutManager;//Used to create staggered grid in the recycler view
    FirebaseUser firebaseUser;//firebase user
    FirebaseFirestore firebaseFirestore;//firebase cloud firestore object
    FirestoreRecyclerAdapter<firebasemodel,NoteViewHolder> noteAdapter;//to bind query with recyclerView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        //linking all id's
        maddnotesfab = findViewById(R.id.addnotesfab);
        mrecyclerview=findViewById(R.id.recyclerview);

        //actionBar changes
        getSupportActionBar().setTitle("All Notes");

        //firebase
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();//gets the signed in user
        firebaseFirestore=FirebaseFirestore.getInstance();

        //recyclerview properties
        mrecyclerview.setHasFixedSize(true);//so that any change in adapter doesn't affect the recyclerview

        //staggeredGridLayoutManager with 2 columns and Vertical
        staggeredGridLayoutManager=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);

        //providing recycler view a layout manager viz staggered grid layout manager
        mrecyclerview.setLayoutManager(staggeredGridLayoutManager);

        //getting information from our firebase and arranging all notes in the order of their titles and in ascending order
        Query query =firebaseFirestore
                .collection("notes")
                .document(firebaseUser.getUid())
                .collection("myNotes")
                .orderBy("title",Query.Direction.ASCENDING);

        //This gets all the data of titles and content from all the notes from the user
        FirestoreRecyclerOptions<firebasemodel> allusernotes=new FirestoreRecyclerOptions
                                                .Builder<firebasemodel>()
                                                .setQuery(query,firebasemodel.class)
                                                .build();

        noteAdapter = new FirestoreRecyclerAdapter<firebasemodel, NoteViewHolder>(allusernotes) {
            @Override
            protected void onBindViewHolder(@NonNull NoteViewHolder holder, int position, @NonNull firebasemodel model) {
                holder.notetitle.setText(model.getTitle());
                holder.notecontent.setText(model.getContent());

                String DocId=noteAdapter.getSnapshots().getSnapshot(position).getId();
                int[]thisNoteColour=randomColour();
                if(randomColour()!=null) {
                    holder.notetitle.setBackgroundColor(getResources().getColor(thisNoteColour[0]));
                    holder.mmenupopup.setBackgroundColor(getResources().getColor(thisNoteColour[0]));
                    holder.mnote.setBackgroundColor(getResources().getColor(thisNoteColour[1]));
                }
                holder.mmenupopup.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onClick(View v) {
                        PopupMenu popupMenu=new PopupMenu(v.getContext(),v);
                            popupMenu.setGravity(Gravity.END);

                            popupMenu.getMenu().add("delete").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                                @Override
                                public boolean onMenuItemClick(MenuItem item) {


                                    //write deletion code here

                                    DocumentReference documentReference=firebaseFirestore.collection("notes").document(firebaseUser.getUid()).collection("myNotes").document(DocId);
                                    documentReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(notesActivity.this, model.getTitle()+" note deleted successfully", Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(notesActivity.this, model.getTitle()+" note couldn't be deleted", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                    return false;
                                }
                            });
                        popupMenu.getMenu().add("Details").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                Intent intent=new Intent(v.getContext(),DetailsNoteActivity.class);
                                intent.putExtra("title",model.getTitle());
                                intent.putExtra("content",model.getContent());
                                intent.putExtra("Id",DocId);
                                startActivity(intent);
                                return false;
                            }
                        });
                            popupMenu.show();
                        }
                });
                holder.mnote.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(notesActivity.this,editNoteActivity.class);
                        intent.putExtra("title",model.getTitle());
                        intent.putExtra("content",model.getContent());
                        intent.putExtra("Id",DocId);
                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.notes_layout,parent,false);
                return new NoteViewHolder(view);
            }
        };
        //adapter binding the recycler view,title,content and notesLayout
        mrecyclerview.setAdapter(noteAdapter);
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder
    {
        private TextView notetitle;
        private TextView notecontent;
        LinearLayout mnote;
        LinearLayout mtitlebar;
        ImageView mmenupopup;
        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            notetitle=itemView.findViewById(R.id.notetitle);
            notecontent=itemView.findViewById(R.id.notecontent);
            mnote=itemView.findViewById(R.id.note);
            mtitlebar=itemView.findViewById(R.id.titlebar);
            mmenupopup=itemView.findViewById(R.id.menupopup);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                firebaseAuth.signOut();
                finish();
                startActivity(new Intent(notesActivity.this, MainActivity.class));

        }
        return super.onOptionsItemSelected(item);
    }

    public void addNoteButtonClicked(View view) {
        startActivity(new Intent(notesActivity.this,createNoteActivity.class));

    }

    @Override
    protected void onStart() {
        noteAdapter.startListening();
        super.onStart();
    }

    @Override
    protected void onStop() {
        if(noteAdapter!=null) {
            noteAdapter.stopListening();
        }
        super.onStop();
    }

    public int[] randomColour()
    {
        int red[]={R.color.neonRed,R.color.neonOrange};
        int yellow[]={R.color.neonYellowDark,R.color.neonYellow2Light};
        int purple[]={R.color.neonPurpleDark,R.color.neonPurpleLight};
        int blue[]={R.color.neonblueDark,R.color.neonblueLight};
        int pink[]={R.color.neonPinkDark,R.color.neonPinkLight};
        Map<Integer,int[]>colours=new HashMap<>();
        colours.put(0,red);
        colours.put(1,yellow);
        colours.put(2,purple);
        colours.put(3,blue);
        colours.put(4,pink);
        Random random=new Random();
        int randomColor=random.nextInt(colours.size());
        return colours.get(randomColor);
    }
}