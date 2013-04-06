<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="customtags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<customtags:pagetemplate>
	<jsp:attribute name="header">
		<link rel="stylesheet" type="text/css" href="${contextPaths.staticPath}/site/general/tables.css" />
	</jsp:attribute>
	<jsp:attribute name="title">Task List</jsp:attribute>
	<jsp:body>
		<h1>Tasks</h1>
		
		<div>
		<c:choose>
			<c:when test="${filteredByJob}">
				For job ${jobId}.
			</c:when>
			<c:otherwise>
				For all jobs.
			</c:otherwise>
		</c:choose>
		</div>
		<br>
		<table>
			<thead>
				<tr>
					<td>ID</td>
					<td>Job ID</td>
					<td>Task ID</td>
					<td># Words</td>
					<td>Chart</td>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${tasks}" var="task">
					<tr>
						<td>${task.id}</td>
						<td>${task.jobId}</td>
						<td>${task.taskId}</td>
						<td>${task.wordCounts.size()}</td>
						<td><script type="text/javascript" src="http://api.zingchart.com/html5?${renderingQueryStrings[task.id]}"></script></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</jsp:body>
</customtags:pagetemplate>