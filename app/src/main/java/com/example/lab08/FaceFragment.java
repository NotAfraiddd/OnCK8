package com.example.lab08;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


import androidx.fragment.app.Fragment;

import com.example.lab08.dao.UserHappyDao;
import com.example.lab08.database.UserHappyDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

import entity.UserHappy;


public class FaceFragment extends Fragment {
    private ImageView happy, sad, normal;
    private Button btnFinish;

    public FaceFragment(UserHappy userHappy){
        Bundle bundle = new Bundle();
        bundle.putSerializable(Login_Register.FACE_KEY,userHappy);
        setArguments(bundle);
    }
    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {

        UserHappyDao userHappyDao = UserHappyDatabase.getInstance(getContext()).userHappyDao();
        View faceView = inflater.inflate(R.layout.fragment_face,container,false);

        UserHappy userHappy = (UserHappy) getArguments().getSerializable(Login_Register.FACE_KEY);

        happy = faceView.findViewById(R.id.imageHappy);
        normal = faceView.findViewById(R.id.imageNormal);
        sad = faceView.findViewById(R.id.imageSad);
        btnFinish = faceView.findViewById(R.id.btn_finish);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        happy.setOnClickListener(view -> {
            userHappy.setHappy(userHappy.getHappy() + 1);
            UserHappyDatabase.service.execute(() -> {
                userHappyDao.update(userHappy);
            });
            showMess();
        });

        normal.setOnClickListener(view -> {
            userHappy.setNormal(userHappy.getNormal() + 1);
            UserHappyDatabase.service.execute(() -> {
                userHappyDao.update(userHappy);
            });
            showMess();
        });

        sad.setOnClickListener(view -> {
            userHappy.setSad(userHappy.getSad() + 1);
            UserHappyDatabase.service.execute(() -> {
                userHappyDao.update(userHappy);
            });
            showMess();
        });

        btnFinish.setOnClickListener(view -> {

            UserHappy updated = userHappyDao.getUserByEmail(userHappy.getEmail());

            new AlertDialog.Builder(getContext())
                    .setMessage("Email: " + updated.getEmail() +
                            "Happy: " + updated.getHappy() +
                            "\nNormal: " + updated.getNormal() +
                            "\nUnhappy: " + updated.getSad())
                    .setPositiveButton("Ok", null).show();

            CollectionReference col = db.collection("User_happy");
            col.whereEqualTo("email", updated.getEmail()).get()
                    .addOnCompleteListener(getActivity(), task -> {
                        if (task.isSuccessful())
                            for (QueryDocumentSnapshot doc : task.getResult()) {
                                Map<Object, Object> updatedUser = new HashMap<>();
                                updatedUser.put("happy", (int) updated.getHappy());
                                updatedUser.put("unhappy", (int) updated.getSad());
                                updatedUser.put("normal", (int) updated.getNormal());
                                col.document(doc.getId()).set(updatedUser, SetOptions.merge());
                            }
                    });
        });

        return faceView;
    }

    public void showMess() {
        Toast.makeText(getContext(), "Thank you for your feedback! :)", Toast.LENGTH_SHORT).show();
    }
}
