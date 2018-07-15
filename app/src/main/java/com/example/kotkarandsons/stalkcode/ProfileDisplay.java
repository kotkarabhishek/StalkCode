package com.example.kotkarandsons.stalkcode;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class ProfileDisplay extends AppCompatActivity {

    private ImageView displayImage;
    private TextView dusername,demail,dage;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    private Button edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_display);

        displayImage=(ImageView)findViewById(R.id.dispimage);
        dusername=(TextView)findViewById(R.id.displayusername);
        demail=(TextView)findViewById(R.id.displayemail);
        dage=(TextView)findViewById(R.id.displayage);
        edit=(Button)findViewById(R.id.profileedit);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        firebaseStorage=FirebaseStorage.getInstance();
        storageReference=firebaseStorage.getReference();

        storageReference.child(firebaseAuth.getUid()).child("Images").child("ProfilePic").getDownloadUrl().addOnSuccessListener(
                new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(displayImage);
                        Toast.makeText(ProfileDisplay.this,"Updated",Toast.LENGTH_SHORT).show();
                    }
                }
        ).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ProfileDisplay.this,"Not update",Toast.LENGTH_SHORT).show();
            }
        });

        DatabaseReference databaseReference=firebaseDatabase.getReference(firebaseAuth.getUid());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileDisplay.this,EditProfile.class));
            }
        });

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserProfile userProfile=dataSnapshot.getValue(UserProfile.class);
                dusername.setText("UserName: "+userProfile.getUserName());
                demail.setText("Email: "+userProfile.getEmail());
                dage.setText("Age: "+userProfile.getAge());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ProfileDisplay.this,databaseError.getCode(),Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                                    onBackPressed();
                                    break;
        }
        return super.onOptionsItemSelected(item);
    }
}
