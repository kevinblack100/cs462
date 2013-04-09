<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="customtags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<customtags:pagetemplate>
	<jsp:attribute name="header">
		<link rel="stylesheet" type="text/css" href="${contextPaths.staticPath}/site/general/tables.css" />
	</jsp:attribute>
	<jsp:attribute name="title">Job ${job.jobId} Profile</jsp:attribute>
	<jsp:body>
		<h1>Job ${job.jobId} Profile</h1>
		<br>
		<div>
			<dl>
				<dt>Job Results ID:</dt>
				<dd>${job.id}</dd>
			</dl>
			<dl>
				<dt>Original Job ID:</dt>
				<dd>${job.jobId}</dd>
			</dl>
			<dl>
				<dt># Words:</dt>
				<dd>${job.wordCounts.size()}</dd>
			</dl>
			<dl>
				<dt>Chart:</dt>
				<dd>
					<script type="text/javascript" src="http://api.zingchart.com/html5?${renderingQueryString}">
					</script>
					<br>
					<script type="text/javascript" src="http://api.zingchart.com/html5?${componentRenderingQueryString}">
					</script>
				</dd>
			</dl>
			<dl>
				<dt>Tasks:</dt>
				<dd>
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
									<td>${task.jobId}</td>
									<td><a href="${contextPaths.dynamicPath}/tasks/${task.id}">${task.taskId}</a></td>
									<td>${task.wordCounts.size()}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</dd>
			</dl>
		</div>
	</jsp:body>
</customtags:pagetemplate>