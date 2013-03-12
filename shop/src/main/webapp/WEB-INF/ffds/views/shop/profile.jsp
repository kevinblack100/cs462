<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="customtags" tagdir="/WEB-INF/tags/ffds" %>

<customtags:pagetemplate>
	<jsp:attribute name="title">Flower Shop Profile</jsp:attribute>
	<jsp:body>
		<h1>Flower Shop Profile</h1>
		<form method="post" action="${contextPaths.dynamicPath}/shop/profile">
			<fieldset>
				<label for="shop-name">Shop name</label>
				<input type="text" id="shop-name" name="shop-name" value="${profile.name}" />
				<br>
				<label for="shop-address">Shop Address</label>
				<input type="text" id="shop-address" name="shop-address" value="${profile.address}" />
				<br>
				<label for="shop-latitude">Latitude</label>
				<input type="text" id="shop-latitude" name="shop-latitude" value="${profile.latitude}" />
				<br>
				<label for="shop-longitude">Longitude</label>
				<input type="text" id="shop-longitude" name="shop-longitude" value="${profile.longitude}" />
				<br>
				<br>
				<input type="submit" id="submit" name="submit" value="Submit" />
			</fieldset>
		</form>
	</jsp:body>
</customtags:pagetemplate>