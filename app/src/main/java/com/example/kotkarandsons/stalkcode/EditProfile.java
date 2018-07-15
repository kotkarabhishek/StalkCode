package com.example.kotkarandsons.stalkcode;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class EditProfile extends AppCompatActivity {

    private EditText username,email,age;
    private String updateduname,updateduemail,updateduage;
    private Button saveProfile;
    private TextView changepassword;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private ImageView image;
    private DatabaseReference databaseReference;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private static int PICK_IMAGE=123;
    Uri imagepath;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==PICK_IMAGE && resultCode==RESULT_OK && data.getData()!=null) {
            imagepath = data.getData();
            try {
                Bitmap bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),imagepath);
                image.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        username=(EditText)findViewById(R.id.usernameupdate);
        email=(EditText)findViewById(R.id.emailupdate);
        age=(EditText)findViewById(R.id.ageupdate);
        changepassword=(TextView)findViewById(R.id.changepasswordprofile);
        image=(ImageView)findViewById(R.id.getimageprofileupdate);
        saveProfile=(Button)findViewById(R.id.save);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        firebaseStorage=FirebaseStorage.getInstance();
        storageReference=firebaseStorage.getReference();
        storageReference.child(firebaseAuth.getUid()).child("Images").child("ProfilePic").getDownloadUrl().addOnSuccessListener(
                new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(image);
                        Toast.makeText(EditProfile.this,"Updated",Toast.LENGTH_SHORT).show();
                    }
                }
        ).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(EditProfile.this,"Not update",Toast.LENGTH_SHORT).show();
            }
        });


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final DatabaseReference databaseReference=firebaseDatabase.getReference(firebaseAuth.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserProfile userProfile=dataSnapshot.getValue(UserProfile.class);
                username.setText(userProfile.getUserName());
                email.setText(userProfile.getEmail());
                age.setText(userProfile.getAge());
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(EditProfile.this,databaseError.getCode(),Toast.LENGTH_SHORT).show();

            }
        });
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Image"),PICK_IMAGE);
                Toast.makeText(EditProfile.this,"ImageClicked",Toast.LENGTH_SHORT).show();
            }
        });
        saveProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name1=username.getText().toString().trim();
                String email1=email.getText().toString().trim();
                String age1=age.getText().toString().trim();
                UserProfile userProfile=new UserProfile(name1,email1,age1);
                databaseReference.setValue(userProfile);
                StorageReference imageReference=storageReference.child(firebaseAuth.getUid()).child("Images").child("ProfilePic");
                UploadTask uploadTask=imageReference.putFile(imagepath);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EditProfile.this,"Successful",Toast.LENGTH_SHORT).show();
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(EditProfile.this,"Failed",Toast.LENGTH_SHORT).show();
                    }
                });
                finish();
                startActivity(new Intent(EditProfile.this,SecondActivity.class));
            }
        });
        changepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditProfile.this,ChangePassword.class));
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
