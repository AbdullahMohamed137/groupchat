package com.example.chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class chat extends AppCompatActivity {
    FirebaseAuth mAuth ;
    DatabaseReference myRef;
    ArrayList<message> reasla = new ArrayList<>();
    String getmess;
    String l;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Button out = findViewById(R.id.sign_out);
        out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                out();
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("chat");

        final EditText type = findViewById(R.id.write_message);
        ImageButton send = findViewById(R.id.send);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getmess = type.getText().toString();
                if (getmess.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Empty", Toast.LENGTH_LONG).show();
                }
                else {
                    String id = myRef.push().getKey();
                    message ms = new message(id,getmess,CurrentDate());
                    myRef.child(id).setValue(ms);

                }
            }
        });
        final ListView list = findViewById(R.id.list);
        final AlertDialog.Builder deletedialog = new AlertDialog.Builder(this);
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                View viewe = getLayoutInflater().inflate(R.layout.delete,null);
                deletedialog.setView(viewe);
                final AlertDialog Builde = deletedialog.create();
                Builde.show();

                final Button dele = viewe.findViewById(R.id.sure_delete);
                final Button cancle = viewe.findViewById(R.id.cancle);

                dele.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myRef.child(reasla.get(position).getId()).removeValue();
                        Builde.dismiss();
                    }
                });

                cancle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Builde.dismiss();
                    }
                });
                return true;
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        final ListView list = findViewById(R.id.list);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> x = dataSnapshot.getChildren();
                reasla.clear();
                for (DataSnapshot i:x) {
                    message y = i.getValue(message.class);
                    reasla.add(y);
                }
                BaseAdapter baseAdapter = new BaseAdapter(getApplicationContext(),reasla);
                list.setAdapter(baseAdapter);



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    public String CurrentDate() {
        Date calendar = Calendar.getInstance().getTime();
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        return s.format(calendar);
    }





    public void out(){
        mAuth.getInstance().signOut();
    }
}
