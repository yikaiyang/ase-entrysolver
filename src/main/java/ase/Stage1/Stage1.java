package ase.Stage1;

import ase.RestClient;

public class Stage1 extends RestClient {
    private static int STAGE_NUMBER = 1;

    public void solve(TaskResponse assignment, TaskSolution solution){
        solution.solution = -assignment.number;
    }

    public void run(){
        for (int i = 1; i <= 10; i++){
            var assignment = this.getAssignment(STAGE_NUMBER,i, TaskResponse.class);
            TaskSolution response = new TaskSolution();
            solve(assignment, response);
            this.submitSolution(STAGE_NUMBER,i, response, TaskSolutionResponse.class);
        }
    }
}
