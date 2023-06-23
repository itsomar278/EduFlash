package com.example.eduflash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class TopicPickActivity extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener {
    private Spinner TopicsSpinner;
    private Button NextButton;
    private EditText TopicTextView;
    private List<String> myList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_pick);

        TopicsSpinner = findViewById(R.id.TopicsSpinner);
        NextButton = findViewById(R.id.NextButton);
        TopicTextView = findViewById(R.id.Topic);

        TopicTextView.setOnClickListener(this);
        TopicTextView.setOnFocusChangeListener(this);
        NextButton.setOnClickListener(this);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        String json = sharedPreferences.getString("Topics", null);

        if (json != null) {
            myList = new Gson().fromJson(json, new TypeToken<List<String>>() {}.getType());
        } else {
            myList = new ArrayList<String>();
        }

        if (!myList.isEmpty()) {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
            adapter.add("NONE");
            adapter.addAll(myList);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            TopicsSpinner.setAdapter(adapter);
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.Topic) {
            view.requestFocus();
            if (TopicTextView.getText().toString().equals("Your Topic : ")) {
                TopicTextView.setText("");
            }
        } else if (view.getId() == R.id.NextButton) {
            if (TopicsSpinner.getSelectedItem() != null && !TopicsSpinner.getSelectedItem().toString().equals("NONE")) {
                String topic = TopicsSpinner.getSelectedItem().toString();
                Intent intent = new Intent(TopicPickActivity.this, Add_New_Activity.class);
                intent.putExtra("Topic", topic);
                startActivity(intent);
            } else if (TopicTextView.getText().toString().equals("Your Topic : ") || TopicTextView.getText().toString().trim().equals("")) {
                Drawable errorDrawable = getResources().getDrawable(R.drawable.spinner_border_error);
                TopicsSpinner.setBackground(errorDrawable);
            } else {
                String topic = TopicTextView.getText().toString();
                myList.add(topic);
                String json = new Gson().toJson(myList);
                SharedPreferences sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("Topics", json);
                editor.apply();
                Intent intent = new Intent(TopicPickActivity.this, Add_New_Activity.class);
                intent.putExtra("Topic", topic);
                startActivity(intent);
            }
        }
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        if (view.hasFocus() && TopicTextView.getText().toString().equals("Your Topic :")) {
            TopicTextView.setText("");
        }
    }
}
