package com.zespolowka.entity.createTest;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
public class TaskProgramming extends Task {

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, targetEntity = TaskProgrammingDetail.class, fetch = FetchType.EAGER)
    private Set<TaskProgrammingDetail> programmingDetailSet = new HashSet<>();

    public TaskProgramming() {
    }

    public TaskProgramming(final String question, final Float max_points) {
        super(question, max_points);
    }

    public Set<TaskProgrammingDetail> getProgrammingDetailSet() {
        return programmingDetailSet;
    }

    public void setProgrammingDetailSet(Set<TaskProgrammingDetail> programmingDetailSet) {
        this.programmingDetailSet = programmingDetailSet;
    }

    public void addTaskkProgrammingDetail(TaskProgrammingDetail taskProgrammingDetail) {
        this.programmingDetailSet.add(taskProgrammingDetail);
    }

    @Override
    public String toString() {
        return "TaskProgramming{" +
                "Task='" + super.toString() + '\'' +
                "programmingDetailSet=" + programmingDetailSet +
                '}';
    }
}
