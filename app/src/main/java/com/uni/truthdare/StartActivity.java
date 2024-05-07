package com.uni.truthdare;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

public class StartActivity extends AppCompatActivity {
    private Button start, truth, dare, toggleButton, selectPlayers;
    private int numberOfPlayers = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        start = findViewById(R.id.start);
        truth = findViewById(R.id.truth);
        dare = findViewById(R.id.dare);
        toggleButton = findViewById(R.id.toggle_button);
        selectPlayers = findViewById(R.id.select_players);

        truth.setVisibility(View.GONE);
        dare.setVisibility(View.GONE);

        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (truth.getVisibility() == View.VISIBLE) {
                    // Hide Truth and Dare buttons
                    truth.setVisibility(View.GONE);
                    dare.setVisibility(View.GONE);
                } else {
                    // Show Truth and Dare buttons
                    truth.setVisibility(View.VISIBLE);
                    dare.setVisibility(View.VISIBLE);
                    toggleButton.setVisibility(View.GONE);
                }
            }
        });


        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("NUMBER_OF_PLAYERS", numberOfPlayers);
                startActivity(intent);
            }
        });

        truth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), TruthActivity.class));
            }
        });

        dare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), DareActivity.class));
            }
        });

        selectPlayers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Show the dialog to select the number of players
                showSelectPlayersDialog();
            }
        });
    }

        private void showSelectPlayersDialog() {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Select Number of Players");

            final String[] playerNumbers = {"2", "3", "4", "5", "6", "7", "8", "9", "10"}; // Example player numbers

            builder.setItems(playerNumbers, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Store the selected number of players
                    numberOfPlayers = Integer.parseInt(playerNumbers[which]);
                    // Show or hide the start button based on whether players are selected
                    start.setVisibility(numberOfPlayers > 0 ? View.VISIBLE : View.INVISIBLE);
                }
            });

            builder.show();

    }
}
