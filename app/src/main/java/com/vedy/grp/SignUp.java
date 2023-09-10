package com.vedy.grp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SignUp extends AppCompatActivity {
    EditText name;
    EditText email;
    EditText password;
    Button signup;
    FirebaseAuth mAuth;
    ProgressBar pgbr;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent usee = new Intent(getApplicationContext(), Gas.class);
            startActivity(usee);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();
     name = findViewById(R.id.editname);
     email = findViewById(R.id.editemail);
     password = findViewById(R.id.editpass);
     signup = findViewById(R.id.SignUp);
     pgbr =findViewById(R.id.progressbrr);

        View v= findViewById(R.id.textView7);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUp.this,Login.class);
                startActivity(intent);
            }
        });


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pgbr.setVisibility(View.VISIBLE);
                String textemail = email.getText().toString();
                String textname = name.getText().toString();
                String textpass = password.getText().toString();

                if(TextUtils.isEmpty(textemail)){
                    Toast.makeText(SignUp.this,"Please Enter Email",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(textname)){
                    Toast.makeText(SignUp.this,"Please Enter YourName",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(textpass)){
                    Toast.makeText(SignUp.this,"Password cannot be empty",Toast.LENGTH_SHORT).show();
                    return;
                }

                HashMap<String,Object> usere = new HashMap<>();
                usere.put("Name",textname);
                usere.put("Email",textemail);
                usere.put("Password",textpass);

                FirebaseDatabase.getInstance().getReference().child("Users").push().setValue(usere);


                mAuth.createUserWithEmailAndPassword(textemail, textpass)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                pgbr.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information

                                    Toast.makeText(SignUp.this, "Authentication Success.",
                                            Toast.LENGTH_SHORT).show();
                                    Intent in = new Intent(SignUp.this,Login.class);
                                    startActivity(in);


                                } else {
                                    // If sign in fails, display a message to the user.

                                    Toast.makeText(SignUp.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();

                                }
                            }
                        });


            }
        });
    }
}