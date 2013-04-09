<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="customtags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<customtags:pagetemplate>
	<jsp:attribute name="header">
		<link rel="stylesheet" type="text/css" href="${contextPaths.staticPath}/site/general/tables.css" />
	</jsp:attribute>
	<jsp:attribute name="title">Logged Events</jsp:attribute>
	<jsp:body>
		<h1>Logged Events</h1>
		<br>
		<table>
			<thead>
				<tr>
					<td>ID</td>
					<td>Event</td>
					<td>Transmission Type</td>
					<td>ESL</td>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${loggedEvents}" var="loggedEvent">
					<tr>
						<td>${loggedEvent.id}</td>
						<td>
							<div>
								<span>${loggedEvent.event.domain}:${loggedEvent.event.name}</span>
								<br>
								<ul>
								<c:forEach items="${loggedEvent.event.attributes.entrySet()}" var="attribute">
									<li>${attribute.key}: ${attribute.value}</li>
								</c:forEach>
								</ul>
							</div>
						</td>
						<td>${loggedEvent.transmissionType}</td>
						<td>${loggedEvent.esl}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</jsp:body>
</customtags:pagetemplate>