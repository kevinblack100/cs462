<!-- To be included into a page template -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="navigation">
	<a href="${contextPaths.dynamicPath}/">Home</a>
	|
	<a href="${contextPaths.dynamicPath}/users">Users</a>
	|
	<a href="${contextPaths.dynamicPath}/shops">Shops</a>
	|
	<c:choose>
		<c:when test="${loggedInUserContext.signedInUserDetails ne null}">
			<a href="${contextPaths.dynamicPath}/secure/accounts/signout">Sign Out</a>
		</c:when>
		<c:otherwise>
			<a href="${contextPaths.dynamicPath}/secure/accounts/signin">Sign In</a>
			/
			<a href="${contextPaths.dynamicPath}/secure/accounts/register">Register</a>
		</c:otherwise>
	</c:choose>
</div>