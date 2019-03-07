package ase.Stage2;

import ase.RestClient;

public class Stage2 extends RestClient {
    private static int STAGE_NUMBER = 2;

    public void solve(TaskResponse assignment, TaskSolution solution){

        solution.solution = assignment.a + assignment.b;
    }

    public void run(){
        for (int i = 1; i <= 20; i++){
            var assignment = this.getAssignment(STAGE_NUMBER,i, TaskResponse.class);
            TaskSolution response = new TaskSolution();
            solve(assignment, response);
            this.submitSolution(STAGE_NUMBER,i, response, TaskSolutionResponse.class);
        }
    }
}
