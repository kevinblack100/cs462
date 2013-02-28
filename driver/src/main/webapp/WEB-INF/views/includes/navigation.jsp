<!-- To be included into a page template -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="navigation">
	<a href="${pageContext.request.contextPath}/pages/">Home</a>
	|
	<a href="${pageContext.request.contextPath}/pages/users">Users</a>
	|
	<c:choose>
		<c:when test="${loggedInUserContext.signedInUserDetails ne null}">
			<a href="${pageContext.request.contextPath}/pages/secure/accounts/signout">Sign Out</a>
		</c:when>
		<c:otherwise>
			<a href="${pageContext.request.contextPath}/pages/secure/accounts/signin">Sign In</a>
			/
			<a href="${pageContext.request.contextPath}/pages/secure/accounts/register">Register</a>
		</c:otherwise>
	</c:choose>
</div>