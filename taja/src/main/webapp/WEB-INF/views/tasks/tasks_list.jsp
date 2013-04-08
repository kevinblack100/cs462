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
		<br>
		<table>
			<thead>
				<tr>
					<td>Task Results ID</td>
					<td>Job ID</td>
					<td>Task ID</td>
					<td># Words</td>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${tasks}" var="task">
					<tr>
						<td><a href="${contextPaths.dynamicPath}/tasks/${task.id}">${task.id}</a></td>
						<td><a href="${contextPaths.dynamicPath}/jobs/${task.jobId}">${task.jobId}</a></td>
						<td><a href="${contextPaths.dynamicPath}/tasks/${task.id}">${task.taskId}</a></td>
						<td>${task.wordCounts.size()}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</jsp:body>
</customtags:pagetemplate>