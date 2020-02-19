package com.studentlog.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.studentlog.R;
import com.studentlog.model.Data;


public class SignUpActivity extends AppCompatActivity {

    private SignUpVM signUpViewModel;

    EditText edUserName, edPass, edMobileNo;

    Context mContext;

    Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signUpViewModel = ViewModelProviders.of(this).get(SignUpVM.class);

        edUserName = findViewById(R.id.ed_user_name);
        edPass = findViewById(R.id.ed_user_pass);
        edMobileNo = findViewById(R.id.ed_mob_no);

        btnSignUp = findViewById(R.id.btn_signup);

        mContext = this;

        btnSignUp.setOnClickListener(v->{

            if(checkCredentials()){
                signUpViewModel.signUpUser(edUserName.getText().toString(),
                        edPass.getText().toString(),
                        edMobileNo.getText().toString());
            }

        });

        signUpViewModel.getInserResult().observe(this, data -> {

            if(data!=null){
                switch (data.status){

                    case SUCCESS:{
                        Toast.makeText(mContext, "Sign up successfully", Toast.LENGTH_SHORT).show();
                        break;
                    }

                    case ERROR:{
                        Toast.makeText(mContext, "Sign up Unssuccess", Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
            }

        });


    }

    private boolean checkCredentials(){

        if(TextUtils.isEmpty(edUserName.getText().toString().trim())){
            Toast.makeText(mContext, "Please enter UserName", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(TextUtils.isEmpty(edPass.getText().toString().trim())){
            Toast.makeText(mContext, "Please enter Password", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(TextUtils.isEmpty(edMobileNo.getText().toString().trim())){
            Toast.makeText(mContext, "Please enter Mobile Number", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

}
