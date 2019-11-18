package com.example.quizz.model;

import java.util.List;

public class quiz {

    private  String question;
    private List<String> answers;
    private int correctIndex;

    public quiz(String question, List<String> answers, int correctindex) {
        this.question = question;
        this.answers = answers;
        this.correctIndex = correctindex;
    }

    public quiz(){

}

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }

    public int getCorrectIndex() {
        return correctIndex;
    }

    public void setCorrectIndex(int correctIndex) {
        this.correctIndex = correctIndex;
    }
}
