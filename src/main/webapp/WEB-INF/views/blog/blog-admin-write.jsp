<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JBlog</title>
<Link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/jblog.css">
</head>
<body>
	<div id="container">
		<!-- 헤더 자리 -->
		<div id="wrapper">
			<div id="content" class="full-screen">
				<c:import url="/WEB-INF/views/includes/admin-menu.jsp">
					<c:param name="menu" value="write"/>
				</c:import>
				<form:form modelAttribute="pvo"
						action="${pageContext.request.contextPath}/${authUser.id}/admin/write"
						method="post">
			      	<table class="admin-cat-write">
			      		<tr>
			      			<td class="t">제목</td>
			      			<td>
			      				<form:input path="title" size="70"/>
								<p style="font-weight:bold; color:red; text-align:left; padding:0; margin:0;">
									<form:errors path="title"/>
								</p>
								<input type="hidden" id="user" value="${authUser.id }">
				      			<select name="category">
				      				<!-- 등록된 카테고리 표시 -->
				      				<c:if test="${listSize!=0 }">
				      				<c:forEach items="${categoryList }" var="cvo">
				      				<option value="${cvo.no }">${cvo.subject }</option>
				      				</c:forEach>
				      				</c:if>
				      				<c:if test="${listSize==0 }">
				      				<script type="text/javascript">
				      					var user = document.getElementById('user').value;
				      					alert("[카테고리] 최소 1개 필요");
				      					location.href="${pageContext.request.contextPath}/"+user+"/admin/category";
				      				</script>
				      				</c:if>
				      			</select>
				      		</td>
			      		</tr>
			      		<tr>
			      			<td class="t">내용</td>
			      			<td>
			      			<form:textarea path="contents"/>
							<p style="font-weight:bold; color:red; text-align:left; padding:0; margin:0;">
								<form:errors path="contents"/>
							</p>
			      			</td>
			      		</tr>
			      		<tr>
			      			<td>&nbsp;</td>
			      			<td class="s"><input type="submit" value="포스팅"></td>
			      		</tr>
			      	</table>
				</form:form>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/footer.jsp"></c:import>
	</div>
</body>
</html>