package com.studentlog.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.studentlog.R;
import com.studentlog.dashboard.DashBoardActivity;
import com.studentlog.utils.SPManager;

public class LoginActivity extends AppCompatActivity {

    EditText edUserName, edUserPass;
    Button btnLogin, btnSignUp;
    Context mContext;
    SPManager sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edUserName = findViewById(R.id.ed_user_name);
        edUserPass = findViewById(R.id.ed_user_password);

        btnLogin = findViewById(R.id.btn_login);
        btnSignUp = findViewById(R.id.btn_signUp);

        sp = SPManager.getInstance(this);

        btnLogin.setOnClickListener(v -> {

            if(checkLoginCredentials()){

                if(edUserName.getText().toString().equals("admin") && edUserPass.getText().toString().equals("admin")){
                    sp.setUserLoginName("admin");
                    sp.setUserLoginPass("admin");

                    Intent intent = new Intent(this, DashBoardActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(mContext, "Restricted access to admin only", Toast.LENGTH_SHORT).show();
                }
            }
        });


        btnSignUp.setOnClickListener(v->{
            Intent intent = new Intent(this, SignUpActivity.class);
            startActivity(intent);
        });

    }



    private boolean checkLoginCredentials(){

        if(TextUtils.isEmpty(edUserName.getText().toString().trim())){
            Toast.makeText(mContext, "Please enter UserName", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(TextUtils.isEmpty(edUserPass.getText().toString().trim())){
            Toast.makeText(mContext, "Please enter Password", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

}
