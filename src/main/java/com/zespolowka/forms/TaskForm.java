package com.zespolowka.forms;

import com.zespolowka.entity.createTest.ProgrammingLanguages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;
import java.util.TreeSet;

public class TaskForm {
    public static final int CLOSEDTASK = 0;
    public static final int OPENTASK = 1;
    public static final int PROGRAMMINGTASK = 2;
<<<<<<< HEAD
    public static final int SQLTASK = 3;
=======
>>>>>>> addf63146eadb4865c3e88fc9502c025b3871c1e
    private static final Logger logger = LoggerFactory.getLogger(TaskForm.class);

    private String question;

    private String answer;

    private int taskType;

<<<<<<< HEAD
    private Integer points = 1;

    private String preparations;
=======
    private int points;
>>>>>>> addf63146eadb4865c3e88fc9502c025b3871c1e

    private Set<String> languages = new TreeSet<>();

    private Set<ProgrammingTaskForm> programmingTaskForms;

    private Boolean caseSensitivity;

    private Boolean wrongReset = true;

    private Boolean countNotFull;

<<<<<<< HEAD
=======

>>>>>>> addf63146eadb4865c3e88fc9502c025b3871c1e
    public TaskForm() {
        programmingTaskForms = new TreeSet<>();
    }

    public TaskForm(int taskType) {
        this.taskType = taskType;
        programmingTaskForms = new TreeSet<>();
        if (taskType == 2) {
            programmingTaskForms.add(new ProgrammingTaskForm(ProgrammingLanguages.JAVA.toString()));
            programmingTaskForms.add(new ProgrammingTaskForm(ProgrammingLanguages.CPP.toString()));
            programmingTaskForms.add(new ProgrammingTaskForm(ProgrammingLanguages.PYTHON.toString()));
        }
<<<<<<< HEAD
=======
        logger.info("test1");
>>>>>>> addf63146eadb4865c3e88fc9502c025b3871c1e
    }

    public TaskForm(String question, int taskType, String answer) {
        this.question = question;
        this.taskType = taskType;
        this.answer = answer;
    }

    public TaskForm(String question, int taskType) {
        this.question = question;
        this.answer = "";
        this.taskType = taskType;
    }

    public TaskForm(String question, String answer, int taskType) {
        this.question = question;
        this.answer = answer;
        this.taskType = taskType;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
<<<<<<< HEAD
        logger.info("setQuestion");
=======
>>>>>>> addf63146eadb4865c3e88fc9502c025b3871c1e
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
<<<<<<< HEAD
        logger.info("setAnswer");
=======
>>>>>>> addf63146eadb4865c3e88fc9502c025b3871c1e
        this.answer = answer;
    }

    public Set<String> getLanguages() {
        return languages;
    }

    public void setLanguages(Set<String> languages) {
        logger.info("setLanguages");
<<<<<<< HEAD
        if (languages == null) {
            this.languages = new TreeSet<>();
        } else this.languages = languages;
        Set<ProgrammingTaskForm> programmingTaskFormSet = new TreeSet<>();
        if (this.languages.contains(ProgrammingLanguages.JAVA.toString())) {
=======
        if (languages == null) languages = new TreeSet<>();
        this.languages = languages;
        Set<ProgrammingTaskForm> programmingTaskFormSet = new TreeSet<>();
        if (languages.contains(ProgrammingLanguages.JAVA.toString())) {
>>>>>>> addf63146eadb4865c3e88fc9502c025b3871c1e
            programmingTaskFormSet.add(new ProgrammingTaskForm(ProgrammingLanguages.JAVA.toString(), true));
        } else {
            programmingTaskFormSet.add(new ProgrammingTaskForm(ProgrammingLanguages.JAVA.toString()));
        }
<<<<<<< HEAD
        if (this.languages.contains(ProgrammingLanguages.CPP.toString())) {
=======
        if (languages.contains(ProgrammingLanguages.CPP.toString())) {
>>>>>>> addf63146eadb4865c3e88fc9502c025b3871c1e
            programmingTaskFormSet.add(new ProgrammingTaskForm(ProgrammingLanguages.CPP.toString(), true));
        } else {
            programmingTaskFormSet.add(new ProgrammingTaskForm(ProgrammingLanguages.CPP.toString()));
        }
<<<<<<< HEAD
        if (this.languages.contains(ProgrammingLanguages.PYTHON.toString())) {
=======
        if (languages.contains(ProgrammingLanguages.PYTHON.toString())) {
>>>>>>> addf63146eadb4865c3e88fc9502c025b3871c1e
            programmingTaskFormSet.add(new ProgrammingTaskForm(ProgrammingLanguages.PYTHON.toString(), true));
        } else {
            programmingTaskFormSet.add(new ProgrammingTaskForm(ProgrammingLanguages.PYTHON.toString()));
        }
        setProgrammingTaskForms(programmingTaskFormSet);
    }

    public Set<ProgrammingTaskForm> getProgrammingTaskForms() {
<<<<<<< HEAD
        if (programmingTaskForms.size() == 0) {
            programmingTaskForms.add(new ProgrammingTaskForm(ProgrammingLanguages.JAVA.toString()));
            programmingTaskForms.add(new ProgrammingTaskForm(ProgrammingLanguages.CPP.toString()));
            programmingTaskForms.add(new ProgrammingTaskForm(ProgrammingLanguages.PYTHON.toString()));
        }
=======
>>>>>>> addf63146eadb4865c3e88fc9502c025b3871c1e
        return programmingTaskForms;
    }

    public void setProgrammingTaskForms(Set<ProgrammingTaskForm> programmingTaskForms) {
<<<<<<< HEAD
        logger.info("setProgrammingTaskForms");
        if (programmingTaskForms == null) {
            this.programmingTaskForms = new TreeSet<>();
        } else this.programmingTaskForms = programmingTaskForms;
=======
        this.programmingTaskForms = programmingTaskForms;
        logger.info("setProgrammingTaskForms");
>>>>>>> addf63146eadb4865c3e88fc9502c025b3871c1e
    }

    public int getTaskType() {
        return taskType;
    }

    public void setTaskType(int taskType) {
        logger.info("setTaskType");
        this.taskType = taskType;
    }

<<<<<<< HEAD
    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
=======
    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        logger.info("setPoints");
>>>>>>> addf63146eadb4865c3e88fc9502c025b3871c1e
        this.points = points;
    }

    public Boolean getCaseSensitivity() {
        return caseSensitivity;
    }

    public void setCaseSensitivity(Boolean caseSensitivity) {
<<<<<<< HEAD
=======
        logger.info("setCaseSensitivity");
>>>>>>> addf63146eadb4865c3e88fc9502c025b3871c1e
        this.caseSensitivity = caseSensitivity;
    }

    public Boolean getWrongReset() {
        return wrongReset;
    }

    public void setWrongReset(Boolean wrongReset) {
<<<<<<< HEAD
=======
        logger.info("setWrongReset");
>>>>>>> addf63146eadb4865c3e88fc9502c025b3871c1e
        this.wrongReset = wrongReset;
    }

    public Boolean getCountNotFull() {
        return countNotFull;
    }

    public void setCountNotFull(Boolean countNotFull) {
<<<<<<< HEAD
        this.countNotFull = countNotFull;
    }

    public String getPreparations() {
        return preparations;
    }

    public void setPreparations(String preparations) {
        this.preparations = preparations;
    }

=======
        logger.info("setCountNotFull");
        this.countNotFull = countNotFull;
    }

>>>>>>> addf63146eadb4865c3e88fc9502c025b3871c1e
    @Override
    public String toString() {
        return "TaskForm{" +
                "question='" + question + '\'' +
                ", answer='" + answer + '\'' +
                ", taskType=" + taskType +
                ", points=" + points +
                ", languages=" + languages +
<<<<<<< HEAD
             //   ", programmingTaskForms=" + programmingTaskForms +
=======
                ", programmingTaskForms=" + programmingTaskForms +
>>>>>>> addf63146eadb4865c3e88fc9502c025b3871c1e
                ", caseSensitivity=" + caseSensitivity +
                ", wrongReset=" + wrongReset +
                ", countNotFull=" + countNotFull +
                '}';
    }
<<<<<<< HEAD


=======
>>>>>>> addf63146eadb4865c3e88fc9502c025b3871c1e
}
