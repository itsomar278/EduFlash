package com.example.eduflash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button addNewCardBtn;
    private Button studyBtn;
    private Button quickQuizBtn;
    private Button randomCardBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addNewCardBtn = findViewById(R.id.addNewCardBtn);
        studyBtn = findViewById(R.id.studyBtn);
        quickQuizBtn = findViewById(R.id.quickQuizBtn);
        randomCardBtn = findViewById(R.id.randomCardBtn);
        addNewCardBtn.setOnClickListener(this);
        studyBtn.setOnClickListener(this);
        quickQuizBtn.setOnClickListener(this);
        randomCardBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        // Handle button click events

        switch (view.getId()) {
            case R.id.addNewCardBtn:
                // Button "Add New Card" is pressed
                // Start the desired activity
                Intent addNewCardIntent = new Intent(MainActivity.this, TopicPickActivity.class);
                startActivity(addNewCardIntent);
                break;
            case R.id.studyBtn:
                // Button "Study" is pressed
                // Start the desired activity

                Intent studyIntent = new Intent(MainActivity.this, StudyActivity.class);
                startActivity(studyIntent);


                break;
            case R.id.quickQuizBtn:
                /*
                // Button "Quick Quiz" is pressed
                // Start the desired activity
                Intent quickQuizIntent = new Intent(MainActivity.this, QuickQuizActivity.class);
                startActivity(quickQuizIntent);

                 */
                break;
            case R.id.randomCardBtn:
                // Button "Random Card" is pressed
                // Start the desired activity
                /*
                Intent randomCardIntent = new Intent(MainActivity.this, RandomCardActivity.class);
                startActivity(randomCardIntent);
                break;

                 */
        }


    }


}