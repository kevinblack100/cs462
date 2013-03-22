<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="customtags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<customtags:pagetemplate>
	<jsp:attribute name="header">
		<link rel="stylesheet" type="text/css" href="${contextPaths.staticPath}/site/general/tables.css" />
	</jsp:attribute>
	<jsp:attribute name="title">Event Channels</jsp:attribute>
	<jsp:body>
		<h1>Event Channels</h1>
		
		<h3>Guild &lt;=&gt; Flower Shop Channels</h3>
		<table id="guild-flower-shop-channels-table">
			<thead>
				<tr>
					<td class="table-header">ID</td>
					<td class="table-header">Flower Shop ID</td>
					<td class="table-header">Send ESL (Guild to Flower Shop)</td>
					<td class="table-header">Receive ESL (Flower Shop to Guild)</td>
				</tr>
			</thead>
			<tbody>
			<c:forEach items="${guildFlowerShopEventChannels}" var="channel">
				<tr>
					<td>${channel.id}</td>
					<td>${channel.remoteEntityId}</td>
					<td>${channel.sendESL}</td>
					<td>${channel.receiveESL}</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
	</jsp:body>
</customtags:pagetemplate>
