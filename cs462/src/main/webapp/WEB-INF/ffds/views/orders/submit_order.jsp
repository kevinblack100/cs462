<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="customtags" tagdir="/WEB-INF/tags/ffds" %>

<customtags:pagetemplate>
	<jsp:attribute name="title">Submit Order</jsp:attribute>
	<jsp:body>
		<h1>Submit Order</h1>
		<form method="post" action="${pageContext.request.contextPath}/ffds/orders/submit">
			<fieldset>
				<label for="pickup-time">Pickup Time</label>
				<input type="time" id="pickup-time" name="pickup-time" />
				<br />
				<label for="delivery-address">Delivery Address</label>
				<input type="text" id="delivery-address" name="delivery-address" />
				<br />
				<label for="delivery-time">Delivery Time</label>
				<input type="time" id="delivery-time" name="delivery-time" />
				<br />
				<br />
				<input type="submit" id="submit" name="submit" value="Submit" />
			</fieldset>
		</form>
	</jsp:body>
</customtags:pagetemplate>