<!-- To be included into a page template -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="navigation">
	<a href="${pageContext.request.contextPath}/pages/">Home</a>
	|
	<c:choose>
		<c:when test="${loggedInUserContext.signedInUserDetails ne null}">
			<span>TODO: Sign Out</span>
		</c:when>
		<c:otherwise>
			<a href="${pageContext.request.contextPath}/pages/secure/signin/query">Sign In</a>
			|
			<span>TODO: Register</span>
		</c:otherwise>
	</c:choose>
</div>