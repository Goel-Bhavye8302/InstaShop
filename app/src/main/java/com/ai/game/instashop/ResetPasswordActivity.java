package com.ai.game.instashop;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;

import java.util.ArrayList;
import java.util.List;

public class ResetPasswordActivity extends AppCompatActivity {
    public EditText email;
    boolean emailFound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        email = findViewById(R.id.editTextTextEmailAddress);
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
        emailFound = false;
        ParseQuery<ParseUser> query = ParseUser.getQuery();
//        query.findInBackground(new FindCallback<ParseObject>() {
//            @Override
//            public void done(List<ParseObject> objects, ParseException e) {
//                if(e == null && objects != null){
//                    for(ParseObject it : objects){
//                        Log.i("ajsdbajhdvasjdvhajh", it.getString("email"));
//                        if(it.get("email").toString().equals(email.getText().toString())){
//                            emailFound = true;
//                            break;
//                        }
//                    }
//                }
//            }
//        });
        try {
            List<ParseUser> objects = query.find();
            for(ParseUser it : objects){
                Log.i("ajsdbajhdvasjdvhajh", it.getString("username"));
                if(it.get("email").toString().equals(email.getText().toString())){
                    emailFound = true;
                    break;
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(TextUtils.isEmpty(email.getText())) email.setError("Email-Id Required");
        else if(emailFound){
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
            showAlert("Error Password Reset failed", "Email not found!", true);
        }
    }
}