package com.example.eduflash;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Add_New_Activity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {
    private Switch QuestionSwitch;
    private ImageView QuestionImageView;
    private Button QuestionImagePicker;
    private EditText QuestionTextView;
    private Switch AnswerSwitch;
    private ImageView AnswerImageView;
    private Button AnswerImagePicker;
    private EditText AnswerTextView;
    private Button SaveButton;
    private int Select_Image_Question = 1;
    private int Select_Image_Answer = 2;
    private SharedPreferences GlobalSharedPreferences;
    private SharedPreferences.Editor editor;

    private Card GlobalCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GlobalSharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        editor = GlobalSharedPreferences.edit();
        setContentView(R.layout.activity_add_new);

        QuestionSwitch = findViewById(R.id.QuestionSwitch);
        AnswerSwitch = findViewById(R.id.AnswerSwitch);
        AnswerSwitch.setOnCheckedChangeListener(this);
        QuestionSwitch.setOnCheckedChangeListener(this);

        QuestionImageView = findViewById(R.id.QuestionImageView);
        AnswerImageView = findViewById(R.id.AnswerImageView);

        QuestionImagePicker = findViewById(R.id.QuestionImagePicker);
        QuestionImagePicker.setOnClickListener(this);
        AnswerImagePicker = findViewById(R.id.AnswerImagePicker);

        QuestionTextView = findViewById(R.id.QuestionTextView);
        AnswerTextView = findViewById(R.id.AnswerTextView);

        SaveButton = findViewById(R.id.SaveButton);
        SaveButton.setOnClickListener(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (compoundButton.getId() == R.id.QuestionSwitch) {
            if (b) {
                QuestionImageView.setVisibility(View.VISIBLE);
                QuestionImagePicker.setVisibility(View.VISIBLE);
                QuestionTextView.setVisibility(View.INVISIBLE);
            } else {
                QuestionImageView.setVisibility(View.INVISIBLE);
                QuestionImagePicker.setVisibility(View.INVISIBLE);
                QuestionTextView.setVisibility(View.VISIBLE);
            }
        } else {
            if (b) {
                AnswerImageView.setVisibility(View.VISIBLE);
                AnswerImagePicker.setVisibility(View.VISIBLE);
                AnswerTextView.setVisibility(View.INVISIBLE);
            } else {
                AnswerImageView.setVisibility(View.INVISIBLE);
                AnswerImagePicker.setVisibility(View.INVISIBLE);
                AnswerTextView.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.QuestionImagePicker || view.getId() == R.id.AnswerImagePicker) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            if (view.getId() == R.id.AnswerImagePicker) {
                startActivityForResult(Intent.createChooser(intent, "Title"), Select_Image_Answer);
            } else {
                startActivityForResult(Intent.createChooser(intent, "Title"), Select_Image_Question);
            }
        } else if (view.getId() == R.id.SaveButton) {
            boolean questionSave = SaveTheQuestion();
            boolean answerSave = SaveTheAnswer();
            if (questionSave && answerSave) {
                ArrayList<Card> myCards = retrieveTheCards();
                myCards.add(GlobalCard);
                updateCards(myCards);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == 1) {
                Uri uri = data.getData();
                QuestionImageView.setImageURI(uri);
            } else {
                Uri uri = data.getData();
                AnswerImageView.setImageURI(uri);
            }
        }
    }

    public boolean SaveTheQuestion() {
        GlobalCard = new Card();
        Intent intent = getIntent();
        String topic = intent.getStringExtra("Topic");
        GlobalCard.setTopic(topic);
        if (QuestionImageView.getVisibility() == View.INVISIBLE) {
            if (QuestionTextView.getText().toString().equals("Question : ") || QuestionTextView.getText().toString().trim().isEmpty()) {
                QuestionTextView.setError("Please enter a question");
                return false;
            } else {
                GlobalCard.setQuestion(QuestionTextView.getText().toString());
                GlobalCard.setQuestionImage(null);
                return true;
            }
        } else {
            GlobalCard.setQuestion("");
            if (QuestionImageView.getDrawable() != null) {
                Bitmap bm = ((BitmapDrawable) QuestionImageView.getDrawable()).getBitmap();
                GlobalCard.setQuestionImage(bm);
                return true;
            } else {
                Drawable errorDrawable = getResources().getDrawable(R.drawable.spinner_border_error);
                QuestionImageView.setBackground(errorDrawable);
                return false;
            }
        }
    }

    public boolean SaveTheAnswer() {
        if (AnswerImageView.getVisibility() == View.INVISIBLE) {
            if (AnswerTextView.getText().toString().equals("Answer : ") || AnswerTextView.getText().toString().trim().isEmpty()) {
                AnswerTextView.setError("Please enter the answer");
                return false;
            } else {
                GlobalCard.setAnswer(AnswerTextView.getText().toString());
                GlobalCard.setAnswerImage(null);
                return true;
            }
        } else {
            GlobalCard.setAnswer("");
            if (AnswerImageView.getDrawable() != null) {
                Bitmap bm = ((BitmapDrawable) AnswerImageView.getDrawable()).getBitmap();
                GlobalCard.setAnswerImage(bm);
                return true;
            } else {
                Drawable errorDrawable = getResources().getDrawable(R.drawable.spinner_border_error);
                AnswerImageView.setBackground(errorDrawable);
                return false;
            }
        }
    }

    public ArrayList<Card> retrieveTheCards() {
        Gson gson = new Gson();
        String json = GlobalSharedPreferences.getString("cards", null);
        Type type = new TypeToken<ArrayList<Card>>() {
        }.getType();
        ArrayList<Card> cardsList;
        if (json != null) {
            cardsList = gson.fromJson(json, type);
        } else {
            cardsList = new ArrayList<Card>();
        }
        return cardsList;
    }

    public void updateCards(ArrayList<Card> myCards) {
        Gson gson = new Gson();
        String updatedJson = gson.toJson(myCards);
        editor.putString("cards", updatedJson);
        editor.apply();
    }
}
