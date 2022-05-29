package com.ai.game.instashop;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignUpActivity extends AppCompatActivity implements View.OnKeyListener{
    public EditText name, eMail, password, confirmPassword;
    public ImageView togglePasswordVisibility;
    public ImageView togglePasswordVisibility2;
    public Boolean passwordVisible = false;
    public Boolean passwordVisible2 = false;

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN){
            signUp(v);
        }
        return false;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        name = findViewById(R.id.editTextTextPersonName);
        eMail = findViewById(R.id.editTextTextEmailAddress);
        password = findViewById(R.id.editTextTextPassword);
        confirmPassword = findViewById(R.id.editTextTextPassword2);
        togglePasswordVisibility = findViewById(R.id.passwordVisibility);
        togglePasswordVisibility2 = findViewById(R.id.passwordVisibility2);

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

        confirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (confirmPassword.getText().length() != 0){
                    togglePasswordVisibility2.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        confirmPassword.setOnKeyListener(this);
        ParseAnalytics.trackAppOpenedInBackground(getIntent());
    }

    public void logIn(View view){
        finish();
    }

    private void showAlert(String title, String message, boolean error) {
        AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", (dialog, which) -> {
                    dialog.cancel();
                    // don't forget to change the line below with the names of your Activities
                    if (!error) {
                        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                });
        AlertDialog ok = builder.create();
        ok.show();
    }

    public void signUp(View view){
        if(TextUtils.isEmpty(name.getText()) || TextUtils.isEmpty(eMail.getText()) || TextUtils.isEmpty(password.getText()) || TextUtils.isEmpty(confirmPassword.getText())) {
            if(TextUtils.isEmpty(eMail.getText())) eMail.setError("Email-Id Required");
            if(TextUtils.isEmpty(name.getText())) name.setError("Name Required");
            if(TextUtils.isEmpty(password.getText())) {
                password.setError("Password Required");
                togglePasswordVisibility.setVisibility(View.INVISIBLE);
            }
            if(TextUtils.isEmpty(confirmPassword.getText())) {
                confirmPassword.setError("Confirm Password Required");
                togglePasswordVisibility2.setVisibility(View.INVISIBLE);
            }
        }
        else if(!password.getText().toString().equals(confirmPassword.getText().toString()))
            Toast.makeText(getApplicationContext(), "Password Mismatch", Toast.LENGTH_SHORT).show();

        else{
            ParseUser user = new ParseUser();
            user.setUsername(name.getText().toString().trim());
            user.setEmail(eMail.getText().toString().trim());
            user.setPassword(password.getText().toString());
            user.put("Name", name.getText().toString().trim());
            user.saveInBackground();
            user.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {
                    if(e == null){
//                        Toast.makeText(getApplicationContext(), "Sign-Up Successful", Toast.LENGTH_SHORT).show();
                        showAlert("Account Created Successfully!", "Please verify your email before Login", false);
                    }
                    else {
                        ParseUser.logOut();
//                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        showAlert("Error Account Creation failed", "Account could not be created" + " :" + e.getMessage(), true);
                        e.printStackTrace();
                    }
                }
            });
        }
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
    public void togglePasswordVisibility2(View v) {
        if(!passwordVisible2) {
            confirmPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            togglePasswordVisibility2.setImageResource(R.drawable.ic_password_visible);
            confirmPassword.setSelection(confirmPassword.getText().length());
            passwordVisible2 = true;
        }
        else {
            confirmPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            togglePasswordVisibility2.setImageResource(R.drawable.ic_password_invisible);
            confirmPassword.setSelection(confirmPassword.getText().length());
            passwordVisible2 = false;
        }
    }
}