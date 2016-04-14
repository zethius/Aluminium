package com.zespolowka.entity.solutionTest;


public class TaskTypeChecker {
    public boolean isTaskClosedSolution(Object object) {
        return object instanceof TaskClosedSolution;
    }

    public boolean isTaskOpenSolution(Object object) {
        return object instanceof TaskOpenSolution;
    }

    public boolean isTaskProgrammingSolution(Object object) {
        return object instanceof TaskProgrammingSolution;
    }
}
