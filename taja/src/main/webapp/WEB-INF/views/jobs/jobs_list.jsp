<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="customtags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<customtags:pagetemplate>
	<jsp:attribute name="header">
		<link rel="stylesheet" type="text/css" href="${contextPaths.staticPath}/site/general/tables.css" />
	</jsp:attribute>
	<jsp:attribute name="title">Job List</jsp:attribute>
	<jsp:body>
		<h1>Jobs</h1>
		<br>
		<table>
			<thead>
				<tr>
					<td>Job Results ID</td>
					<td>Job ID</td>
					<td># Words</td>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${jobs}" var="job">
					<tr>
						<td><a href="${contextPaths.dynamicPath}/jobs/${job.jobId}">${job.id}</a></td>
						<td><a href="${contextPaths.dynamicPath}/jobs/${job.jobId}">${job.jobId}</a></td>
						<td>${job.wordCounts.size()}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</jsp:body>
</customtags:pagetemplate>