package com.ai.game.instashop;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;

import java.security.Key;
import java.util.ArrayList;
import java.util.List;

public class ResetPasswordActivity extends AppCompatActivity implements View.OnKeyListener {
    public EditText email;
    boolean emailFound;

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
            resetPassword(v);
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        email = findViewById(R.id.editTextTextEmailAddress);

        email.setOnKeyListener(this);
    }

    private void showAlert(String title, String message, boolean error) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ResetPasswordActivity.this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", (dialog, which) -> {
                    dialog.cancel();
                    // don't forget to change the line below with the names of your Activities
                    if (!error) {
                        Intent intent = new Intent(ResetPasswordActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                });
        AlertDialog ok = builder.create();
        ok.show();
    }

    public void resetPassword(View view){
        if(TextUtils.isEmpty(email.getText())) email.setError("Email-Id Required");
        else {
            ParseQuery<ParseUser> query = ParseUser.getQuery();
            query.whereEqualTo("email", email.getText().toString());
            query.findInBackground(new FindCallback<ParseUser>() {
                @Override
                public void done(List<ParseUser> objects, ParseException e) {
                    if(e == null && objects.size() > 0){
                        ParseUser.requestPasswordResetInBackground(email.getText().toString(), new RequestPasswordResetCallback() {
                            public void done(ParseException e) {
                                if (e == null) {
                                    // An email was successfully sent with reset instructions.
                                    showAlert("Email Sent!", "An email was successfully sent to " + email.getText().toString() + " with reset instructions.", false);
                                } else {
                                    // Something went wrong. Look at the ParseException to see what's up.
                                    showAlert("Error Password Reset failed", "Something went wrong" + " :" + e.getMessage(), true);
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                    else{
                        showAlert("Error Password Reset failed", "Something went wrong" + " : Email not found.", true);
                    }
                }
            });
        }

    }
}