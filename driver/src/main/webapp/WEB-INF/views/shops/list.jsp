<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="customtags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<customtags:pagetemplate>
	<jsp:attribute name="header">
		<link rel="stylesheet" type="text/css" href="${contextPaths.staticPath}/site/shops/list.css" />
	</jsp:attribute>
	<jsp:attribute name="title">Flower Shop List</jsp:attribute>
	<jsp:body>
		<h1>Registered Flower Shops</h1>
		
		<h3>Register new Flower Shop Profile</h3>
		<form method="post" action="${contextPaths.dynamicPath}/shops/create">
			<fieldset>
				<div>
					<dl>
						<dd><label for="name">Name: </label></dd>
						<dt><input type="text" id="new-profile-name" name="name" size=30 /></dt>
					</dl>
					<dl>
						<dd><label for="location">Location: </label></dd>
						<dt><input type="text" id="new-profile-location" name="location" size=30 /></dt>
					</dl>
					<dl>
						<dd><label for="esl">Event Signal URL: </label></dd>
						<dt><input type="text" id="new-profile-esl" name="esl" size=30 /></dt>
					</dl>
				</div>
				<input type="submit" id="new-profile-submit" value="Register" />
			</fieldset>
		</form>
		
		<h3>Registered Flower Shops</h3>
		<table id="shop-profiles-table">
			<thead>
				<tr>
					<td id="profile-id-header" class="table-header">ID</td>
					<td id="profile-name-header" class="table-header">Name</td>
					<td id="profile-location-header" class="table-header">Location</td>
					<td id="profile-esl-header" class="table-header">Event Signal URL for sending rfq:bid_available</td>
					<c:if test="${loggedInUserContext.signedInUserDetails != null}">
					<td id="driver-esl-header" class="table-header">Event Signal URL for receiving rfq:delivery_ready</td>
					</c:if>
				</tr>
			</thead>
			<tbody>
			<c:forEach items="${flowerShopProfilesController.flowerShopManager.all}" var="profile">
				<tr>
					<td class="profile-id">${profile.id}</td>
					<td class="profile-name">${profile.name}</td>
					<td class="profile-location">${profile.location}</td>
					<td class="profile-esl">${profile.eventSignalURL}</td>
					<c:if test="${loggedInUserContext.signedInUserDetails != null}">
					<td id="driver-esl">
						<c:choose>
							<c:when test="${driverProfile.deliveryReadyESLs.containsKey(profile.id)}">
								${driverProfile.deliveryReadyESLs[profile.id]}
							</c:when>
							<c:otherwise>
								<form method="post" action="${contextPaths.dynamicPath}/shops/generate-delivery-ready-esl">
									<fieldset>
										<input type="hidden" name="shop-profile-id" value="${profile.id}" />
										<input type="submit" name="button-generate-esl" value="Generate" />
									</fieldset>
								</form>
							</c:otherwise>
						</c:choose>
					</td>
					</c:if>
				</tr>
			</c:forEach>
			</tbody>
		</table>
	</jsp:body>
</customtags:pagetemplate>