package com.example.lab08;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.lab08.dao.UserHappyDao;
import com.example.lab08.database.UserHappyDatabase;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import entity.UserHappy;

public class Login_Register extends AppCompatActivity {
    private FrameLayout container;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db;
    private UserHappyDao userHappyDao;
    private CollectionReference userCollection;

    public static final String FACE_KEY = "face";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_register);

        container = findViewById(R.id.login_register);
        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        userCollection = db.collection("User_happy");

        UserHappyDatabase.service.execute(()->{
            userHappyDao = UserHappyDatabase.getInstance(getApplicationContext())
                    .userHappyDao();
        });

        int mode = getIntent().getIntExtra(MainActivity.MODE, 1);

        if (mode == 1)
            setFragment(new SignFragment((email, pass) ->
                firebaseAuth.signInWithEmailAndPassword(email, pass)
                        .addOnCompleteListener(Login_Register.this, task -> {
                    if (task.isSuccessful()) {
                        UserHappy userHappy = userHappyDao.getUserByEmail(email);
                        setFragment(new FaceFragment(userHappy));
                    } else
                        showMess("Fail");
                })));
        else {
            setFragment(new RegisterFragment(user -> {
                firebaseAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword())
                        .addOnCompleteListener(Login_Register.this, task -> {
                            if (task.isSuccessful()) {
                                showMess("Thanh cong roi bro");
                                userHappyDao.insert(user);
                                addUserToFirestore(user);
                            } else
                                showMess("That bai roi cau oi");
                        });
            }));
        }
    }

    private void addUserToFirestore(UserHappy user) {
        userCollection.add(user).addOnSuccessListener(this,documentReference -> {
            showMess("Them vao firestore thanh cong roi");
        }).addOnFailureListener(this,e ->{
            showMess("Fail");
            Log.e(Login_Register.class.getName(),e.getMessage());
        });
    }

    private void setFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().
                replace(container.getId(), fragment).commit();
    }

    public void showMess(String a) {
        new AlertDialog.Builder(this)
                .setMessage(a)
                .setPositiveButton("Okay", null).show();
    }
}
