<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="customtags" tagdir="/WEB-INF/tags/ffds" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<customtags:pagetemplate>
	<jsp:attribute name="title">User Profile</jsp:attribute>
	<jsp:body>
		<h1>${username} Profile</h1>
		<p> 
			<c:choose>
				<c:when test="${hasFoursquareAuthToken}">
				TODO display user check in data depending on logged in status
				</c:when>
				<c:otherwise>
					<c:choose>
						<c:when test="${userLoggedIn}">
						<a href="${pageContext.request.contextPath}/ffds/oauth/v2/authorize/${username}/foursquare">
							Authorize FFDS to use your Foursquare Account
						</a>
						</c:when>
						<c:otherwise>
						Has not Authorized FFDS to use Foursquare Account
						</c:otherwise>
					</c:choose>
				</c:otherwise>
			</c:choose>
		</p>
	</jsp:body>
</customtags:pagetemplate>