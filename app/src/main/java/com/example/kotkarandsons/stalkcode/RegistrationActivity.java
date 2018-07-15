package com.example.kotkarandsons.stalkcode;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.w3c.dom.Text;

import java.io.IOException;

public class RegistrationActivity extends AppCompatActivity {

    private EditText username, email, password,age;
    private Button registration;
    private TextView gotoLogin;
    private FirebaseAuth firebaseAuth;
   private FirebaseStorage firebaseStorage;

    public String name,email1,pass,age1;
    private ImageView profileImage;
  private static int PICK_IMAGE=123;
  private StorageReference storageReference;
  Uri imagepath;
    @Override
   protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==PICK_IMAGE && resultCode==RESULT_OK && data.getData()!=null) {
            imagepath = data.getData();
            try {
                Bitmap bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),imagepath);
                profileImage.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        setUI();
        firebaseAuth=FirebaseAuth.getInstance();
      firebaseStorage=FirebaseStorage.getInstance();
      storageReference=firebaseStorage.getReference();

        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (valid()) {
                    String Email = email.getText().toString().trim();
                    String Pass = password.getText().toString().trim();

                    firebaseAuth.createUserWithEmailAndPassword(Email, Pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {

                                sendUserData();
                                Toast.makeText(RegistrationActivity.this,"User Data Uploaded",Toast.LENGTH_SHORT).show();
                              //  finish();

                                // Sign in success, update UI with the signed-in user's information
                               sendVerficationEmail();
                               firebaseAuth.signOut();
                                startActivity(new Intent(RegistrationActivity.this,MainActivity.class));
                            } else {
                                Toast.makeText(RegistrationActivity.this, "UnSuccessful", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }
            }
        });

      profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Image"),PICK_IMAGE);
                Toast.makeText(RegistrationActivity.this,"ImageClicked",Toast.LENGTH_SHORT).show();
                }
        });


        gotoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }


    private void setUI() {
        username = (EditText) findViewById(R.id.rusername);
        email = (EditText) findViewById(R.id.remail);
        password = (EditText) findViewById(R.id.rpassword);
        registration = (Button) findViewById(R.id.register);
        gotoLogin = (TextView) findViewById(R.id.goLogin);
        age=(EditText) findViewById(R.id.age);
        profileImage=(ImageView)findViewById((R.id.profileimage));
    }

    private Boolean valid() {
        Boolean res=false;
        name = username.getText().toString();
        email1 = email.getText().toString();
        pass = password.getText().toString();
        age1= age.getText().toString();
        if (name.isEmpty() || email1.isEmpty() || pass.isEmpty() || age1.isEmpty() || imagepath==null ) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return res;
        }
        else {
            return true;
        }
    }

    private void sendVerficationEmail()
    {
        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null)
        {
            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful())
                    {
                        Toast.makeText(RegistrationActivity.this,"Registered Mail sent please verify",Toast.LENGTH_SHORT).show();
                        finish();
                      //  startActivity(new Intent(RegistrationActivity.this,MainActivity.class));

                    }
                    else
                    {
                        Toast.makeText(RegistrationActivity.this,"Verification Mail not Sent",Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
    }

    private void sendUserData()
    {

        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        DatabaseReference myref=firebaseDatabase.getReference(firebaseAuth.getCurrentUser().getUid());
        UserProfile userProfile=new UserProfile(name,email1,age1);
        myref.setValue(userProfile);
      StorageReference imageReference=storageReference.child(firebaseAuth.getUid()).child("Images").child("ProfilePic");
        UploadTask uploadTask=imageReference.putFile(imagepath);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
    Toast.makeText(RegistrationActivity.this,"Successful",Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(RegistrationActivity.this,"Failed",Toast.LENGTH_SHORT).show();
            }
        });
}
}

