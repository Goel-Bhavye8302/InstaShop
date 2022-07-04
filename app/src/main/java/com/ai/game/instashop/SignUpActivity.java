package com.ai.game.instashop;

import androidx.annotation.NonNull;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity implements View.OnKeyListener{
    public EditText name, eMail, profession, password, confirmPassword;
    public ImageView togglePasswordVisibility;
    public ImageView togglePasswordVisibility2;
    public Boolean passwordVisible = false;
    public Boolean passwordVisible2 = false;

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;

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

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        name = findViewById(R.id.editTextTextPersonName);
        eMail = findViewById(R.id.editTextTextEmailAddress);
        profession = findViewById(R.id.editTextProfession);
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
                        mAuth.signOut();
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                });
        AlertDialog ok = builder.create();
        ok.show();
    }

    public void signUp(View view){
        if(TextUtils.isEmpty(name.getText()) || TextUtils.isEmpty(eMail.getText()) || TextUtils.isEmpty(profession.getText()) || TextUtils.isEmpty(password.getText()) || TextUtils.isEmpty(confirmPassword.getText())) {
            if(TextUtils.isEmpty(eMail.getText())) eMail.setError("Email-Id Required");
            if(TextUtils.isEmpty(name.getText())) name.setError("Name Required");
            if(TextUtils.isEmpty(profession.getText())) profession.setError("Profession Required");
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

        else {
            mAuth.createUserWithEmailAndPassword(eMail.getText().toString().trim(), password.getText().toString().trim())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Firebase_User user = new Firebase_User(
                                        name.getText().toString(),
                                        eMail.getText().toString(),
                                        profession.getText().toString(),
                                        password.getText().toString()
                                );
                                String uid = task.getResult().getUser().getUid();
                                database.getReference().child("Users").child(uid).setValue(user);
                                if(mAuth.getCurrentUser() != null){
                                    mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(SignUpActivity.this, "Verification Email sent!", Toast.LENGTH_SHORT).show();
                                                showAlert("Account Created Successfully!", "Please verify your email before Login", false);
                                            }
                                            else{
                                                showAlert("error!", task.getException().getMessage(), true);
                                            }
                                        }
                                    });
                                }
                            } else {
                                // If sign in fails, display a message to the user.
                                showAlert("Error : Account Creation failed", "Account could not be created" + " :" + task.getException().getMessage(), true);
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