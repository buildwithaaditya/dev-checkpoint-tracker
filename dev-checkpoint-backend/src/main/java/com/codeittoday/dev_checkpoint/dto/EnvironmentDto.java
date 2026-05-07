package com.codeittoday.dev_checkpoint.dto;

import java.util.List;

public class EnvironmentDto {

    private String environmentName;
    private List<StepDto> steps;

    public String getEnvironmentName() {
        return environmentName;
    }

    public void setEnvironmentName(String environmentName) {
        this.environmentName = environmentName;
    }

    public List<StepDto> getSteps() {
        return steps;
    }

    public void setSteps(List<StepDto> steps) {
        this.steps = steps;
    }
}
