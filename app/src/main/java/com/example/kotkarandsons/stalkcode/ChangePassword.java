package com.example.kotkarandsons.stalkcode;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangePassword extends AppCompatActivity {
private EditText password;
private Button change1;
private FirebaseUser firebaseUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        change1= findViewById(R.id.changeg);
        password= findViewById(R.id.changeeditpassword);
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      change1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password1=password.getText().toString();
              firebaseUser.updatePassword(password1).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(ChangePassword.this,"Password Changed Successfully",Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(new Intent(ChangePassword.this,MainActivity.class));
                        }
                        else
                        {
                            Toast.makeText(ChangePassword.this,"Password Change Failed",Toast.LENGTH_SHORT).show();
                        }

                    }
                });
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
