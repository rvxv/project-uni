package com.uni.truthdare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.uni.truthdare.R;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private TextView playersTextView;
    private Button btn, truthBtn, dareBtn;
    private ImageView imgView;
    private Random random = new Random();
    private int lastDirection;
    private MediaPlayer mp;
    private int numberOfPlayers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = findViewById(R.id.button);
        truthBtn = findViewById(R.id.btn1);
        dareBtn = findViewById(R.id.btn2);
        imgView = findViewById(R.id.imageView);
        playersTextView = findViewById(R.id.playersTextView);

        // Retrieve the number of players from the intent extras
        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey("NUMBER_OF_PLAYERS")) {
            numberOfPlayers = extras.getInt("NUMBER_OF_PLAYERS");
        }

        // Set the number of players as the text of the TextView
        if (numberOfPlayers != 0) {
            playersTextView.setText("Number of Players: " + numberOfPlayers);
        }

        truthBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), TruthActivity.class));
            }
        });

        dareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), DareActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        truthBtn.setEnabled(false);
        dareBtn.setEnabled(false);
        btn.setEnabled(true);
    }

    public void spin(View view) {
        int numberOfPlayers = getIntent().getIntExtra("NUMBER_OF_PLAYERS", 4); // Default value is 4
        int anglePerPlayer = 360 / numberOfPlayers;
        int newDirection = random.nextInt(360); // Random angle within 360 degrees
        int rotationAngle = newDirection / anglePerPlayer * anglePerPlayer; // Adjusted rotation angle

        float pivotX = imgView.getWidth() / 2;
        float pivotY = imgView.getHeight() / 2;
        Animation rotate = new RotateAnimation(lastDirection, rotationAngle, pivotX, pivotY);
        rotate.setDuration(2000);
        rotate.setFillAfter(true);
        rotate.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                btn.setEnabled(false);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                truthBtn.setEnabled(true);
                dareBtn.setEnabled(true);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        lastDirection = rotationAngle;
        imgView.startAnimation(rotate);
    }
}
