package com.marcusilgner.ghcity;

import jetbrains.buildServer.issueTracker.AbstractIssueFetcher;
import jetbrains.buildServer.issueTracker.IssueData;
import jetbrains.buildServer.util.FileUtil;
import jetbrains.buildServer.util.cache.EhCacheUtil;
import org.apache.commons.httpclient.Credentials;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Fetches issues from a Github issue tracker
 * User: Marcus Ilgner <mail@marcusilgner.com>
 * Date: 30/01/11
 */
public class GithubIssueFetcher
        extends AbstractIssueFetcher {

    private final static Log LOGGER = LogFactory.getLog(GithubIssueFetcher.class);

    private final static String HOST_PATTERN = "^(\\w+)/(\\w+)$";
    private Pattern myPattern;

    public void setPattern(final Pattern _myPattern) {
        myPattern = _myPattern;
    }

    public class GithubFetchFunction implements FetchFunction {
        private String host;
        private String id;
        private Credentials credentials;

        public GithubFetchFunction(final String _host, final String _id, final Credentials _credentials) {
            host = removeTrailingSlash(_host);
            if (!host.matches(HOST_PATTERN)) {
                throw new IllegalArgumentException(String.format("Host does not match username/repository: %s", host));
            }
            id = _id;
            credentials = _credentials;
        }

        @NotNull
        public IssueData fetch() {
           String url = getApiUrl(host, id);
           LOGGER.debug(String.format("Fetching issue data from %s", url));
           try {
               InputStream xml = fetchHttpFile(url, credentials);
               IssueData result = parseIssue(xml);
               LOGGER.debug(result.toString());
               return result;
           }   catch (IOException e) {
               LOGGER.fatal(e);
               throw new RuntimeException("Error fetching issue data", e);
           }
        }

        private IssueData parseIssue(final InputStream _xml) {
            try {
                Element issue = FileUtil.parseDocument(_xml, false);
                String summary = getChildContent(issue, "title");
                String state = getChildContent(issue, "state");
                String url = getUrl(host, id);
                boolean resolved = state.equalsIgnoreCase("closed");
                IssueData result = new IssueData(id, summary, state, url, resolved);
                return result;
            } catch (JDOMException e) {
                throw new RuntimeException(String.format("Error parsing XML for issue '%s' on '%s'.", id, host));
            } catch (IOException e) {
                throw new RuntimeException(String.format("Error reading XML for issue '%s' on '%s'.", id, host));
            }
        }

        private String getApiUrl(@NotNull final String _host, @NotNull final String _id) {
            Matcher matcher = myPattern.matcher(_id);
            String realId = _id;
            if (matcher.find()) {
                realId = matcher.group(1);
            }
            return String.format("http://github.com/api/v2/xml/issues/show/%s/%s", _host, realId);
        }
    }

    public GithubIssueFetcher(@org.jetbrains.annotations.NotNull EhCacheUtil cacheUtil) {
        super(cacheUtil);
    }

    public IssueData getIssue(@NotNull final String _host, @NotNull final String _id, @Nullable final org.apache.commons.httpclient.Credentials _credentials)
            throws Exception {
        String url = getUrl(_host, _id);
        return getFromCacheOrFetch(url, new GithubFetchFunction(_host, _id, _credentials));
    }

    private String removeTrailingSlash(String _host) {
        int lastSlash = _host.lastIndexOf("/");
        if (lastSlash+1 == _host.length()) {
            return _host.substring(0, lastSlash);
        }
        return _host;
    }

    public String getUrl(@NotNull final String _host, @NotNull final String _id) {
        LOGGER.debug(String.format("Getting URL for issue %s, using pattern %s to match", _id, myPattern.toString()));
        String realId = _id;
        Matcher matcher = myPattern.matcher(_id);
        if (matcher.find()) {
            realId = matcher.group(1);
        }
        String url = String.format("http://github.com/%s/issues/%s", removeTrailingSlash(_host), realId);
        LOGGER.debug(String.format("URL is %s", url));
        return url;
    }

}