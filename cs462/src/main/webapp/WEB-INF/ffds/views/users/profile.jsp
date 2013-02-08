<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="customtags" tagdir="/WEB-INF/tags/ffds" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<customtags:pagetemplate>
	<jsp:attribute name="header">
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/site/ffds/users/profile.css" />
	
		<script type="text/javascript" src="${pageContext.request.contextPath}/thirdparty/jquery-1.9.1.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/thirdparty/handlebars-1.0.rc.2.js"></script>
		
		<script type="text/javascript" src="${pageContext.request.contextPath}/site/ffds/users/profile.js"></script>
		
		<script type="text/x-handlebars-template" id="checkin-list-template">
			<div class="checkin-list">
				<h3>Latest Foursquare Checkin Details</h3>
				{{#each items}}
				<div class="checkin-entry">
					When: <span class="time">{{secondsToDate createdAt}}</span>
					<br />
					<br />
					Where: <span class="venue-name">{{venue.name}}</span>
					<br />
					<span class="venue-city">{{venue.location.city}}</span>, 
					<span class="venue-state">{{venue.location.state}}</span>,
					<span class="venue-country">{{venue.location.country}}</span>
					<br />
					<br />
					How: <span class="checkin-means">{{source.name}}</span>
				</div>
				<br />
				{{/each}}
			</div>
		</script>
	</jsp:attribute>
	<jsp:attribute name="title">User Profile</jsp:attribute>
	<jsp:body>
		<h1>${username} Profile</h1>
		<p> 
			<c:choose>
				<c:when test="${hasFoursquareAuthToken}">
					<div id="checkins-data" class="hidden">${checkins}</div>
					<div id="checkins-detail">
					</div>
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