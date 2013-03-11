<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="customtags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<customtags:pagetemplate>
	<jsp:attribute name="title">Dispatch Event</jsp:attribute>
	<jsp:body>
		<h1>Dispatch Events</h1>
		<form method="post" action="${contextPaths.dynamicPath}/event/dispatch">
			<fieldset>
				<div id="form-fields">
					<dl>
						<dd><label for="_domain">Domain:</label></dd>
						<dt><input type="text" id="event-domain" name="_domain" size=10 /></dt>
					</dl>
					<dl>
						<dd><label for="_name">Name:</label></dd>
						<dt><input type="text" id="event-name" name="_name" size=10 /></dt>
					</dl>
					<dl>
						<dd><label for="attrib">Attribute 1:</label></dd>
						<dt><input type="text" id="attrib-1" name="attrib" size=10 /></dt>
					</dl>
					<dl>
						<dd><label for="attrib">Attribute 1:</label></dd>
						<dt><input type="text" id="attrib-2" name="attrib" size=10 /></dt>
					</dl>
				</div>
				<input type="submit" id="submit" name="submit" value="Dispatch" />
			</fieldset>
		</form>
	</jsp:body>
</customtags:pagetemplate>