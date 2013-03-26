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