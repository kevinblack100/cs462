<!-- To be included into a page template -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="navigation">
	<a href="${pageContext.request.contextPath}/ffds/">Home</a>
	|
	<a href="${pageContext.request.contextPath}/ffds/users/">Users</a>
	|
	<c:if test="${orderPolicy.maySubmit(loggedInUserContext.signedInUserDetails)}">
		<a href="${pageContext.request.contextPath}/ffds/orders/submit">Submit Order</a>
		|
	</c:if>
	<c:choose>
		<c:when test="${loggedInUserContext.signedInUserDetails ne null}">
			<a href="${pageContext.request.contextPath}/ffds/secure/accounts/manage">Manage Account</a>
			/
			<a href="${pageContext.request.contextPath}/ffds/secure/signout/execute">Sign Out</a>
		</c:when>
		<c:otherwise>
			<a href="${pageContext.request.contextPath}/ffds/secure/signin/query">Sign In</a>
			/
			<a href="${pageContext.request.contextPath}/ffds/secure/accounts/register/query">Register</a>
		</c:otherwise>
	</c:choose>
</div>