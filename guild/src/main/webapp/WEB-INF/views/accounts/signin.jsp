<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="customtags" tagdir="/WEB-INF/tags" %>

<customtags:pagetemplate>
	<jsp:attribute name="title">Sign In</jsp:attribute>
	<jsp:body>
		<h1>Sign In</h1>
		<form method="POST" action="./authenticate">
			<fieldset>
				<label for="username">Username:</label>
				<input id="username" name="username" type="text" size="20" />
				<br />
				<!-- Disabling non-blank password usage for now
				<label for="password">Password:</label>
				<input id="password" name="password" type="password" size="20" />
				<br />
				-->
				<input type="hidden" id="password" name="password" value="${commonApplicationConstants.defaultPassword}" />
				<input id="submit" name="submit" type="submit" value="Sign In" />
			</fieldset>
		</form>
	</jsp:body>
</customtags:pagetemplate>
