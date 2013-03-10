<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="customtags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<customtags:pagetemplate>
	<jsp:attribute name="title">Flower Shop List</jsp:attribute>
	<jsp:body>
		<h1>Registered Flower Shops</h1>
		
		<h3>Register new Flower Shop Profile</h3>
		<form method="post" action="${pageContext.request.contextPath}/pages/shops/create">
			<fieldset>
				<div>
					<dl>
						<dd><label for="name">Name: </label></dd>
						<dt><input type="text" id="new-profile-name" name="name" size=30 />
					</dl>
					<dl>
						<dd><label for="location">Location: </label></dd>
						<dt><input type="text" id="new-profile-location" name="location" size=30 />
					</dl>
					<dl>
						<dd><label for="esl">Event Signal URL: </label></dd>
						<dt><input type="text" id="new-profile-esl" name="esl" size=30 />
					</dl>
				</div>
				<input type="submit" id="new-profile-submit" value="Register" />
			</fieldset>
		</form>
		
		<h3>Registered Flower Shops</h3>
		<ul>
		<c:forEach items="${flowerShopProfilesController.manager.all}" var="profile">
			<li>
				<span class="profile-id">${profile.id}</span>
				<span class="profile-name">${profile.name}</span>
				<span class="profile-location">${profile.location}</span>
				<span class="profile-esl">${profile.eventSignalURL}</span>
			</li>
		</c:forEach>
		</ul>
	</jsp:body>
</customtags:pagetemplate>