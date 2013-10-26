package com.marcusilgner.ghcity;

import jetbrains.buildServer.issueTracker.AbstractIssueProvider;
import jetbrains.buildServer.issueTracker.IssueFetcher;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * An issue provider for Github issue tracker
 * User: Marcus Ilgner <mail@marcusilgner.com>
 * Date: 30/01/11
 */
public class GithubIssueProvider
        extends AbstractIssueProvider {

    public GithubIssueProvider(@org.jetbrains.annotations.NotNull IssueFetcher fetcher) {
        super("GithubIssues", fetcher);
    }

    @Override
    public void setProperties(@NotNull final Map<String, String> map) {
        super.setProperties(map);
        myHost = map.get("repository");
    }

    @NotNull
    @Override
    protected Pattern compilePattern(@NotNull final Map<String, String> properties) {
        Pattern result = super.compilePattern(properties);
        if (myFetcher instanceof GithubIssueFetcher) {
            ((GithubIssueFetcher)myFetcher).setPattern(result);
        }
        return result;
    }

    @Override
    protected String extractId(@NotNull String match) {
        Matcher matcher = myPattern.matcher(match);
        matcher.find();
        if (matcher.groupCount() >= 1) {
            return matcher.group(1);
        } else {
            return super.extractId(match);
        }
    }
}
