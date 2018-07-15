package com.example.kotkarandsons.stalkcode;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CodeListDisplay extends AppCompatActivity {

    protected RecyclerView listdisplay;
    protected List<CodeInfo> dataList;
    private CodeAdapter codeAdapter;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
    @Override

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_list_display);
        listdisplay=(RecyclerView)findViewById(R.id.codelistrecycleview);
        listdisplay.setLayoutManager(new LinearLayoutManager(this));
        dataList=new ArrayList<>();
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference(user.getUid()+"/Codes");

      //  CodeInfo code1=new CodeInfo("Monkey","Implementation","Hard","Submitted","12-2-2016");
     // dataList.add(code1);

       // CodeInfo code2=new CodeInfo("Monkey","Implementation","Hard","Submitted","12-2-2016");
      //  dataList.add(code2);


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
              for(DataSnapshot walker: dataSnapshot.getChildren())
              {
                //  CodeInfo code=walker.getValue(CodeInfo.class);
                  String name= (String) walker.child("codeName").getValue();
                  String type=(String)walker.child("codeType").getValue();
                  String level=(String)walker.child("codeLevel").getValue();
                  String status=(String)walker.child("codeStatus").getValue();
                  String date=(String)walker.child("date").getValue();
                 CodeInfo code=new CodeInfo(name,type,level,status,date);
                  dataList.add(code);
            //  Toast.makeText(CodeListDisplay.this, name+" "+type+" "+level+" "+status+" "+date, Toast.LENGTH_SHORT).show();

               /*   for(DataSnapshot codewalker:walker.getChildren()) {
                      String name,type,level,status,date;
                      name=codewalker.getKey();
                     Toast.makeText(CodeListDisplay.this, name, Toast.LENGTH_SHORT).show();
                  }*/
              }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        listdisplay.setAdapter(new CodeAdapter(dataList));
    }

}
