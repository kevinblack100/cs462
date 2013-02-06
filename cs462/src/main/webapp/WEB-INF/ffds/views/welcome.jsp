<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="customtags" tagdir="/WEB-INF/tags/ffds" %>

<customtags:pagetemplate>
	<jsp:attribute name="title">Welcome!</jsp:attribute>
	<jsp:body>
		<h1>Fast Flower Delivery Service</h1>
		<p>Welcome to the Fast Flower Delivery Service site.</p>
		<ul>
			<li>
				<a href="https://github.com/windley/CS462-Event-Edition/tree/master/project">Project Description</a>
			</li>
			<li>
				<a href="./users">User List</a>
			</li>
			<li>
				<a href="./secure/signin/query">Sign In</a>
			</li>
		</ul>
	</jsp:body>
</customtags:pagetemplate>
