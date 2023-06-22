package com.example.eduflash;
import android.graphics.Bitmap;
public class Card {

    public Card() {

    }
    public Card(String question, String answer, Bitmap questionImage, Bitmap answerImage ,String topic) {
        Question = question;
        Answer = answer;
        QuestionImage = questionImage;
        AnswerImage = answerImage;
        Topic = topic;
    }
    private String Topic = "";
    private String Question = "";
    private String Answer = "";
    private Bitmap  QuestionImage = null;
    private Bitmap  AnswerImage = null;

    public void setTopic(String topic) {
        Topic = topic;
    }

    public String getTopic() {
        return Topic;
    }

    public void setQuestion(String question) {
        Question = question;
    }


    public void setAnswer(String answer) {
        Answer = answer;
    }

    public void setQuestionImage(Bitmap questionImage) {
        QuestionImage = questionImage;
    }

    public void setAnswerImage(Bitmap answerImage) {
        AnswerImage = answerImage;
    }
    public String getQuestion() {
        return Question;
    }

    public String getAnswer() {
        return Answer;
    }

    public Bitmap getQuestionImage() {
        return QuestionImage;
    }

    public Bitmap getAnswerImage() {
        return AnswerImage;
    }

}
