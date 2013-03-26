<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="customtags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<customtags:pagetemplate>
	<jsp:attribute name="header">
		<link rel="stylesheet" type="text/css" href="${contextPaths.staticPath}/site/users/profile.css" />
	
		<script type="text/javascript" src="${contextPaths.staticPath}/thirdparty/jquery-1.9.1.js"></script>
		<script type="text/javascript" src="${contextPaths.staticPath}/thirdparty/handlebars-1.0.rc.2.js"></script>
		
		<script type="text/javascript" src="${contextPaths.staticPath}/site/users/profile.js"></script>
		
		<script type="text/x-handlebars-template" id="checkin-list-template">
			<div class="checkin-list">
				<h3>Latest Foursquare Checkin Details</h3>
				{{#each items}}
				<div class="checkin-entry {{entryClass}}">
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
		<div id="user-detials"> 
			<div id="channel-details">
				<h3>Guild Channel Details</h3>
	
				<dl>
					<dt>ID:</dt>
					<dd>${channel.id}</dd>
				</dl>
				<dl>
					<dt>Send ESL:</dt>
					<dd>
						<c:choose>
							<c:when test="${channel.sendESL ne null}">
								${channel.sendESL}
							</c:when>
							<c:otherwise>
								Not specified
							</c:otherwise>
						</c:choose>
					</dd>
				</dl>
				<dl>
					<dt>Receive ESL:</dt>
					<dd>${channel.receiveESL}<dd>
				</dl>
			</div> <!-- channel-details -->
			
			<br>
			
			<div id="foursquare-checkin-details">
				<h3>Foursquare Checkin Details</h3>
				<c:choose>
					<c:when test="${hasFoursquareAuthToken}">
						<div id="checkins-data" class="hidden">${checkins}</div>
						<div id="checkins-detail">
						</div>
					</c:when>
					<c:otherwise>
						<c:choose>
							<c:when test="${userLoggedIn}">
							<a href="${contextPaths.dynamicPath}/oauth/v2/authorize/${username}/foursquare">
								Authorize FFDS to use your Foursquare Account
							</a>
							</c:when>
							<c:otherwise>
							Has not Authorized FFDS to use Foursquare Account
							</c:otherwise>
						</c:choose>
					</c:otherwise>
				</c:choose>
			</div> <!-- #foursquare-checkin-details -->
		</div> <!-- user-details -->
	</jsp:body>
</customtags:pagetemplate>