<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="customtags" tagdir="/WEB-INF/tags" %>

<customtags:pagetemplate>
	<jsp:attribute name="title">Welcome!</jsp:attribute>
	<jsp:body>
		<h1>Task Aggregator &amp; Job Analysis Web Service</h1>
		<h2>in the Distributed Job Execution System</h2>
		<p>Collects word counts from job:task_results events and produces ZingChart graphs of the results</p>
		<a href="https://github.com/windley/CS462-Event-Edition/tree/master/project">Project Specifications</a>
	</jsp:body>
</customtags:pagetemplate>