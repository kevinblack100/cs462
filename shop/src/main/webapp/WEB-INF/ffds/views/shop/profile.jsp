<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="customtags" tagdir="/WEB-INF/tags/ffds" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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
				<h3>Flower Shop &lt;=&gt; Guild Channel Details</h3>
				<label for="channel-send-esl">Send ESL:</label>
				<input type="text" id="channel-send-esl" name="channel-send-esl" value="${channel.sendESL}" size=60 />
				<br>
				<span>Receive ESL: 
				<c:choose>
					<c:when test="${channel.receiveESL ne null}">
						${channel.receiveESL}
					</c:when>
					<c:otherwise>
						Not specified
					</c:otherwise>
				</c:choose>
				</span>
				<br>
				<br>
				<input type="submit" id="submit" name="submit" value="Submit" />
			</fieldset>
		</form>
	</jsp:body>
</customtags:pagetemplate>