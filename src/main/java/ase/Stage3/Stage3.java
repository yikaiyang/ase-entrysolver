package ase.Stage3;

import ase.RestClient;

public class Stage3 extends RestClient {
    private static int STAGE_NUMBER = 3;

    public void solve(TaskResponse assignment, TaskSolution solution){
        int[] numbers = assignment.numbers;
        int result = 0;

        for(int i = 0; i < numbers.length; i++){
            result += numbers[i];
        }

        solution.solution = result;
    }

    public void run(){
        for (int i = 1; i <= 30; i++){
            var assignment = this.getAssignment(STAGE_NUMBER,i, TaskResponse.class);
            TaskSolution response = new TaskSolution();
            solve(assignment, response);
            this.submitSolution(STAGE_NUMBER,i, response, TaskSolutionResponse.class);
        }
    }
}
