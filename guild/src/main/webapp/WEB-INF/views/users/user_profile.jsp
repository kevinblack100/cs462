<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="customtags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<customtags:pagetemplate>
	<jsp:attribute name="title">User Profile</jsp:attribute>
	<jsp:body>
		<h1>${username} Profile</h1>
		<div>
			<dl>
				<dt>Username:</dt>
				<dd>${username}</dd>
			</dl>
			<dl>
				<dt>Driver ranking:</dt>
				<dd>
					<c:choose>
						<c:when test="${profile.driverRanking ne null}">
							${profile.driverRanking}
						</c:when>
						<c:otherwise>
							Not Specified
						</c:otherwise>
					</c:choose>
				</dd>
			</dl>
			<h4>User Channel Details</h4>
			<dl>
				<dt>ID:</dt>
				<dd>
				<c:choose>
					<c:when test="${channel.id ne null}">
						${channel.id}
					</c:when>
					<c:otherwise>
						Not specified
					</c:otherwise>
				</c:choose>
				</dd>
			</dl>
			<dl>
				<dt>Send ESL:</dt>
				<dd>
				<c:choose>
					<c:when test="${isLoggedInUserProfile}">
						<form method="post" action="${contextPaths.dynamicPath}/users/${username}/set-send-esl">
							<fieldset>
								<input type="text" name="send-esl" value="${channel.sendESL}" size=60 />
								<br>
								<input type="submit" name="submit" value="Save" />
							</fieldset>
						</form>
					</c:when>
					<c:otherwise>
						<c:choose>
							<c:when test="${channel.sendESL ne null}">
								${channel.sendESL}
							</c:when>
							<c:otherwise>
								Not specified
							</c:otherwise>
						</c:choose>
					</c:otherwise>
				</c:choose>
				</dd>
			</dl>
			<dl>
				<dt>Receive ESL:</dt>
				<dd>
				<c:choose>
					<c:when test="${channel.receiveESL ne null}">
						${channel.receiveESL}
					</c:when>
					<c:otherwise>
						Not specified
					</c:otherwise>
				</c:choose>
				</dd>
			</dl>
		</div>
	</jsp:body>
</customtags:pagetemplate>