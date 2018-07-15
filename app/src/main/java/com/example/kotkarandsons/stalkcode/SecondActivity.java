package com.example.kotkarandsons.stalkcode;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class SecondActivity extends AppCompatActivity {

    private Button logout;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        firebaseAuth=FirebaseAuth.getInstance();
        logout=(Button)findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               Logout();
            }
        });
    }

    private void Logout()
    {
       firebaseAuth.signOut();
        finish();
        startActivity(new Intent(SecondActivity.this,MainActivity.class));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.logoutmenu:
                                Logout();
                                break;
            case R.id.profilemenu:
                                    startActivity(new Intent(SecondActivity.this,ProfileDisplay.class));
        }
        return super.onOptionsItemSelected(item);
    }
}
