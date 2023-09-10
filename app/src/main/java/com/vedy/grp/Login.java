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

public class Login extends AppCompatActivity {


    EditText Email;
    EditText Pass;
    Button Login;
    ProgressBar pgbr;
    FirebaseAuth mAuth;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
          Intent usee = new Intent(getApplicationContext(),Gas.class);
          startActivity(usee);
          finish();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Email = findViewById(R.id.editTextEmail);
        Pass = findViewById(R.id.editTextPassword);
        Login =findViewById(R.id.Login);
        pgbr = findViewById(R.id.progressbrr);

        mAuth = FirebaseAuth.getInstance();

View v= findViewById(R.id.textView3);
v.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent = new Intent(Login.this,SignUp.class);
        startActivity(intent);
    }
});

Login.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        pgbr.setVisibility(View.VISIBLE);
        String textemail = Email.getText().toString();

        String textpass = Pass.getText().toString();

        if(TextUtils.isEmpty(textemail)){
            Toast.makeText(Login.this,"Please Enter Email",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(textpass)){
            Toast.makeText(Login.this,"Please Enter Password",Toast.LENGTH_SHORT).show();
            return;
        }
        if(textpass.length() <8){
            Toast.makeText(Login.this,"Please enter long Password",Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(textemail, textpass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        pgbr.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            Intent innn =new Intent(getApplicationContext(),Gas.class);
                            startActivity(innn);
                            finish();

                        } else {
                            // If sign in fails, display a message to the user.

                            Toast.makeText(Login.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }
});




    }
}
