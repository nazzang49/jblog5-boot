<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
		<c:import url="/WEB-INF/views/includes/header.jsp"></c:import>
		<div id="wrapper">
			<div id="content">
				<div class="blog-content">
				<c:if test="${post!=null }">
					<h4>${post.title }</h4>
					<p>
						${post.contents }
					<p>
				</c:if>
				<c:if test="${post==null }">
					<h4>카테고리 OR 게시물 없음</h4>
				</c:if>
				</div>
				<table id="admin-cat-add">
					<tr>
					<th>제목</th>
					<th>작성일</th>
					</tr>
				<c:forEach items="${map.postList }" var="pvo">
					<tr>
					<td><a href="${pageContext.request.contextPath}/${userId}/${pvo.categoryNo }/${pvo.no}?pageNum=${pageNum}">${pvo.title }</a></td>
					<td>${pvo.regDate }</td>
					</tr>
				</c:forEach>
				</table>
				<c:if test="${map.count!=null}">
				<!-- pager 추가 -->
				<div class="pager">
					<ul>
						<li>
						<!-- 현재 페이지 기준, 시작 페이지 > 페이지 블럭 -->
						<c:if test='${map.startPage>map.pageBlock }'>
						<a href="${pageContext.servletContext.contextPath }/${userId }/${categoryNo }/${post.no }?pageNum=${map.startPage-map.pageBlock}"> ◀ </a>
						</c:if>
						</li>
						<!-- begin = startPage / end = endPage -->
						<c:forEach var="i" begin="${map.startPage }" end="${map.endPage }">
						<li class="selected"><a href="${pageContext.servletContext.contextPath }/${userId }/${categoryNo }/${post.no }?pageNum=${i}">${i}</a></li>
						</c:forEach>
						<li>
						<c:if test='${map.endPage<map.pageCount }'>
						<a href="${pageContext.servletContext.contextPath }/${userId }/${categoryNo }/${post.no }?pageNum=${map.startPage+map.pageBlock}"> ▶ </a>
						</c:if>
						</li>
					</ul>
				</div>
				</c:if>
			</div>
		</div>

		<div id="extra">
			<div class="blog-logo">
				<img src="${pageContext.request.contextPath}/assets${bvo.logo}">
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp"></c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp"></c:import>
	</div>
</body>
</html>
