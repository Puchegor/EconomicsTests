package com.elmareos.economicstests;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.ArrayList;

public class Question implements Parcelable{
    private int idTopic;
    private int idQuestion;
    private String questionText;
    private ArrayList<Answer> answers;
    private String correctAnswer;

    public Question(int idTopic, int idQuestion, String questionText, ArrayList<Answer> answers, String correctAnswer){
        this.idTopic = idTopic;
        this.idQuestion = idQuestion;
        this.questionText = questionText;
        this.answers = answers;
        this.correctAnswer = correctAnswer;
    }

    protected Question(Parcel in) {
        idTopic = in.readInt();
        idQuestion = in.readInt();
        questionText = in.readString();
        answers = in.createTypedArrayList(Answer.CREATOR);
        correctAnswer = in.readString();
    }

    public static final Creator<Question> CREATOR = new Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };

    public int getIdTopic() {
        return idTopic;
    }

    public int getIdQuestion() {
        return idQuestion;
    }

    public String getQuestionText() {
        return questionText;
    }

    public ArrayList<Answer> getAnswers() {
        return answers;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    @NonNull
    @Override
    public String toString() {
        return getQuestionText();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idTopic);
        dest.writeInt(idQuestion);
        dest.writeString(questionText);
        dest.writeTypedList(answers);
        dest.writeString(correctAnswer);
    }
}
