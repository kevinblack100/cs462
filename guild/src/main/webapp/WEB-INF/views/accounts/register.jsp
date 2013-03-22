<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="customtags" tagdir="/WEB-INF/tags" %>

<customtags:pagetemplate>
	<jsp:attribute name="title">Register</jsp:attribute>
	<jsp:body>
		<h1>Register</h1>
		<form method="POST" action="${contextPaths.dynamicPath}/secure/accounts/register">
			<fieldset>
				<label for="username">Username:</label>
				<input type="text" id="username" name="username" size="20" />
				<br>
				<!-- Disabling password usage for now
				<label for="password">Password:</label>
				<input id="password" name="password" type="password" size="20" />
				<br>
				-->
				<br>
				<input type="submit" id="submit" name="submit" value="Register" />
			</fieldset>
		</form>
	</jsp:body>
</customtags:pagetemplate>