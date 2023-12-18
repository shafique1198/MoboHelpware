package com.example.mobohelpware;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginOrSignUp extends AppCompatActivity {


    //variables

    Button callSignUp, login_btn;
    ImageView image;
    TextView logoText, sloganText;
    TextInputLayout username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login_or_sign_up);

        //Hooks
        callSignUp = findViewById(R.id.sign_up);
        image = findViewById(R.id.logo_image);
        logoText = findViewById(R.id.logo_name);
        sloganText = findViewById(R.id.slogan_name);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login_btn =(Button) findViewById(R.id.login_btn);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //loginUser();
                if (!validateUsername() | !validatePassword()) {
                    return;
                } else {
                    isUser();
                }
            }
        });

        /*login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openUserProfileActivity();

            }

        public void openUserProfileActivity(){

                Intent intent = new Intent(LoginOrSignUp.this,UserProfile.class);
                startActivity(intent);


        }
        });*/


        callSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginOrSignUp.this, SignUp.class);
                Pair[] pairs = new Pair[7];
                pairs[0] = new Pair<View, String>(image, "logo_image");
                pairs[1] = new Pair<View, String>(logoText, "logo_text");
                pairs[2] = new Pair<View, String>(sloganText, "logo_desc");
                pairs[3] = new Pair<View, String>(username, "username_tran");
                pairs[4] = new Pair<View, String>(password, "password_tran");
                pairs[5] = new Pair<View, String>(login_btn, "button_tran");
                pairs[6] = new Pair<View, String>(callSignUp, "login_signup_tran");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(LoginOrSignUp.this, pairs);
                startActivity(intent, options.toBundle());
            }
        });
    }

    private Boolean validateUsername() {
        String val = username.getEditText().getText().toString();
        if (val.isEmpty()) {
            username.setError("Field cannot be empty");
            return false;
        } else {
            username.setError(null);
            username.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePassword() {
        String val = password.getEditText().getText().toString();
        if (val.isEmpty()) {
            password.setError("Field cannot be empty");
            return false;
        } else {
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }
    }

    /*public void loginUser() {
        //Validate Login Info
        if (!validateUsername() | !validatePassword()) {
            return;
        } else {
            isUser();
        }
    }*/

    private void isUser() {

        final String userEnteredUsername = username.getEditText().getText().toString().trim();
        final String userEnteredPassword = password.getEditText().getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");

        Query checkUser = reference.orderByChild("username").equalTo(userEnteredUsername);
        //Query checkPass = reference.orderByChild("password").equalTo(userEnteredPassword);



        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {


            @Override

            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){

                    username.setError(null);
                    username.setErrorEnabled(false);

                    String passwordFromDB =(String) dataSnapshot.child(userEnteredUsername).child("password").getValue(String.class);

                    if(passwordFromDB.equals(userEnteredPassword)) {

                        username.setError(null);
                        username.setErrorEnabled(false);

                        String nameFromDB =(String) dataSnapshot.child(userEnteredUsername).child("name").getValue(String.class);
                        String usernameFromDB =(String) dataSnapshot.child(userEnteredUsername).child("username").getValue(String.class);
                        String phoneNoFromDB =(String) dataSnapshot.child(userEnteredUsername).child("phoneNo").getValue(String.class);
                        String emailFromDB =(String) dataSnapshot.child(userEnteredUsername).child("email").getValue(String.class);

                        Intent intentLogin = new Intent(getApplicationContext(),UserProfile.class);

                        intentLogin.putExtra("name", nameFromDB);
                        intentLogin.putExtra("username", usernameFromDB);
                        intentLogin.putExtra("email", emailFromDB);
                        intentLogin.putExtra("phoneNo", phoneNoFromDB);
                        intentLogin.putExtra("password", passwordFromDB);

                        startActivity(intentLogin);

                    }

                    else {
                        password.setError("Wrong Password");
                        password.requestFocus();
                    }
                }

                else{

                    username.setError("No such user exists");
                    username.requestFocus();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


}
