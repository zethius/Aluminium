package com.zespolowka.entity.solutionTest;


public class TaskTypeChecker {
<<<<<<< HEAD
    public TaskTypeChecker() {
    }

    public static boolean isTaskClosedSolution(Object object) {
        return object instanceof TaskClosedSolution;
    }

    public static boolean isTaskOpenSolution(Object object) {
        return object instanceof TaskOpenSolution;
    }

    public static boolean isTaskProgrammingSolution(Object object) {
        return object instanceof TaskProgrammingSolution;
    }
    public static boolean isTaskSqlSolution(Object object) {
        return object instanceof TaskSqlSolution;
    }
=======
    public boolean isTaskClosedSolution(Object object) {
        return object instanceof TaskClosedSolution;
    }

    public boolean isTaskOpenSolution(Object object) {
        return object instanceof TaskOpenSolution;
    }

    public boolean isTaskProgrammingSolution(Object object) {
        return object instanceof TaskProgrammingSolution;
    }
>>>>>>> addf63146eadb4865c3e88fc9502c025b3871c1e
}
