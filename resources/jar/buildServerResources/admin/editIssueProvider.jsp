<%@ include file="/include.jsp"%>
<%@ taglib prefix="props" tagdir="/WEB-INF/tags/props" %>

<%@ include file="/include.jsp"%>
<%@ taglib prefix="props" tagdir="/WEB-INF/tags/props" %>

<div>
  <table class="editProviderTable">
    <c:if test="${showType}">
      <tr>
        <th><label class="shortLabel">Connection Type:</label></th>
        <td>Github</td>
      </tr>
    </c:if>
    <tr>
      <th><label for="name" class="shortLabel">Display Name: <l:star/></label></th>
      <td>
        <props:textProperty name="name" maxlength="100" style="width: 16em;"/>
        <span id="error_name" class="error"></span>
      </td>
    </tr>
    <tr>
      <th><label for="repository" class="shortLabel">Repository: <l:star/></label></th>
      <td>
        <props:textProperty name="repository" maxlength="100" style="width: 16em;"/>
        <span id="error_repository" class="error"></span>
      </td>
    </tr>
    <tr>
      <th><label for="username" class="shortLabel">Username:</label></th>
      <td>
        <props:textProperty name="username" maxlength="100" style="width: 16em;"/>
        <span id="error_username" class="error"></span>
      </td>
    </tr>
    <tr>
      <th><label for="secure:password" class="shortLabel">Password:</label></th>
      <td>
        <props:passwordProperty name="secure:password" maxlength="100" style="width: 16em;"/>
        <span id="error_secure:password" class="error"></span>
      </td>
    </tr>
    <tr>
       <th><label for="pattern" class="shortLabel">Ticket Pattern: <l:star/></label></th>
       <td>
         <props:textProperty name="pattern" maxlength="100" style="width: 16em;"/>
         <span id="error_idPrefix" class="error"></span>
         <span class="fieldExplanation">Use general regexp, e.g. #(\d+)<bs:help file="Issue+Tracker+Tab"/></span>
        </td>
    </tr>
  </table>
</div>
