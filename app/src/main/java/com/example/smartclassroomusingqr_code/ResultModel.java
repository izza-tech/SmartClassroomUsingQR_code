package com.example.smartclassroomusingqr_code;

public class ResultModel {
    String Name;
    String Marks;
    String TotalQuestion;

    public ResultModel() {
    }

    public ResultModel(String name, String marks, String totalQuestion) {
        Name = name;
        Marks = marks;
        TotalQuestion = totalQuestion;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getMarks() {
        return Marks;
    }

    public void setMarks(String marks) {
        Marks = marks;
    }

    public String getTotalQuestion() {
        return TotalQuestion;
    }

    public void setTotalQuestion(String totalQuestion) {
        TotalQuestion = totalQuestion;
    }
}
