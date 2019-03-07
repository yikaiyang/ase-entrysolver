package ase;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class RestClient {
    private final String TASK_URL = "http://localhost:8080/assignment/stage/%d/testcase/%d";
    private final HttpClient HTTP_CLIENT = HttpClient.newBuilder().build();

    public <T> T parseResponse(String response, Class<T> responseClassType){
        Gson gson = new Gson();
        return gson.fromJson(response, responseClassType);
    }


    public <T extends ITaskResponse> T getAssignment(int stage, int assignment, Class<T> assignmentReponseClass){
        System.out.println("=======================================");
        System.out.println("getAssignment: Stage: " + stage + " Assignment: " + assignment);

        String url = String.format(TASK_URL, stage, assignment);

        HttpRequest GET_TASK_REQUEST = HttpRequest.newBuilder().GET().uri(
                URI.create(url)
        ).build();

        System.out.println("GET URL: " + GET_TASK_REQUEST.toString());

        T taskResponse = null;

        try {
            HttpResponse<String> response = HTTP_CLIENT.send(GET_TASK_REQUEST, HttpResponse.BodyHandlers.ofString());

            int statusCode = response.statusCode();
            taskResponse = parseResponse(response.body(), assignmentReponseClass);

             if (statusCode == 404){
                 System.out.println("ERROR: STATUSCODE 404: Assignment is not available: Stage:" + stage + " Assignment:" + assignment );
             }

             if (statusCode == 200 || statusCode == 202){
                 System.out.println(response.body());
             }

        } catch (IOException e) {
            System.out.println("ERROR: IOEXCEPTION: " + e.getMessage());
        } catch (InterruptedException e) {
            System.out.println("ERROR: INTERRUPT: " + e.getMessage());
        }

        return taskResponse;
    }

    public <T extends AbstractTaskSolutionResponse> T
        submitSolution
            (int stage,
             int assignment,
             ITaskSolution solution,
             Class<T> taskSolutionResponseClass)
    {
        System.out.println("--------");
        System.out.println("submitSolution: stage:" + stage + " assignment:" + assignment);

        Gson gson = new Gson();
        String json = gson.toJson(solution);

        String url = String.format(TASK_URL, stage, assignment);

        HttpRequest POST_SOLUTION_REQUEST = HttpRequest
                .newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(json)).uri(
                URI.create(url)
        ).build();

        System.out.println("POST URL: " + POST_SOLUTION_REQUEST.toString());
        System.out.println("Content:" + json);

        T solutionResponse = null;

        try {
            HttpResponse<String> response = HTTP_CLIENT.send(POST_SOLUTION_REQUEST, HttpResponse.BodyHandlers.ofString());

            int statusCode = response.statusCode();

            solutionResponse = parseResponse(response.body(), taskSolutionResponseClass);

            if (statusCode == 404){
                System.out.println("ERROR: STATUSCODE 404: getAssignment: Assignment is not available: Stage:" + stage + "Assignment " + assignment );
            }

            if (statusCode == 200 || statusCode == 202){
                System.out.println("SUCCESS: " + solutionResponse.message);
                System.out.println("NEXT TASK: " + solutionResponse.linkToNextTask);
            }

            if (statusCode == 417){
                System.out.println("ERROR: 417 EXPECTATION_FAILED: " + solutionResponse.message);
            } else if (statusCode == 400){
                System.out.println("ERROR: 400 BAD_REQUEST: " + solutionResponse.message);
            } else if (statusCode == 500){
                System.out.println("ERROR: 500 SERVER_ERROR: " + solutionResponse.message);
            }

        } catch (IOException e) {
            System.out.println("ERROR: IOEXCEPTION: " + e.getMessage());
        } catch (InterruptedException e) {
            System.out.println("ERROR: INTERRUPT: " + e.getMessage());
        }

        System.out.println("=======================================");

        return solutionResponse;
    }

}
