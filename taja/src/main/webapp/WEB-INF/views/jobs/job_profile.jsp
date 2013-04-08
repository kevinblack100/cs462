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
				<dt>Results ID:</dt>
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
				<dd>TODO</dd>
			</dl>
		</div>
	</jsp:body>
</customtags:pagetemplate>