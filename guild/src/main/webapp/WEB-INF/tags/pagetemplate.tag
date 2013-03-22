<!-- Based on template.tag file example in the LDS Stack training on Intermediate JSP -->
<%@ tag description="Site Template Taglet" pageEncoding="UTF-8" %>
<!-- could include initialization code/scriptlets -->
<%@ attribute name="header" required="false" fragment="true" %>
<%@ attribute name="title" required="true" rtexprvalue="true" %>
<!DOCTYPE html>
<html>
	<head>
		<jsp:invoke fragment="header" />
		<title>${title}</title>
	</head>
	<body>
<%-- 		<%@ include file="/WEB-INF/views/includes/navigation.jsp" %> --%>
		<div id="main-content-panel">
			<jsp:doBody />
		</div>
	</body>
</html>