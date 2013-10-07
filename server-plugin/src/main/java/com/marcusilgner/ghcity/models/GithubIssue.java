package com.marcusilgner.ghcity.models;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class GithubIssue {
    public String title;
    public String body;
    public String state;

    public boolean isResolved() {
        return state.equalsIgnoreCase("closed");
    }
}
