package com.ai.game.instashop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.LogOutCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public EditText eMail, password;
    public ImageView togglePasswordVisibility;
    public Boolean passwordVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        eMail = findViewById(R.id.editTextTextEmailAddress);
        password = findViewById(R.id.editTextTextPassword);
        togglePasswordVisibility = findViewById(R.id.passwordVisibility);

        ParseAnalytics.trackAppOpenedInBackground(getIntent());
    }

    public void togglePasswordVisibility(View v) {
        if(!passwordVisible) {
            password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            togglePasswordVisibility.setImageResource(R.drawable.ic_password_visible);
            password.setSelection(password.getText().length());
            passwordVisible = true;
        }
        else {
            password.setTransformationMethod(PasswordTransformationMethod.getInstance());
            togglePasswordVisibility.setImageResource(R.drawable.ic_password_invisible);
            password.setSelection(password.getText().length());
            passwordVisible = false;
        }
    }

    public void login(View view){
        if(TextUtils.isEmpty(eMail.getText()) || TextUtils.isEmpty(password.getText())) {
            if(TextUtils.isEmpty(eMail.getText())) eMail.setError("Email-Id Required");
            if(TextUtils.isEmpty(password.getText())) {
                password.setError("Password Required");
            }
        }
        else{
            ParseUser.logInInBackground(eMail.getText().toString(), password.getText().toString(), new LogInCallback() {
                @Override
                public void done(ParseUser user, ParseException e) {
                    if(e == null && user != null){
                        Toast.makeText(getApplicationContext(), "Logged In!", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
    public void signUp(View view){
        Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
        startActivity(intent);
    }
}