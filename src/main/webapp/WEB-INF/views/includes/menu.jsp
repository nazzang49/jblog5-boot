<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<ul class="menu">
	<c:choose>
	<c:when test="${empty authUser }">
	<li><a href="${pageContext.request.contextPath}/user/login">Login</a></li>
	<li><a href="${pageContext.request.contextPath}/user/join">Join</a></li>
	</c:when>
	<c:otherwise>
	<li><a href="${pageContext.request.contextPath}/user/logout">Logout</a></li>
	<!-- 로그인 정보에 근거, 본인 블로그로 이동 -->
	<li><a href="${pageContext.request.contextPath}/${authUser.id}">MyBlog</a></li>
	</c:otherwise>
	</c:choose>
</ul>