package com.example.kotkarandsons.stalkcode;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPassword extends AppCompatActivity {

    private EditText Email;
    private Button reset;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        firebaseAuth=FirebaseAuth.getInstance();
        Email=(EditText)findViewById(R.id.emailforreset);
        reset=(Button)findViewById(R.id.rresetpassword);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=Email.getText().toString().trim();
                if(email.equals(""))
                {
                    Toast.makeText(ResetPassword.this,"Enter Email",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(ResetPassword.this,"Email Sent",Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(ResetPassword.this,MainActivity.class));
                            }
                            else
                            {
                                Toast.makeText(ResetPassword.this,"Email Not Sent",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        
    }
}
