package com.marcusilgner.ghcity;

import jetbrains.buildServer.issueTracker.AbstractIssueProviderFactory;
import jetbrains.buildServer.issueTracker.IssueFetcher;
import jetbrains.buildServer.issueTracker.IssueProvider;
import org.jetbrains.annotations.NotNull;

/**
 * User: Marcus Ilgner <mail@marcusilgner.com>
 * Date: 30/01/11
 */
public class GithubIssueProviderFactory
        extends AbstractIssueProviderFactory {
    protected GithubIssueProviderFactory(@NotNull IssueFetcher fetcher) {
        super(fetcher, "github");
    }

    @NotNull
    public IssueProvider createProvider() {
        return new GithubIssueProvider(myFetcher);
    }
}
