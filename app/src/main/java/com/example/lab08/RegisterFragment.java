package com.example.lab08;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


import androidx.fragment.app.Fragment;

import entity.UserHappy;
import listener.IOnEmailAuthenListener;
import listener.IOnFirebaseStoreChange;

public class RegisterFragment extends Fragment {
    private EditText txtEmail,txtPass,txtName,txtCheckPass;
    private Button btnRegister2;
    private IOnFirebaseStoreChange iOnFirebaseStoreChange;

    public RegisterFragment(IOnFirebaseStoreChange iOnFirebaseStoreChange) {
        this.iOnFirebaseStoreChange = iOnFirebaseStoreChange;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View registerView = inflater.inflate(R.layout.fragment_register,container,false);
        txtName = registerView.findViewById(R.id.txt_yourName);
        txtEmail = registerView.findViewById(R.id.txt_email2);
        txtPass = registerView.findViewById(R.id.txt_pass2);
        txtCheckPass = registerView.findViewById(R.id.txt_check_pass2);
        btnRegister2 = registerView.findViewById(R.id.btn_register2);

        btnRegister2.setOnClickListener(view -> {
            String email = txtEmail.getText().toString();
            String name = txtName.getText().toString();
            String pass = txtPass.getText().toString();
            String checkPass = txtCheckPass.getText().toString();

            if(email.equals("") || name.equals("")|| pass.equals("")||checkPass.equals("")){
                showMess("Chua co dien thong tin ban oi !!!!!");
                return;
            }

            UserHappy userHappy = new UserHappy(name,email,pass);
            iOnFirebaseStoreChange.writeToFirestore(userHappy);
        });
        return registerView;
    }

    public void showMess(String a){
        new AlertDialog.Builder(getContext())
                .setMessage(a)
                .setPositiveButton("Okayy ne",null).show();
    }
}
