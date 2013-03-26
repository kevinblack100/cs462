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
					<td class="table-header">ID</td>
					<td class="table-header">Name</td>
					<td class="table-header">Location</td>
					<td class="table-header">Latitude</td>
					<td class="table-header">Longitude</td>
					<td class="table-header">Shop Channel ID</td>
					<td class="table-header">Send ESL (Guild to Flower Shop)</td>
					<td class="table-header">Receive ESL (Flower Shop to Guild)</td>
				</tr>
			</thead>
			<tbody>
			<c:forEach items="${flowerShopDataContainers}" var="container">
				<tr>
					<td>${container.profile.id}</td>
					<td>${container.profile.name}</td>
					<td>${container.profile.location}</td>
					<td>${container.profile.latitude}</td>
					<td>${container.profile.longitude}</td>
					<td>${container.channel.id}</td>
					<td>
						<form method="post" action="${contextPaths.dynamicPath}/shops/update-send-esl">
							<fieldset>
								<input type="hidden" name="channel-id" value="${container.channel.id}" />
								<input type="text" name="send-esl" value="${container.channel.sendESL}" size=60 />
								<br>
								<input type="submit" name="submit" value="Save" />
							</fieldset>
						</form>
					</td>
					<td>${container.channel.receiveESL}</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
	</jsp:body>
</customtags:pagetemplate>
