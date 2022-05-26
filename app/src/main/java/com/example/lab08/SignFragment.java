package com.example.lab08;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


import androidx.fragment.app.Fragment;

import listener.IOnEmailAuthenListener;

public class SignFragment extends Fragment {

    private EditText txtEmail,txtPassWord;
    private Button btnSignIn;
    private IOnEmailAuthenListener onEmailAuthenListener;

    public SignFragment(IOnEmailAuthenListener iOnEmailAuthenListener) {
        this.onEmailAuthenListener = iOnEmailAuthenListener;
    }

    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View signView = inflater.inflate(R.layout.fragment_sign_in,container,false);

        txtEmail = signView.findViewById(R.id.txt_email);
        txtPassWord = signView.findViewById(R.id.txt_pass);
        btnSignIn = signView.findViewById(R.id.btn_sign_in2);

        btnSignIn.setOnClickListener(view -> {
            String email =txtEmail.getText().toString();
            String pass = txtPassWord.getText().toString();

            if(email.equals("") || pass.equals("")){
                showMess("email or pass is empty");
                return;
            }

            Log.e("Test", email + " - " + pass);
            onEmailAuthenListener.signInWithPassAndEmail(email,pass);
        });
        return signView;
    }

    public void showMess(String a){
        new AlertDialog.Builder(getContext())
                .setMessage(a)
                .setPositiveButton("Okay",null).show();
    }

}
