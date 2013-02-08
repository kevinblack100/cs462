<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="customtags" tagdir="/WEB-INF/tags/ffds" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<customtags:pagetemplate>
	<jsp:attribute name="title">User Profile</jsp:attribute>
	<jsp:body>
		<h1>${username} Profile</h1>
		<p>Has foursquare authorization token?: 
			<c:choose>
				<c:when test="${hasFoursquareAuthToken}">
				Yes
				</c:when>
				<c:otherwise>
				No
				</c:otherwise>
			</c:choose>
		</p>
		<p>TODO: list the user's login information</p>
	</jsp:body>
</customtags:pagetemplate>