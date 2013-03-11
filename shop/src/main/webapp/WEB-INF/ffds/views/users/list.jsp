<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="customtags" tagdir="/WEB-INF/tags/ffds" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<customtags:pagetemplate>
	<jsp:attribute name="title">User List</jsp:attribute>
	<jsp:body>
		<h1>Users</h1>
		<ul>
		<c:forEach items="${usernames}" var="username">
			<li>
				<a href="${contextPaths.dynamicPath}/users/${username}">${username}</a>
			</li>
		</c:forEach>
		</ul>
	</jsp:body>
</customtags:pagetemplate>