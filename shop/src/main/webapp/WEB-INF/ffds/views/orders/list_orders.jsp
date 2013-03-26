<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="customtags" tagdir="/WEB-INF/tags/ffds" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<customtags:pagetemplate>
	<jsp:attribute name="header">
		<link rel="stylesheet" type="text/css" href="${contextPaths.staticPath}/site/orders/list_orders.css" />
	</jsp:attribute>
	<jsp:attribute name="title">Submitted Orders</jsp:attribute>
	<jsp:body>
		<h1>Submitted Orders</h1>
		
		<c:if test="${orderPolicy.maySubmit(loggedInUserContext.signedInUserDetails)}">
			<a href="${contextPaths.dynamicPath}/orders/submit">Submit New Order</a>
		</c:if>
		
		<br>
		<br>
		
		<table>
			<thead>
				<tr>
					<td class="table-header">Order ID</td>
					<td class="table-header">State</td>
					<td class="table-header">Requested Pickup Time</td>
					<td class="table-header">Delivery Address</td>
					<td class="table-header">Requested Delivery Time</td>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${orders}" var="order">
				<tr>
					<td><a href="${contextPaths.dynamicPath}/orders/${order.id}">${order.id}</a></td>
					<td>${order.state}</td>
					<td>${order.pickupTime}</td>
					<td>${order.deliveryAddress}</td>
					<td>${order.deliveryTime}</td>
				</tr>
				</c:forEach>
			</tbody>
		</table>
		
	</jsp:body>
</customtags:pagetemplate>
