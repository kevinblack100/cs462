<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="customtags" tagdir="/WEB-INF/tags/ffds" %>

<customtags:pagetemplate>
	<jsp:attribute name="title">Flower Shop Profile</jsp:attribute>
	<jsp:body>
		<h1>Flower Shop Profile</h1>
		<form method="post" action="${pageContext.request.contextPath}/ffds/shop/profile">
			<fieldset>
				<label for="shop-name">Shop name</label>
				<input type="text" id="shop-name" name="shop-name" value="${profile.name}" />
				<br />
				<label for="shop-address">Shop Address</label>
				<input type="text" id="shop-address" name="shop-address" value="${profile.address}" />
				<br />
				<br />
				<input type="submit" id="submit" name="submit" value="Submit" />
			</fieldset>
		</form>
	</jsp:body>
</customtags:pagetemplate>