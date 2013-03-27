<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="customtags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<customtags:pagetemplate>
	<jsp:attribute name="header">
		<link rel="stylesheet" type="text/css" href="${contextPaths.staticPath}/site/general/tables.css" />
	</jsp:attribute>
	<jsp:attribute name="title">Your Delivery Requests</jsp:attribute>
	<jsp:body>
		<h1>Your Delivery Requests</h1>
		
		<table>
			<thead>
				<tr>
					<td class="table-header">Delivery ID</td>
					<td class="table-header">State</td>
					<td class="table-header">Shop Delivery ID</td>
					<td class="table-header">Requested Pickup Time</td>
					<td class="table-header">Delivery Address</td>
					<td class="table-header">Requested Delivery Time</td>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${deliveryRequests}" var="deliveryRequest">
				<tr>
					<td>${deliveryRequest.id}</td>
					<td>${deliveryRequest.state}</td>
					<td>${deliveryRequest.shopDeliveryId}</td>
					<td>${deliveryRequest.requestedPickupTime}</td>
					<td>${deliveryRequest.deliveryAddress}</td>
					<td>${deliveryRequest.requestedDeliveryTime}</td>
				</tr>
				</c:forEach>
			</tbody>
		</table>
		
	</jsp:body>
</customtags:pagetemplate>
