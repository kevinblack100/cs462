<!-- To be included into a page template -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="navigation">
	<a href="${pageContext.request.contextPath}/ffds/">Home</a>
	|
	<a href="${pageContext.request.contextPath}/ffds/users/">Users</a>
	|
	<c:choose>
		<c:when test="${loginController.signedInUserDetails ne null}">
			<a href="${pageContext.request.contextPath}/ffds/secure/signout/execute">Sign Out</a>
		</c:when>
		<c:otherwise>
			<a href="${pageContext.request.contextPath}/ffds/secure/signin/query">Sign In</a>
		</c:otherwise>
	</c:choose>
</div>