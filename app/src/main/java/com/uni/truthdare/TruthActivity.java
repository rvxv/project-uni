package com.uni.truthdare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.uni.truthdare.R;
import com.google.gson.Gson;

import java.util.ArrayList;

public class TruthActivity extends AppCompatActivity {
    private ArrayList<TruthItem> truthList;
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Gson gson = new Gson();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_values);
        // Initialize shared preferences
        sharedPreferences = getSharedPreferences("mySharedPreference", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        // Set up the toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Initialize truth list and RecyclerView
        truthList = new ArrayList<>();
        recyclerViewConfig();

        // Load default and user-added truths
        populateDefaultData();
        if(sharedPreferences.contains("UserTruths"))
            populateUserData(sharedPreferences.getString("UserTruths", null));
    }

    // Populate default truths from Values class
    public void populateDefaultData() {
        Values values = new Values();
        for(int i=0; i<values.truths.length; i++)
            truthList.add(new TruthItem(values.truths[i]));
    }

    // Populate user-added truths from SharedPreferences
    public void populateUserData(String jsonTruths) {
        String[] truths = gson.fromJson(jsonTruths, String[].class);
        for (String truth : truths) truthList.add(new TruthItem(truth));
    }

    public void recyclerViewConfig() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        adapter = new TruthAdapter(truthList, this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    public void showDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.custom_dialog_box);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        final EditText input = dialog.findViewById(R.id.editText);
        Button dismiss = dialog.findViewById(R.id.dismiss);
        Button add = dialog.findViewById(R.id.add);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mText = input.getText().toString();
                if(mText.isEmpty())
                    Toast.makeText(getApplicationContext(), "Empty Text", Toast.LENGTH_LONG).show();
                else{
                    updateUserData(mText);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(getApplicationContext(), "Successfully Added", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });

        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        dialog.show();
    }

    public void updateUserData(String string) {
        ArrayList<String> textList = new ArrayList<>();
        if(sharedPreferences.contains("UserTruths")) {
            String jsonTruths = sharedPreferences.getString("UserTruths", null);
            String[] truths = gson.fromJson(jsonTruths, String[].class);
            for (String truth : truths) textList.add(truth);
        }

        textList.add(string);
        editor.putString("UserTruths", gson.toJson(textList));
        editor.apply();
        truthList.add(new TruthItem(string));
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return  super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                showDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
