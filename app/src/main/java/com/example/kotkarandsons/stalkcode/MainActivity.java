package com.example.kotkarandsons.stalkcode;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.oob.SignUp;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    private EditText Username;
    private EditText Password;
    private Button Submit;
    private TextView Attempt,SignUp,resetPassword;
    private int count=5;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Username=(EditText)findViewById(R.id.username);
        Password=(EditText)findViewById(R.id.password);
        Submit=(Button)findViewById(R.id.submit);
        SignUp=(TextView)findViewById(R.id.signup);
        Attempt=(TextView)findViewById(R.id.attempts);
        resetPassword=(TextView)findViewById(R.id.resetpassword);
        Attempt.setText("Attempts Remaining: 5");
        firebaseAuth=FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(this);
        FirebaseUser user=firebaseAuth.getCurrentUser();
     if(user!=null)
        {
            finish();
            startActivity(new Intent(MainActivity.this,SecondActivity.class));
        }

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate(Username.getText().toString(),Password.getText().toString());
            }
        });
        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,RegistrationActivity.class);
                startActivity(intent);
            }
        });
        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,ResetPassword.class));
            }
        });
    }

  protected  void validate(String username,String password)
    {
        progressDialog.setMessage("Verifiying Credentials");
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(username,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    progressDialog.dismiss();
                    checkEmailVerification();
                }
                else
                {
                    progressDialog.dismiss();
                    Toast.makeText(MainActivity.this,"Login UnSuccessful",Toast.LENGTH_SHORT).show();
                    count--;
                    Attempt.setText("No of remaining attempts: "+count);
                    if(count==0)
                        Submit.setEnabled(false);
                }
            }
        });
    }


    private  void checkEmailVerification()
    {
        FirebaseUser firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        Boolean emailflag=firebaseUser.isEmailVerified();
        if(emailflag==true)
        {
            finish();
            startActivity(new Intent(MainActivity.this,Drawer.class));
        }
        else
        {
            Toast.makeText(this,"Verify Mail",Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
        }
    }



}
