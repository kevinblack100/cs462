<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="customtags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<customtags:pagetemplate>
	<jsp:attribute name="header">
		<link rel="stylesheet" type="text/css" href="${contextPaths.staticPath}/site/general/tables.css" />
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
						<dd><label for="latitude">Latitude: </label></dd>
						<dt><input type="text" id="new-profile-latitude" name="latitude" size=10 /></dt>
					</dl>
					<dl>
						<dd><label for="longitude">Longitude: </label></dd>
						<dt><input type="text" id="new-profile-longitude" name="longitude" size=10 /></dt>
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
					<td id="profile-latitude-header" class="table-header">Latitude</td>
					<td id="profile-longitude-header" class="table-header">Longitude</td>
				</tr>
			</thead>
			<tbody>
			<c:forEach items="${flowerShopProfiles}" var="profile">
				<tr>
					<td class="profile-id">${profile.id}</td>
					<td class="profile-name">${profile.name}</td>
					<td class="profile-location">${profile.location}</td>
					<td class="profile-latitude">${profile.latitude}</td>
					<td class="profile-longitude">${profile.longitude}</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
	</jsp:body>
</customtags:pagetemplate>
