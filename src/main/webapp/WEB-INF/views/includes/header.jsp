<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div id="header">
	<!-- 요청에 따라 각 회원들의 blog title 표시 -->
	<h1>${bvo.title } WELCOME</h1>
	<ul>
		<c:choose>
		<c:when test="${empty authUser }">
		<li><a href="${pageContext.request.contextPath}/user/login">Login</a></li>
		</c:when>
		<c:otherwise>
		<li><a href="${pageContext.request.contextPath}/user/logout">Logout</a></li>
		<!-- 관리 페이지 이동(세션에 저장된 아이디 == 현재 블로그 아이디) -->
		<c:if test="${authUser.id==userId }">
		<li><a href="${pageContext.request.contextPath}/${authUser.id}/admin/basic">Admin</a></li>
		</c:if>
		</c:otherwise>
		</c:choose>
	</ul>
</div>