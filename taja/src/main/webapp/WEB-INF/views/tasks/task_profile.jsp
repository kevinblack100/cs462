<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="customtags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<customtags:pagetemplate>
	<jsp:attribute name="title">Task ${task.taskId} Profile</jsp:attribute>
	<jsp:body>
		<h1>Task ${task.taskId} Profile</h1>
		<br>
		<div>
			<dl>
				<dt>Task Results ID:</dt>
				<dd>${task.id}</dd>
			</dl>
			<dl>
				<dt>Job ID:</dt>
				<dd><a href="${contextPaths.dynamicPath}/jobs/${task.jobId}">${task.jobId}</a></dd>
			</dl>
			<dl>
				<dt>Task ID:</dt>
				<dd>${task.taskId}</dd>
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
				</dd>
			</dl>
		</div>
	</jsp:body>
</customtags:pagetemplate>