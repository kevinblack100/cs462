<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="customtags" tagdir="/WEB-INF/tags/ffds" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<customtags:pagetemplate>
	<jsp:attribute name="header">
		<link rel="stylesheet" type="text/css" href="${contextPaths.staticPath}/site/general/tables.css" />
	</jsp:attribute>
	<jsp:attribute name="title">Order Profile</jsp:attribute>
	<jsp:body>
		<h1>Order ${order.id}</h1>
		
		<br>
		
		<span>State: ${order.state}</span>
		<br>
		<span>Requested Pickup Time: ${order.pickupTime}</span>
		<br>
		<span>Delivery Address: ${order.deliveryAddress}</span>
		<br>
		<span>Requested Delivery Time: ${order.deliveryTime}</span>
		
		<br>
		<br>
		
		<c:choose>
			<c:when test="${orderPolicy.maySignalPickup(loggedInUserContext.signedInUserDetails, order)}">
				<form method="post" action="${contextPaths.dynamicPath}/orders/${order.id}/pickedup">
					<input type="submit" name="submit" value="Picked Up" />
				</form>
			</c:when>
		</c:choose>
		
		<h3>Bids</h3>
		
		<table>
			<thead>
				<tr>
					<td class="table-header">Bid ID</td>
					<td class="table-header">Driver ID</td>
					<td class="table-header">Driver Name</td>
					<td class="table-header">Estimated Delivery Time</td>
					<td class="table-header">Amount</td>
					<td class="table-header">Amount Unit</td>
					<td class="table-header">Selected</td>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${bids}" var="bid">
				<tr>
					<td>${bid.id}</td>
					<td>${bid.driverId}</td>
					<td>${bid.driverName}</td>
					<td>${bid.estimatedDeliveryTime}</td>
					<td>${bid.amount}</td>
					<td>${bid.amountUnits}</td>
					<td>
					<c:choose>
						<c:when test="${orderPolicy.maySelectBid(loggedInUserContext.signedInUserDetails, order)}">
							<form method="post" action="${contextPaths.dynamicPath}/orders/${order.id}/selectbid">
								<fieldset>
									<input type="hidden" name="selected-bid-id" value="${bid.id}" />
									<input type="submit" name="submit" value="Select" />
								</fieldset>
							</form>
						</c:when>
						<c:otherwise>
							<c:choose>
								<c:when test="${order.selectedBidID == bid.id}">
								Yes
								</c:when>
								<c:otherwise>
								No
								</c:otherwise>
							</c:choose>
						</c:otherwise>
					</c:choose>
					</td>
				</tr>
				</c:forEach>
			</tbody>
		</table>
		
	</jsp:body>
</customtags:pagetemplate>
