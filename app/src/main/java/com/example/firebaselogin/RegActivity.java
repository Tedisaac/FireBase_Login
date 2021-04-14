package com.example.firebaselogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegActivity extends AppCompatActivity {
EditText txtname,txtemail,txtpass,txtcpass;
Button register;
private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        firebaseAuth = FirebaseAuth.getInstance();

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.

        //mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
        actionBar.setDisplayHomeAsUpEnabled(true);

        txtname = findViewById(R.id.name);
        txtemail = findViewById(R.id.email);
        txtpass = findViewById(R.id.pass);
        txtcpass = findViewById(R.id.pass2);
        register = findViewById(R.id.reg);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = txtname.getText().toString().trim();
                String email = txtemail.getText().toString().trim();
                String password = txtpass.getText().toString().trim();
                String confirmpassword = txtcpass.getText().toString().trim();

                if (TextUtils.isEmpty(name)){
                    Toast.makeText(RegActivity.this,"PLease input name",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(email)){
                    Toast.makeText(RegActivity.this,"PLease input email",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    Toast.makeText(RegActivity.this,"PLease input password",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(confirmpassword)){
                    Toast.makeText(RegActivity.this,"PLease input confirm password",Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length()<6){
                    Toast.makeText(RegActivity.this,"Password length should be greater than 6 characters",Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.equals(confirmpassword)){
                    firebaseAuth.createUserWithEmailAndPassword(email,password)
                            .addOnCompleteListener(RegActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                       startActivity(new Intent(RegActivity.this,MainActivity.class));
                                       finish();
                                       Toast.makeText(RegActivity.this,"Registration successful",Toast.LENGTH_SHORT).show();
                                    } else {
                                       Toast.makeText(RegActivity.this,"Registration failed",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                //startActivity(new Intent(RegActivity.this,MainActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}