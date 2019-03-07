package ase.Stage4;

import ase.RestClient;

import java.math.BigInteger;

public class Stage4 extends RestClient {
    private static int STAGE_NUMBER = 4;

    public void solve(TaskResponse assignment, TaskSolution solution){
        double result = 0;
        int[] numbers = assignment.numbers;
        String operator = assignment.operator;

        switch (operator) {
            case "+":
                result = add(numbers);
                break;
            case "*":
                result = mul(numbers);
                break;
            case "-":
                result = sub(numbers);
                break;
        }

        solution.solution = result;
    }


    private int add(int[] numbers){
        int result = numbers[0];
        for (int i = 1; i < numbers.length; i++){
            result += numbers[i];
        }

        return result;
    }

    private int sub(int[] numbers){
        int result = numbers[0];
        for (int i = 1; i < numbers.length; i++){
            result -= numbers[i];
        }
        return result;
    }

    private double mul(int[] numbers){
        double result = numbers[0];
        for (int i = 1; i < numbers.length; i++){
            result *= numbers[i];
        }
        return result;
    }

    public void run(){
        for (int i = 1; i <= 40; i++){
            var assignment = this.getAssignment(STAGE_NUMBER,i, TaskResponse.class);
            TaskSolution response = new TaskSolution();
            solve(assignment, response);
            this.submitSolution(STAGE_NUMBER,i, response, TaskSolutionResponse.class);
        }
    }
}
