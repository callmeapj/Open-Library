package com.example.aprajeyakhouri.finalapp;

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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    EditText enteremail,enterpassword;
    Button signinbutton,signupbutton;
    FirebaseAuth mauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        enteremail=findViewById(R.id.enteremail);
        enterpassword=findViewById(R.id.enterpassword);
        signinbutton=findViewById(R.id.signinbutton);
        signupbutton=findViewById(R.id.signupbutton);
        mauth=FirebaseAuth.getInstance();

        signinbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailtext=enteremail.getText().toString();
                String passwordtext=enterpassword.getText().toString();
                performsignin(emailtext,passwordtext);
            }
        });


    }
    public void performsignin(String emailtext,String passwordtext){
        mauth.signInWithEmailAndPassword(emailtext,passwordtext).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Intent intent=new Intent(MainActivity.this,Main2Activity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(MainActivity.this,"Wrong email id or password",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
