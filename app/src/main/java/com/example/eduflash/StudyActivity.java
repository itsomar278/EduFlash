package com.example.eduflash;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class StudyActivity extends AppCompatActivity {
    private ArrayList<Card> cards ;
    private SharedPreferences GlobalSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study);
        RecyclerView recycler = findViewById(R.id.Cards_Recycler);
        GlobalSharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        cards = retrieveTheCards();
        recycler.setLayoutManager(new LinearLayoutManager(this));
        CardsAdapter adapter = new CardsAdapter(cards);
        recycler.setAdapter(adapter);
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
}