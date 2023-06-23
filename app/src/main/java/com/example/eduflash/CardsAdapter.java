package com.example.eduflash;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CardsAdapter extends RecyclerView.Adapter<CardsAdapter.ViewHolder>{

private ArrayList<Card> cards ;
public CardsAdapter (ArrayList<Card> cards)
{
    this.cards = cards;
}


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView v = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview, parent, false);


        return new ViewHolder(v);
}

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CardView cardView = holder.cardView;
        ImageView QuestionImageView = cardView.findViewById(R.id.QuestionImage);
        ImageView AnswerImageView = cardView.findViewById(R.id.AnswerImage);
        TextView QuestionTextView = cardView.findViewById(R.id.QuestionText);
        TextView AnswerTextView = cardView.findViewById(R.id.AnswerText);

        Card currentCard = cards.get(holder.getAdapterPosition());

        if (currentCard.getQuestionImage() != null) {
            QuestionImageView.setImageBitmap(currentCard.getQuestionImage());
            QuestionTextView.setVisibility(View.INVISIBLE);
        } else {
            QuestionTextView.setText(currentCard.getQuestion());
            QuestionImageView.setVisibility(View.INVISIBLE);
        }

        if (currentCard.getAnswerImage() != null) {
            AnswerImageView.setImageBitmap(currentCard.getAnswerImage());
            AnswerTextView.setVisibility(View.INVISIBLE);
        } else {
            AnswerTextView.setText(currentCard.getAnswer());
            AnswerImageView.setVisibility(View.INVISIBLE);
        }

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int clickedPosition = holder.getAdapterPosition();
                if (clickedPosition != RecyclerView.NO_POSITION) {
                    Card clickedCard = cards.get(clickedPosition);
                    Log.d("CardsAdapter", "Clicked Answer: " + clickedCard.getAnswer());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private CardView cardView;
        public ViewHolder(CardView cardView){
            super(cardView);
            this.cardView = cardView;
        }

    }
}
