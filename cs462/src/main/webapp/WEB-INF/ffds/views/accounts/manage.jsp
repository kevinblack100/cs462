<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="customtags" tagdir="/WEB-INF/tags/ffds" %>

<customtags:pagetemplate>
	<jsp:attribute name="title">Manage Account</jsp:attribute>
	<jsp:body>
		<h1>Manage Account</h1>
		<form method="POST" action="${pageContext.request.contextPath}/ffds/secure/accounts/manage">
			<fieldset>
				<span>How would you like to use the system?</span>
				<br />
				<input type="checkbox" id="driver-indicator" name="driver-indicator" ${isDriver ? "checked" : "" } value="true" />
				<label for="driver-indicator">Flower delivery driver</label>
				<br />
				<input type="submit" id="submit" name="submit" value="Save" />
			</fieldset>
		</form>
	</jsp:body>
</customtags:pagetemplate>