<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="customtags" tagdir="/WEB-INF/tags/ffds" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<customtags:pagetemplate>
	<jsp:attribute name="header">
		<script type="text/javascript" src="${pageContext.request.contextPath}/thirdparty/jquery-1.9.1.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/site/ffds/users/profile.js"></script>
	</jsp:attribute>
	<jsp:attribute name="title">User Profile</jsp:attribute>
	<jsp:body>
		<h1>${username} Profile</h1>
		<p> 
			<c:choose>
				<c:when test="${hasFoursquareAuthToken}">
					<div id="checkin-data">${checkins}</div>
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