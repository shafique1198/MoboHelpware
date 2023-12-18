package com.example.mobohelpware;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ImageSliderActivity extends AppCompatActivity {

    //variables
    private Button skipButton;
    Animation topAnim, bottomAnim;
    ImageView image;
    TextView logo,slogan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_image_slider);
        //animations
        //topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        //bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);


        //hooks
        //image = findViewById(R.id.imageView);
        //logo = findViewById(R.id.textView);

        //image.setAnimation(topAnim);
        //logo.setAnimation(bottomAnim);

        skipButton =(Button) findViewById(R.id.skip);
        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openloginOrSignUpActivity();
            }
        });

    }
    public void openloginOrSignUpActivity(){

        Intent intent = new Intent(ImageSliderActivity.this, LoginOrSignUp.class);

        //Pair[] pairs = new Pair[2];
        //pairs[0] = new Pair<View,String>(image,"logo_image");
        //pairs[1] = new Pair<View,String>(logo,"logo_text");

        //ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(ImageSliderActivity.this,pairs);

        startActivity(intent/*,*options.toBundle()*/);
    }


}
