package com.codeittoday.dev_checkpoint.dto;

public class StepDto {

    private int stepNumber;
    private String name;
    private String status;
    private String description;

    private TestingStatus testingStatus; // only for testing step

    public int getStepNumber() {
        return stepNumber;
    }

    public void setStepNumber(int stepNumber) {
        this.stepNumber = stepNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TestingStatus getTestingStatus() {
        return testingStatus;
    }

    public void setTestingStatus(TestingStatus testingStatus) {
        this.testingStatus = testingStatus;
    }
}