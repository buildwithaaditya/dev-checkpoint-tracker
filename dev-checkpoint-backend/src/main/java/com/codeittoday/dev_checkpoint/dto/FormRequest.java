package com.codeittoday.dev_checkpoint.dto;

import java.util.List;

public class FormRequest {

    private List<EnvironmentDto> environments;

    public List<EnvironmentDto> getEnvironments() {
        return environments;
    }

    public void setEnvironments(List<EnvironmentDto> environments) {
        this.environments = environments;
    }
}