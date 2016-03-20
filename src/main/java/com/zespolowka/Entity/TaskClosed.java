package com.zespolowka.Entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Peps on 2016-03-09.
 */
public class TaskClosed extends Task{


    List<String> possible_answers = new ArrayList<String>();
    List<Boolean> checked_answers=new ArrayList<Boolean>();
    List<Boolean> true_answers=new ArrayList<Boolean>();

    public TaskClosed(String task_group, String question, int max_points, List<String> possible_answers, List<Boolean> true_answers) {
        super(task_group, question, max_points);
        this.possible_answers = possible_answers;
        this.true_answers = true_answers;
    }

    public List<String> getPossible_answers() {
        return possible_answers;
    }

    public void setPossible_answers(List<String> possible_answers) {
        this.possible_answers = possible_answers;
    }


    public List<Boolean> getChecked_answers() {
        return checked_answers;
    }

    public void setChecked_answers(List<Boolean> checked_answers) {
        this.checked_answers = checked_answers;
    }

    public List<Boolean> getTrue_answers() {
        return true_answers;
    }

    public void setTrue_answers(List<Boolean> true_answers) {
        this.true_answers = true_answers;
    }

    public void check_answers(){
        int pkt=0;
        for(int i=0; i<true_answers.size(); i++){
            if(checked_answers.get(i)==true_answers.get(i)){
               pkt++;
            }
        }
        setPoints(pkt);
    }
}
