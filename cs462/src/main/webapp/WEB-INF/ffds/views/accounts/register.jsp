<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="customtags" tagdir="/WEB-INF/tags/ffds" %>

<customtags:pagetemplate>
	<jsp:attribute name="title">Register</jsp:attribute>
	<jsp:body>
		<form method="POST" action="${pageContext.request.contextPath}/ffds/secure/accounts/register/execute">
			<fieldset>
				<label for="username">Username:</label>
				<input id="username" name="username" type="text" size="20" />
				<br />
				<label for="password">Password:</label>
				<input id="password" name="password" type="password" size="20" />
				<br />
				<input id="submit" name="submit" type="submit" value="Register" />
			</fieldset>
		</form>
	</jsp:body>
</customtags:pagetemplate>