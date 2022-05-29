package com.ai.game.instashop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
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

public class MainActivity extends AppCompatActivity implements View.OnKeyListener {
    public EditText eMail, password;
    public ImageView togglePasswordVisibility;
    public Boolean passwordVisible = false;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN){
            login(v);
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        eMail = findViewById(R.id.editTextTextEmailAddress);
        password = findViewById(R.id.editTextTextPassword);
        togglePasswordVisibility = findViewById(R.id.passwordVisibility);

        password.setOnKeyListener(this);

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (password.getText().length() != 0){
                    togglePasswordVisibility.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
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

    private void showAlert(String title, String message, boolean error) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", (dialog, which) -> {
                    dialog.cancel();
                    // don't forget to change the line below with the names of your Activities
//                    if (!error) {
//                        Intent intent = new Intent(MainActivity.this, MainActivity.class);
//                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                        startActivity(intent);
//                    }
                });
        AlertDialog ok = builder.create();
        ok.show();
    }

    public void login(View view){
        if(TextUtils.isEmpty(eMail.getText()) || TextUtils.isEmpty(password.getText())) {
            if(TextUtils.isEmpty(eMail.getText())) eMail.setError("Email-Id Required");
            if(TextUtils.isEmpty(password.getText())) {
                password.setError("Password Required");
                togglePasswordVisibility.setVisibility(View.INVISIBLE);
            }
        }
        else{
            ParseUser.logInInBackground(eMail.getText().toString(), password.getText().toString(), new LogInCallback() {
                @Override
                public void done(ParseUser user, ParseException e) {
                    if(e == null && user != null){
//                        Toast.makeText(getApplicationContext(), "Logged In!", Toast.LENGTH_SHORT).show();
                        showAlert("Login Successful", "Welcome!", false);
                    }
                    else {
                        ParseUser.logOut();
                        e.printStackTrace();
//                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        showAlert("Login Fail", e.getMessage() + " Please try again", true);
                    }
                }
            });
        }
    }

    public void signUp(View view){
        Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
        startActivity(intent);
    }

    public void resetPassword(View view) {
        Intent intent = new Intent(getApplicationContext(), ResetPasswordActivity.class);
        startActivity(intent);
    }
}