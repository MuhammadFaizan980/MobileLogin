package com.example.muhammadfaizan.mobilelogin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class SigninActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    PhoneAuthProvider phoneAuthProvider;
    Button button;
    EditText edtPhone, edtCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        edtPhone = findViewById(R.id.edtPhone);
        edtCode = findViewById(R.id.edtCode);
        button = findViewById(R.id.btnSend);
        firebaseAuth = FirebaseAuth.getInstance();
        phoneAuthProvider = PhoneAuthProvider.getInstance(); //mobile provider


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phoneAuthProvider.verifyPhoneNumber(
                        edtPhone.getText().toString().trim(),
                        60,
                        TimeUnit.SECONDS,
                        SigninActivity.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                                firebaseAuth.signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            startActivity(new Intent(SigninActivity.this, MainActivity.class));
                                            finish();
                                        } else {
                                            Toast.makeText(SigninActivity.this, "Error", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }

                            @Override
                            public void onVerificationFailed(FirebaseException e) {

                            }
                        }
                );
            }
        });


    }
}
