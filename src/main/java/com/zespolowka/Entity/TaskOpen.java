package com.zespolowka.Entity;

/**
 * Created by Peps on 2016-03-09.
 */
public class TaskOpen extends Task {
    String answer;

    public TaskOpen(String task_group, String question, int max_points, String answer) {
        super(task_group, question, max_points);
        this.answer = answer;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
