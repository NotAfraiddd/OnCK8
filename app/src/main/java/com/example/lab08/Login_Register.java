package com.example.lab08;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;

import listener.IOnEmailAuthenListener;

public class Login_Register extends AppCompatActivity {
    private FrameLayout container;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_register);

        container = findViewById(R.id.login_register);
        //firebaseAuth =FirebaseAuth.getInstance();
        int mode = getIntent().getIntExtra(MainActivity.MODE,1);

        if(mode == 1)
            setFragment(new SignFragment(/*new IOnEmailAuthenListener() {
                @Override
                public void signInWithPassAndEmail(String email, String pass) {
                    firebaseAuth.signInWithEmailAndPassword(email,pass)
                            .addOnCompleteListener(Login_Register.this, task -> {
                                if(task.isSuccessful())
                                    setFragment(new FaceFragment());
                                else showMess("Fail");
                            });
                }
            }*/));
        else setFragment(new RegisterFragment(/*new IOnEmailAuthenListener(){
            @Override
            public void signInWithPassAndEmail(String email, String pass) {
                    firebaseAuth.signInWithEmailAndPassword(email,pass)
                            .addOnCompleteListener(Login_Register.this,task -> {
                                if(task.isSuccessful())
                                    setFragment(new FaceFragment());
                                else showMess("Khong duoc roi ban oi !!!");
                            });
            }
        }*/ ));

    }
    private void setFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().
                replace(container.getId(),fragment).commit();
    }
    public void showMess(String a){
        new AlertDialog.Builder(this)
                .setMessage(a)
                .setPositiveButton("Okay",null).show();
    }
}
