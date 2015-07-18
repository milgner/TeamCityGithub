package com.marcusilgner.ghcity;

import jetbrains.buildServer.issueTracker.AbstractIssueProviderFactory;
import jetbrains.buildServer.issueTracker.IssueFetcher;
import jetbrains.buildServer.issueTracker.IssueProvider;
import jetbrains.buildServer.issueTracker.IssueProviderType;
import org.jetbrains.annotations.NotNull;

/**
 * User: Marcus Ilgner <mail@marcusilgner.com>
 * Date: 30/01/11
 */
public class GithubIssueProviderFactory
        extends AbstractIssueProviderFactory {
    public GithubIssueProviderFactory(@NotNull final IssueProviderType type, @NotNull IssueFetcher fetcher) {
        super(type, fetcher);
    }

    @NotNull
    public IssueProvider createProvider() {
        return new GithubIssueProvider(myType, myFetcher);
    }
}
