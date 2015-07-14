package com.marcusilgner.ghcity;

import jetbrains.buildServer.issueTracker.IssueProviderType;
import jetbrains.buildServer.web.openapi.PluginDescriptor;
import org.jetbrains.annotations.NotNull;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Oleg Rybak (oleg.rybak@jetbrains.com)
 */
public class GithubIssueProviderType extends IssueProviderType {

  @NotNull
  private final String myConfigUrl;

  @NotNull
  private final String myPopupUrl;

  public GithubIssueProviderType(@NotNull final PluginDescriptor pluginDescriptor) {
    myConfigUrl = pluginDescriptor.getPluginResourcesPath("admin/editIssueProvider.jsp");
    myPopupUrl = pluginDescriptor.getPluginResourcesPath("popup-experimental.jsp");
  }

  @NotNull
  @Override
  public String getType() {
    return "GithubIssues";
  }

  @NotNull
  @Override
  public String getDisplayName() {
    return "GitHub Issues";
  }

  @NotNull
  @Override
  public String getEditParametersUrl() {
    return myConfigUrl;
  }

  @NotNull
  @Override
  public String getIssueDetailsUrl() {
    return myPopupUrl;
  }


}
