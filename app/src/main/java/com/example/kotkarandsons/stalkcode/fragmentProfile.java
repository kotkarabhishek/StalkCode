package com.example.kotkarandsons.stalkcode;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class fragmentProfile extends Fragment {



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragmentprofile,null);

    }
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    private TextView name,email,age;
    private ImageView image;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        name=(TextView) view.findViewById(R.id.fragmentuser);
        email=(TextView) view.findViewById(R.id.fragmentemail);
        age=(TextView) view.findViewById(R.id.fragmentage);
        image=(ImageView)view.findViewById(R.id.fragmentimage);
       firebaseStorage=FirebaseStorage.getInstance();
        storageReference=firebaseStorage.getReference();

        storageReference.child(firebaseAuth.getUid()).child("Images").child("ProfilePic").getDownloadUrl().addOnSuccessListener(
                new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).fit().centerCrop().into(image);
                        Toast.makeText(getActivity(),"Updated",Toast.LENGTH_SHORT).show();
                    }
                }
        ).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(),"Not update",Toast.LENGTH_SHORT).show();
            }
        });

        databaseReference=firebaseDatabase.getReference(firebaseAuth.getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserProfile userProfile=dataSnapshot.getValue(UserProfile.class);
                name.setText("UserName "+userProfile.getUserName());
                email.setText("Email: "+userProfile.getEmail());
                age.setText("Age: "+userProfile.getAge());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(),databaseError.getCode(),Toast.LENGTH_SHORT).show();

            }
        });

       }
}
